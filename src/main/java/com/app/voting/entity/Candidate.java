package com.app.voting.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
    @SequenceGenerator(name = "candidate_seq", sequenceName = "candidate_seq", allocationSize = 1)
    private Long id;

    private String name;
}
