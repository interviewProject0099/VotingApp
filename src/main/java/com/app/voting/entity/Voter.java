package com.app.voting.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "voters")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voter_seq")
    @SequenceGenerator(name = "voter_seq", sequenceName = "voter_seq", allocationSize = 1)
    private Long id;

    private String name;

    private boolean hasVoted;
}
