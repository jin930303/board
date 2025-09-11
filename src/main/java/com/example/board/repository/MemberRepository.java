package com.example.board.repository;

import com.example.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {


    int countByUsername(String username);

    int countByNickname(String nickname);

    int countByEmail(String email);

    @Query(value = "select * from member where username = :username",nativeQuery = true)
    Optional<MemberEntity> findByUsername(@Param("username") String username);
}
