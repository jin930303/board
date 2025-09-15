package com.example.board.service;

import com.example.board.dto.Board4DTO;
import com.example.board.entity.Board4Entity;
import com.example.board.repository.Board4Repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Log4j2
@Service
public class Board4ServiceImpl implements Board4Service{

    @Autowired
    Board4Repository board4Repository;

    @Value("${upload.file.path}")
    private String uploadDir;

    @Override
    public void board4Save(Board4DTO board4DTO) {
        MultipartFile file = board4DTO.getImg();
        if(file!=null && !file.isEmpty()){
            try{
                String uuid = UUID.randomUUID().toString();
                String originalFileName= file.getOriginalFilename();
                String saveFileName = uuid+"_"+originalFileName;

                File uploadDirectory = new File(uploadDir);
                if(!uploadDirectory.exists()){
                    uploadDirectory.mkdirs();
                }
                File saveFile = new File(uploadDir,saveFileName);
                file.transferTo(saveFile);

                board4DTO.setFilePath("C:\\upload\\"+saveFileName);
                board4DTO.setFileOriginalName(originalFileName);
                board4DTO.setFileSize(file.getSize());
            }
            catch (IOException e){
                throw new RuntimeException("파일 업로드 실패",e);
            }
        }

        Board4Entity board4Entity = new Board4Entity();
        board4Entity.setTitle(board4DTO.getTitle());
        board4Entity.setContent(board4DTO.getContent());
        board4Entity.setInputDate(LocalDateTime.now());
        board4Entity.setAuthor(board4DTO.getAuthor());
        log.info("--------------");
        log.info(board4Entity.getAuthor());
        board4Entity.setFileOriginalName(board4DTO.getFileOriginalName());
        board4Entity.setFilePath(board4DTO.getFilePath());
        board4Entity.setFileSize(board4DTO.getFileSize());

        board4Repository.save(board4Entity);

    }

    @Override
    public List<Board4Entity> out() {
        return board4Repository.findAll();
    }

    @Override
    public Board4Entity findById(Long id) {
        return board4Repository.findById(id).orElseThrow(()->new NullPointerException("데이터를 찾을 수 없습니다."));
    }

    @Override
    public void updateBoard(Long id, Board4DTO board4DTO) {
        Board4Entity entity = board4Repository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        MultipartFile file = board4DTO.getImg();

        boolean checkNewFile= (file !=null && !file.isEmpty());

        boolean wasFilePresent = (entity.getFilePath() !=null && !entity.getFilePath().isEmpty());

        if(checkNewFile){
            if(wasFilePresent){
                log.info("기존파일 변경 및 내용 수정");
                try{
                    Files.deleteIfExists(Paths.get(entity.getFilePath()));
                }
                catch(IOException e){
                    throw new RuntimeException("기존 파일 삭제 실패",e);
                }
            }
            try{
                String uuid = UUID.randomUUID().toString();
                String originalFileName = file.getOriginalFilename();
                String saveFileName = uuid + "_" + originalFileName;

                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }
                File saveFile = new File(uploadDir, saveFileName);
                file.transferTo(saveFile);

                entity.setFilePath("C:\\upload\\" + saveFileName);
                entity.setFileOriginalName(originalFileName);
                entity.setFileSize(file.getSize());
            }
            catch(IOException e){
                throw new RuntimeException("파일 업로드 실패", e);
            }

        }
        entity.setTitle(board4DTO.getTitle());
        entity.setContent(board4DTO.getContent());
        entity.setInputDate(LocalDateTime.now());
        board4Repository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws IOException {
        Board4Entity entity = board4Repository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시판이 없습니다."));
        try {
            if (entity.getFilePath() != null) {
                Files.deleteIfExists(Paths.get(entity.getFilePath()));
            }
        }
        catch(IOException e){
            log.info("파일 삭제 실패");
        }
        board4Repository.deleteById(id);
    }


}
