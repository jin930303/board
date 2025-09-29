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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
    private final CommentRepository commentRepository;
    private final Board5Repository board5Repository;
    private final MemberRepository memberRepository;

    public CommentApiController(CommentRepository commentRepository, Board5Repository board5Repository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.board5Repository = board5Repository;
        this.memberRepository = memberRepository;
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable("boardId") Long boardId) {
        List<BoardCommentEntity> comments = commentRepository.findByBoardIdOrderByCreateTimeAsc(boardId);

        List<CommentDTO> dtos = comments.stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setAuthorNickname(comment.getAuthor().getNickname());
            dto.setCreateTime(comment.getCreateTime());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @AuthenticationPrincipal Object principal) {

        String authorNickname = extractAuthor(principal);

        if(authorNickname == null || authorNickname.isEmpty()){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        Board5Entity board = board5Repository.findById(commentDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        MemberEntity author = memberRepository.findByNickname(authorNickname)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

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
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long commentId,
                                                    @RequestBody CommentDTO commentDTO,
                                                    Authentication authentication
    ) {

        String currentNickname = extractNickname(authentication);

        if (currentNickname == null || currentNickname.isEmpty() || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        BoardCommentEntity comment =commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        // ⭐⭐⭐ 여기서 currentNickname과 댓글 작성자의 닉네임을 비교하도록 수정
        if(!comment.getAuthor().getNickname().equals(currentNickname)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // ... (나머지 수정 로직 유지)
        comment.setContent(commentDTO.getContent());
        BoardCommentEntity updatedComment = commentRepository.save(comment);

        CommentDTO dto = new CommentDTO();
        dto.setContent(updatedComment.getContent());
        dto.setAuthorNickname(updatedComment.getAuthor().getNickname());
        dto.setCreateTime(updatedComment.getCreateTime());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
                                              Authentication authentication) {

        String currentNickname = extractNickname(authentication);

        if (currentNickname == null || currentNickname.isEmpty() || !authentication.isAuthenticated()) {
            // 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        BoardCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
        if (!comment.getAuthor().getNickname().equals(currentNickname)) {
            // 403 Forbidden (권한 없음)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 4. 삭제 로직
        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }

    private String extractNickname(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();

        // 1. 소셜 로그인 사용자 (DefaultOAuth2User) 처리
        if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
            // CustomOAuth2UserService에서 DB 닉네임을 설정한 'name' 속성을 사용
            return ((org.springframework.security.oauth2.core.user.DefaultOAuth2User) principal).getAttribute("name");
        }

        // 2. 일반 로그인 사용자 (UserDetails) 처리
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // UserDetails의 getUsername()이 닉네임 역할을 한다고 가정
            return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        }

        return null; // Fallback
    }

    private String extractAuthor(Object principal) {
        if (principal == null) {
            return null; // 로그인 정보 없음
        }

        // 1. 소셜 로그인 사용자 (DefaultOAuth2User) 처리
        if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
            // CustomOAuth2UserService에서 DB 닉네임을 설정한 'name' 속성을 사용합니다.
            return ((org.springframework.security.oauth2.core.user.DefaultOAuth2User) principal).getAttribute("name");
        }

        // 2. 일반 로그인 사용자 (UserDetails) 처리
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // UserDetails의 getUsername()이 닉네임 역할을 한다고 가정
            return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        }

        return null; // 예상치 못한 Principal 타입
    }
}
