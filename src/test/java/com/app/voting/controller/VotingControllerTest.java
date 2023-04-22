package com.app.voting.controller;

import com.app.voting.entity.Candidate;
import com.app.voting.entity.Voter;
import com.app.voting.exceptions.CandidateNotFoundException;
import com.app.voting.exceptions.UserCanVoteOnlyOnceException;
import com.app.voting.exceptions.VoterNotFoundException;
import com.app.voting.service.VotingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotingController.class)
public class VotingControllerTest {

    public static final String URL_TEMPLATE = "/voting/vote/";
    public static final String URL_TEMPLATE_POST_CANDIDATE = "/voting/candidates";
    public static final String URL_TEMPLATE_POST_VOTER = "/voting/voters";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotingService votingServiceMock;

    @Test
    public void shouldReturnAllCandidates() throws Exception {
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

        when(votingServiceMock.getAllCandidates()).thenReturn(candidates);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE_POST_CANDIDATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(candidates.size()));
    }

    @Test
    public void shouldReturnAllVoters() throws Exception {
        // given
        Voter voter1 = new Voter();
        voter1.setId(1L);
        voter1.setName("John Doe");

        Voter voter2 = new Voter();
        voter2.setId(2L);
        voter2.setName("Jane Doe");

        List<Voter> voters = new ArrayList<>();
        voters.add(voter1);
        voters.add(voter2);

        when(votingServiceMock.getAllVoters()).thenReturn(voters);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE_POST_VOTER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(voters.size()));
    }

    @Test
    public void shouldAddCandidate() throws Exception {
        // given
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Candidate 1");

        when(votingServiceMock.addCandidate(any(Candidate.class))).thenReturn(candidate);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE_POST_CANDIDATE)
                        .content("{\"id\":1,\"name\":\"Candidate 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(candidate.getId()))
                .andExpect(jsonPath("$.name").value(candidate.getName()));
    }

    @Test
    public void shouldAddVoter() throws Exception {
        // given
        Voter voter = new Voter();
        voter.setId(1L);
        voter.setName("Voter 1");

        when(votingServiceMock.addVoter(any(Voter.class))).thenReturn(voter);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE_POST_VOTER)
                        .content("{\"id\":1,\"name\":\"Voter 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(voter.getId()))
                .andExpect(jsonPath("$.name").value(voter.getName()));
    }

    @Test
    public void shouldReturnOkWhenVoteIsCastSuccessfully() throws Exception {
        // given
        Long voterId = 1L;
        Long candidateId = 1L;
        doNothing().when(votingServiceMock).castVote(voterId, candidateId);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE + voterId + "/" + candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenVoterIsNotFound() throws Exception {
        // given
        Long voterId = 1L;
        Long candidateId = 1L;
        doThrow(new VoterNotFoundException("Voter with ID " + voterId + " not found.")).when(votingServiceMock)
                .castVote(voterId, candidateId);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE + voterId + "/" + candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundWhenCandidateIsNotFound() throws Exception {
        // given
        Long voterId = 1L;
        Long candidateId = 1L;
        doThrow(new CandidateNotFoundException("Candidate with ID " + candidateId + " not found."))
                .when(votingServiceMock).castVote(voterId, candidateId);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE + voterId + "/" + candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestWhenVoterHasAlreadyVoted() throws Exception {
        // given
        Long voterId = 1L;
        Long candidateId = 1L;
        doThrow(new UserCanVoteOnlyOnceException("Voter with ID " + voterId + " has already cast a vote."))
                .when(votingServiceMock).castVote(voterId, candidateId);

        // when and then
        mockMvc.perform(post(URL_TEMPLATE + voterId + "/" + candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
