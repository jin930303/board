package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "likeslog")
public class LikesLogEntity {
    @Column
    @Id
    private Long id;
    @Column(name = "boardid")
    private Long boardId;
    @Column(name = "userid")
    private String userId;
}
