package com.app.voting.controller;

import com.app.voting.entity.Candidate;
import com.app.voting.entity.Voter;
import com.app.voting.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/voting")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = votingService.getAllCandidates();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/voters")
    public ResponseEntity<List<Voter>> getAllVoters() {
        List<Voter> voters = votingService.getAllVoters();
        return new ResponseEntity<>(voters, HttpStatus.OK);
    }

    @PostMapping("/candidates")
    public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        Candidate createdCandidate = votingService.addCandidate(candidate);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @PostMapping("/voters")
    public ResponseEntity<Voter> addVoter(@RequestBody Voter voter) {
        Voter createdVoter = votingService.addVoter(voter);
        return new ResponseEntity<>(createdVoter, HttpStatus.CREATED);
    }

    @PostMapping("/vote/{voterId}/{candidateId}")
    public ResponseEntity<String> castVote(@PathVariable Long voterId, @PathVariable Long candidateId) {
        votingService.castVote(voterId, candidateId);
        return new ResponseEntity<>("Vote cast successfully", HttpStatus.OK);
    }
}
