package com.app.voting.controller;

import com.app.voting.dto.CandidateResult;
import com.app.voting.service.VotingResultService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(VotingResultController.class)
public class VotingResultControllerTest {

    private static final String URL_TEMPLATE = "/voting-results";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotingResultService votingResultServiceMock;

    @Test
    public void shouldReturnCorrectGetVotingSummaryCountSortedByVote() throws Exception {
        //given
        List<CandidateResult> testData = Arrays.asList(
                new CandidateResult("Candidate A", 10),
                new CandidateResult("Candidate B", 5),
                new CandidateResult("Candidate C", 3)
        );

        when(votingResultServiceMock.getVotingSummaryCountSortedByVote()).thenReturn(testData);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].candidateName").value("Candidate A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].voteCount").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].candidateName").value("Candidate B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].voteCount").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].candidateName").value("Candidate C"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].voteCount").value(3));
    }
}