package com.app.voting.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "vote_casts")
public class VoteCast {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_cast_sequence")
    @SequenceGenerator(name = "vote_cast_sequence", sequenceName = "vote_cast_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Voter voter;

    @ManyToOne
    private Candidate candidate;

    public VoteCast(@NonNull Voter voter, @NonNull Candidate candidate) {
        this.voter = voter;
        this.candidate = candidate;
    }
}
