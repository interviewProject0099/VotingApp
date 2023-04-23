package com.app.voting.service;

import com.app.voting.dto.CandidateResult;
import com.app.voting.entity.Candidate;
import com.app.voting.repository.CandidateRepository;
import com.app.voting.repository.VoteCastRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotingResultServiceTest {

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Mock
    private VoteCastRepository voteCastRepositoryMock;

    @InjectMocks
    VotingResultService classUnderTest;

    @Test
    void getVotingSummaryCountSortedByVote_shouldReturnCandidateResultsSortedByVoteCount() {
        // given
        Candidate candidate1 = new Candidate();
        candidate1.setId(1L);
        candidate1.setName("Candidate 1");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2L);
        candidate2.setName("Candidate 2");

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate1);
        candidates.add(candidate2);

        when(candidateRepositoryMock.findAll()).thenReturn(candidates);
        when(voteCastRepositoryMock.countByCandidateId(1L)).thenReturn(3L);
        when(voteCastRepositoryMock.countByCandidateId(2L)).thenReturn(2L);

        // when
        List<CandidateResult> candidateResults = classUnderTest.getVotingSummaryCountSortedByVote();

        // when
        assertEquals(2, candidateResults.size());
        assertEquals("Candidate 1", candidateResults.get(0).candidateName());
        assertEquals(3L, candidateResults.get(0).voteCount());
        assertEquals("Candidate 2", candidateResults.get(1).candidateName());
        assertEquals(2L, candidateResults.get(1).voteCount());
    }
}
