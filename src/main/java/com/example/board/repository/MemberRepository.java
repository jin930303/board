package com.example.board.repository;

import com.example.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {


    int countByUsername(String username);

    int countByNickname(String nickname);

    int countByEmail(String email);

    Optional<MemberEntity> findByUsername(String username);
}
