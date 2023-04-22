package com.app.voting.controller;

import com.app.voting.dto.CandidateResult;
import com.app.voting.service.VotingResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/voting-results")
@RequiredArgsConstructor
public class VotingResultController {

    private final VotingResultService votingResultService;

    @GetMapping
    public ResponseEntity<List<CandidateResult>> getVotingSummaryCountSortedByVote() {
        List<CandidateResult> results = votingResultService.getVotingSummaryCountSortedByVote();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
