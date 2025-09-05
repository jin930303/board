package com.example.board.repository;

import com.example.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {

    int countById(String id);

    int countByNickname(String nickname);

    int countByEmail(String email);
}
