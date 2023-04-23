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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotingServiceTest {

    @Mock
    private VoterRepository voterRepositoryMock;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Mock
    private VoteCastRepository voteCastRepositoryMock;

    @InjectMocks
    private VotingService classUnderTest;

    private Voter voter;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        voter = new Voter();
        voter.setId(1L);
        voter.setName("John Doe");
        voter.setHasVoted(false);

        candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Candidate 1");
    }

    @Test
    void shouldReturnAllCandidates() {
        // given
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(candidateRepositoryMock.findAll()).thenReturn(candidates);

        // when
        List<Candidate> result = classUnderTest.getAllCandidates();

        // then
        assertEquals(1, result.size());
        assertEquals("Candidate 1", result.get(0).getName());
    }

    @Test
    void shouldReturnAllVoters() {
        // given
        List<Voter> voters = new ArrayList<>();
        voters.add(voter);

        when(voterRepositoryMock.findAll()).thenReturn(voters);

        // when
        List<Voter> result = classUnderTest.getAllVoters();

        // then
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void shouldAddCandidate() {
        // given
        when(candidateRepositoryMock.save(candidate)).thenReturn(candidate);

        // when
        Candidate result = classUnderTest.addCandidate(candidate);

        // then
        assertEquals(1L, result.getId());
        assertEquals("Candidate 1", result.getName());
    }

    @Test
    void shouldAddVoter() {
        // given
        when(voterRepositoryMock.save(voter)).thenReturn(voter);

        // when
        Voter result = classUnderTest.addVoter(voter);

        // then
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void shouldCastVoteSuccessfully() {
        // given
        voter.setId(1L);
        voter.setName("John Doe");
        voter.setHasVoted(false);

        candidate.setId(1L);
        candidate.setName("Candidate 1");

        when(voterRepositoryMock.findById(1L)).thenReturn(Optional.of(voter));
        when(candidateRepositoryMock.findById(1L)).thenReturn(Optional.of(candidate));
        when(voteCastRepositoryMock.save(any(VoteCast.class))).thenReturn(new VoteCast(voter, candidate));

        // when
        classUnderTest.castVote(1L, 1L);

        // then
        assertTrue(voter.isHasVoted());
    }

    @Test
    void shouldNotCastVoteIfVoterAlreadyVoted() {
        // given
        voter.setId(2L);
        voter.setName("Jane Smith");
        voter.setHasVoted(true);
        candidate.setId(2L);
        candidate.setName("Candidate 2");

        when(voterRepositoryMock.findById(2L)).thenReturn(Optional.of(voter));

        // when
        assertThrows(UserCanVoteOnlyOnceException.class, () -> classUnderTest.castVote(2L, 2L));

        // then
        verify(voteCastRepositoryMock, never()).save(any(VoteCast.class));
    }

    @Test
    void shouldNotCastVoteIfVoterNotFound() {
        // given
        when(voterRepositoryMock.findById(3L)).thenReturn(Optional.empty());

        // when
        assertThrows(VoterNotFoundException.class, () -> classUnderTest.castVote(3L, 3L));

        // then
        verify(voteCastRepositoryMock, never()).save(any(VoteCast.class));
    }

    @Test
    void shouldNotCastVoteIfCandidateNotFound() {
        // given
        when(voterRepositoryMock.findById(4L)).thenReturn(Optional.of(voter));
        when(candidateRepositoryMock.findById(4L)).thenReturn(Optional.empty());

        // when
        assertThrows(CandidateNotFoundException.class, () -> classUnderTest.castVote(4L, 4L));

        // then
        verify(voteCastRepositoryMock, never()).save(any(VoteCast.class));
    }

    @Test
    void shouldNotCastVoteIfBothVoterAndCandidateNotFound() {
        // given
        when(voterRepositoryMock.findById(5L)).thenReturn(Optional.empty());

        // when
        assertThrows(VoterNotFoundException.class, () -> classUnderTest.castVote(5L, 5L));

        // then
        verify(voteCastRepositoryMock, never()).save(any(VoteCast.class));
    }
}

