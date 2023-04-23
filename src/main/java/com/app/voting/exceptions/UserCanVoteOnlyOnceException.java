package com.app.voting.exceptions;

public class UserCanVoteOnlyOnceException extends RuntimeException {

    public UserCanVoteOnlyOnceException(String message) {
        super(message);
    }
}
