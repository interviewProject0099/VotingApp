package com.app.voting.repository;

import com.app.voting.entity.VoteCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteCastRepository extends JpaRepository<VoteCast, Long> {

    long countByCandidateId(Long candidateId);
}

