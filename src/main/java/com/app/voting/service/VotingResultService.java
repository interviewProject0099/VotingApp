package com.app.voting.service;

import com.app.voting.dto.CandidateResult;
import com.app.voting.entity.Candidate;
import com.app.voting.repository.CandidateRepository;
import com.app.voting.repository.VoteCastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VotingResultService {

    private final CandidateRepository candidateRepository;
    private final VoteCastRepository voteCastRepository;

    public List<CandidateResult> getVotingSummaryCountSortedByVote() {
        return candidateRepository.findAll()
                .stream()
                .map(candidate ->
                        new CandidateResult(
                                candidate.getName(),
                                voteCastRepository.countByCandidateId(candidate.getId())))
                .sorted(comparing(CandidateResult::voteCount).reversed())
                .collect(toList());
    }
}
