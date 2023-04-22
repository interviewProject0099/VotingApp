package com.app.voting.repository;

import com.app.voting.entity.VoteCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteCastRepository extends JpaRepository<VoteCast, Long> {

    Optional<VoteCast> findByVoterId(Long voterId);

    long countByCandidateId(Long candidateId);
}

