package com.app.voting.service;

import com.app.voting.entity.Candidate;
import com.app.voting.entity.VoteCast;
import com.app.voting.entity.Voter;
import com.app.voting.exceptions.CandidateNotFoundException;
import com.app.voting.exceptions.UserCanVoteOnlyOnceException;
import com.app.voting.exceptions.VoterNotFoundException;
import com.app.voting.repository.CandidateRepository;
import com.app.voting.repository.VoteCastRepository;
import com.app.voting.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VotingService {

    private final VoterRepository voterRepository;

    private final CandidateRepository candidateRepository;

    private final VoteCastRepository voteCastRepository;

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }


    public Voter addVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    @Transactional
    public void castVote(Long voterId, Long candidateId) {
        Voter voter = getVoterById(voterId);
        checkIfVoterHasAlreadyVoted(voter);
        Candidate candidate = getCandidateById(candidateId);
        saveVote(voter, candidate);
        updateVoterHasVoted(voter);
        log.info("Vote cast successfully by Voter with ID {}", voterId);
    }

    private Voter getVoterById(Long voterId) {
        return voterRepository.findById(voterId).orElseThrow(() -> {
            String message = String.format("Voter with ID %d not found.", voterId);
            log.warn(message);
            return new VoterNotFoundException(message);
        });
    }

    private Candidate getCandidateById(Long candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(() -> {
            String message = String.format("Candidate with ID %d not found.", candidateId);
            log.warn(message);
            return new CandidateNotFoundException(message);
        });
    }

    private void checkIfVoterHasAlreadyVoted(Voter voter) {
        if (voter.isHasVoted()) {
            String message = String.format("Voter with ID %d has already cast a vote.", voter.getId());
            log.warn(message);
            throw new UserCanVoteOnlyOnceException(message);
        }
    }

    private void saveVote(Voter voter, Candidate candidate) {
        voteCastRepository.save(new VoteCast(voter, candidate));
    }

    private void updateVoterHasVoted(Voter voter) {
        voter.setHasVoted(true);
        voterRepository.save(voter);
    }
}
