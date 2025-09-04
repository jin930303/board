package com.example.board.service;

import com.example.board.dto.Board3DTO;
import com.example.board.entity.Board3Entity;
import com.example.board.repository.Board3Repository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Setter
@Getter
public class Board3ServiceImpl implements Board3Service{

    @Autowired
    Board3Repository board3Repository;

    @Value("${upload.file.path}")
    private String uploadDir;

    @Override
    public void saveBoard(Board3DTO board3DTO) {
        MultipartFile file = board3DTO.getFile();
        if(file !=null && !file.isEmpty()){
            try {

            String uuid = UUID.randomUUID().toString();
            String originalFileName=file.getOriginalFilename();
            String saveFileName=uuid+"_"+originalFileName;

            File uploadDirectory = new File(uploadDir);
            if(!uploadDirectory.exists()){
                uploadDirectory.mkdirs();
            }
            File saveFile = new File(uploadDir,saveFileName);
            file.transferTo(saveFile);

            board3DTO.setFilePath("C:\\upload\\" + saveFileName);
            board3DTO.setFileOriginalName(originalFileName);
            board3DTO.setFileSize(file.getSize());
            }
            catch (IOException e){
                throw new RuntimeException("파일 업로드 실패",e);
            }
        }

        Board3Entity board3Entity = new Board3Entity();
        board3Entity.setTitle(board3DTO.getTitle());
        board3Entity.setContent(board3DTO.getContent());
        board3Entity.setAuthor(board3DTO.getAuthor());
        board3Entity.setFilePath(board3DTO.getFilePath());
        board3Entity.setFileOriginalName(board3DTO.getFileOriginalName());
        board3Entity.setFileSize(board3DTO.getFileSize());
        board3Entity.setInputDate(LocalDateTime.now());

        board3Repository.save(board3Entity);
    }

    @Override
    public List<Board3Entity> out() {
        return board3Repository.findAll();
    }

    @Override
    public Board3Entity findById(Long id) {
        return board3Repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Override
    public void UpdateBoard(Board3DTO board3DTO, Long id) {
        Board3Entity entity = board3Repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        MultipartFile mulfile = board3DTO.getFile();

        boolean checkNewFile= (mulfile !=null && !mulfile.isEmpty());

        boolean wasFilePresent = (entity.getFilePath() !=null && !entity.getFilePath().isEmpty());

        if(checkNewFile){
            if(wasFilePresent){
                System.out.println("기존파일 변경 및 내용 수정");
                try{
                    Files.deleteIfExists(Paths.get(entity.getFilePath()));
                }
                catch(IOException e){
                    throw new RuntimeException("기존 파일 삭제 실패",e);
                }
            }
            try{
                String uuid = UUID.randomUUID().toString();
                String originalFileName = mulfile.getOriginalFilename();
                String saveFileName = uuid + "_" + originalFileName;

                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }
                File saveFile = new File(uploadDir, saveFileName);
                mulfile.transferTo(saveFile);

                entity.setFilePath("C:\\upload\\" + saveFileName);
                entity.setFileOriginalName(originalFileName);
                entity.setFileSize(mulfile.getSize());
            }
            catch(IOException e){
                throw new RuntimeException("파일 업로드 실패", e);
            }

        }
        entity.setTitle(board3DTO.getTitle());
        entity.setContent(board3DTO.getContent());
        entity.setInputDate(LocalDateTime.now());

        board3Repository.save(Objects.requireNonNull(entity));
        }

    @Override
    public void DeleteById(Long id) throws IOException {
        Board3Entity entity = board3Repository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시판이 없습니다."));
        try {
            if (entity.getFilePath() != null) {
                Files.deleteIfExists(Paths.get(entity.getFilePath()));
            }
        }
        catch(IOException e){
            System.out.println("파일 삭제 실패");
        }
        board3Repository.deleteById(id);

    }

    @Override
    public Page<Board3DTO> getBoardList(int page) {
        int pageNum = (page==0) ? 0 : page-1;
        int pageSize = 10;

        int startRow = pageNum * pageSize +1;
        int endRow = startRow + pageSize -1;

        System.out.println(" 시작 :" + startRow);
        System.out.println(" 끝  :" + endRow);

        List<Board3Entity> list = board3Repository.findByPage(startRow,endRow);

        long totalCount = board3Repository.countBoard3();

        List<Board3DTO> dtolist = list.stream().map(Board3DTO::new).toList();

        Pageable pageable = PageRequest.of(pageNum,pageSize);
        
        return new PageImpl<>(dtolist,pageable,totalCount);
    }

    @Override
    public Page<Board3DTO> searchBoardList(String kw, int page) {

        int pageNum = (page==0)? 0: page -1 ;
        int pageSize = 10;
        int startRow = pageNum * pageSize +1;
        int endRow = startRow * pageSize -1;

        List<Board3Entity> list = board3Repository.findByPageAndKeyword(startRow,endRow,kw);

        long totalCount = board3Repository.countBoard3WithKeyword(kw);

        List<Board3DTO> dtoList = list.stream().map(Board3DTO::new).toList();

        Pageable pageable = PageRequest.of(pageNum,pageSize);

        return new PageImpl<>(dtoList,pageable,totalCount);
    }

}

