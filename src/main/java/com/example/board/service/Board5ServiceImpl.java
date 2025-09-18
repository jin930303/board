package com.example.board.service;

import com.example.board.dto.Board5DTO;
import com.example.board.entity.Board5Entity;
import com.example.board.repository.Board5Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class Board5ServiceImpl implements Board5Service {

    @Autowired
    Board5Repository board5Repository;

    @Value("${upload.file.path}")
    private String uploadDir;

    @Override
    public void save(Board5DTO board5DTO) {
        MultipartFile mulFile = board5DTO.getImg();
        if(mulFile!=null&&!mulFile.isEmpty()){
            try{
                String uuid = UUID.randomUUID().toString();
                String originalFileName = mulFile.getOriginalFilename();
                String saveFileName = uuid+"_"+originalFileName;

                File uploadDirectory = new File(uploadDir);
                if(!uploadDirectory.exists()){
                    uploadDirectory.mkdirs();
                }
                File saveFile = new File(uploadDir,saveFileName);
                mulFile.transferTo(saveFile);

                board5DTO.setFilePath("C:\\upload\\"+saveFileName);
                board5DTO.setFileOriginalName(originalFileName);
                board5DTO.setFileSize(mulFile.getSize());

            }
            catch (IOException e){
                throw new RuntimeException("파일 업로드 실패",e);
            }

        }
        Board5Entity board5Entity = new Board5Entity();
        board5Entity.setTitle(board5DTO.getTitle());
        board5Entity.setContent(board5DTO.getContent());
        board5Entity.setAuthor(board5DTO.getAuthor());
        board5Entity.setInputDate(LocalDateTime.now());
        board5Entity.setFileOriginalName(board5DTO.getFileOriginalName());
        board5Entity.setFilePath(board5DTO.getFilePath());
        board5Entity.setFileSize(board5DTO.getFileSize());

        board5Repository.save(board5Entity);
        }

    @Override
    public List<Board5Entity> out() {
        return board5Repository.findAll();
    }

    @Override
    public Board5Entity findById(Long id) {
        return board5Repository.findById(id).orElseThrow(()->new NullPointerException("찾는 게시판이 없습니다."));
    }

    @Override
    public void updateBoard(Long id, Board5DTO board5DTO) {

        Board5Entity entity = board5Repository.findById(id).orElseThrow(() -> new NullPointerException("찾는 게시판이 없습니다."));

        MultipartFile file = board5DTO.getImg();

        boolean checkNewFile = (file != null && !file.isEmpty());

        boolean wasFilePresent = (entity.getFilePath() != null && !entity.getFilePath().isEmpty());
        if(checkNewFile) {
            if (wasFilePresent) {
                try {
                    Files.deleteIfExists(Paths.get(entity.getFilePath()));

                } catch (IOException e) {
                    throw new RuntimeException("기존 파일 삭제 실패", e);
                }
            }
            try {
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
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }
        entity.setTitle(board5DTO.getTitle());
        entity.setContent(board5DTO.getContent());
        entity.setInputDate(LocalDateTime.now());
        board5Repository.save(entity);

    }

    @Override
    public void deleteById(Long id) {
        Board5Entity entity = board5Repository.findById(id)
                .orElseThrow(()->new NullPointerException("해당 게시물을 찾을 수 없습니다."));
        try{
            if(entity.getFilePath()!=null){
                Files.deleteIfExists(Paths.get(entity.getFilePath()));
            }
        }
        catch(IOException e){
            throw new RuntimeException("파일 삭제 실패");
        }

        board5Repository.deleteById(id);
    }


}
