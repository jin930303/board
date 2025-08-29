package com.example.board.service;

import com.example.board.dto.Board3DTO;
import com.example.board.entity.Board3Entity;
import com.example.board.repository.Board3Repository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Setter
@Getter
public class Board3ServiceImpl implements Board3Service{

    @Autowired
    Board3Repository board3Repository;

    @Override
    public void saveBoard(Board3DTO board3DTO) {
        MultipartFile file = board3DTO.getFile();
        if(file !=null && !file.isEmpty()){
            try {

            String uuid = UUID.randomUUID().toString();
            String originalFileName=file.getOriginalFilename();
            String saveFileName=uuid+" "+originalFileName;

            String uploadDir = "C:\\upload\\";
            File uploadDirectory = new File(uploadDir);
            if(!uploadDirectory.exists()){
                uploadDirectory.mkdirs();
            }
            File saveFile = new File(uploadDir,saveFileName);
            file.transferTo(saveFile);

            board3DTO.setFilePath(uploadDir + saveFileName);
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
}
