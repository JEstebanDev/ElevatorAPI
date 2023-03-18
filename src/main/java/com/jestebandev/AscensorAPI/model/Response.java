package com.jestebandev.AscensorAPI.model;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;
public record Response(
        Instant timeStamp,
        int statusCode,
        HttpStatus status,
        String message,
        Map<?, ?> data){}
