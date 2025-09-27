package com.example.board.controller;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.Board5Entity;
import com.example.board.entity.BoardCommentEntity;
import com.example.board.entity.MemberEntity;
import com.example.board.repository.Board5Repository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentApiContorller {
    private final CommentRepository commentRepository;
    private final Board5Repository board5Repository;
    private final MemberRepository memberRepository;

    public CommentApiContorller(CommentRepository commentRepository, Board5Repository board5Repository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.board5Repository = board5Repository;
        this.memberRepository = memberRepository;
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable("boardId")Long boardId){
        List<BoardCommentEntity> comments = commentRepository.findByBoardIdOrderByCreateTimeAsc(boardId);

        List<CommentDTO> dtos = comments.stream().map(comment ->{
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setAuthorNickname(comment.getAuthor().getNickname());
            dto.setCreateTime(comment.getCreateTime());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Board5Entity board = board5Repository.findById(commentDTO.getBoardId())
                .orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        MemberEntity author = memberRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        BoardCommentEntity newComment = new BoardCommentEntity();
        newComment.setContent(commentDTO.getContent());
        newComment.setCreateTime(LocalDateTime.now());
        newComment.setBoard(board);
        newComment.setAuthor(author);
        BoardCommentEntity saveComment = commentRepository.save(newComment);

        CommentDTO responseDTO = new CommentDTO();
        responseDTO.setContent(saveComment.getContent());
        responseDTO.setAuthorNickname(saveComment.getAuthor().getNickname());
        responseDTO.setCreateTime(saveComment.getCreateTime());

        return ResponseEntity.ok(responseDTO);

    }
    @Transactional
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<CommentDTO>updateComment(@PathVariable("commentId")Long commentId,
                                                   @RequestBody CommentDTO commentDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        BoardCommentEntity comment =commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
        if(!comment.getAuthor().getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        comment.setContent(commentDTO.getContent());
        BoardCommentEntity updatedComment = commentRepository.save(comment);

        CommentDTO dto = new CommentDTO();
        dto.setContent(updatedComment.getContent());
        dto.setAuthorNickname(updatedComment.getAuthor().getNickname());
        dto.setCreateTime(updatedComment.getCreateTime());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId")Long commentId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        BoardCommentEntity comment =commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
        if(!comment.getAuthor().getUsername().equals(currentUsername)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }
}
