package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "likeslog")
@SequenceGenerator(name = "likelog_seq",sequenceName = "likelog_seq",allocationSize = 1,initialValue = 1)
public class LikesLogEntity {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "likelog_seq")
    private Long id;
    @Column(name = "boardid")
    private Long boardId;
    @Column(name = "userid")
    private String userId;
}
