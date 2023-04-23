package com.app.voting.exceptions;

public class VoterNotFoundException extends RuntimeException {

    public VoterNotFoundException(String message) {
        super(message);
    }
}

