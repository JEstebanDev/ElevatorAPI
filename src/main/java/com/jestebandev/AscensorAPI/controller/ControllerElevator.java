package com.jestebandev.AscensorAPI.controller;

import com.jestebandev.AscensorAPI.model.Response;
import com.jestebandev.AscensorAPI.service.ServiceElevator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/elevator")
public class ControllerElevator {
    @Autowired
    private final ServiceElevator serviceElevator;

    @PostMapping("/moveElevator")
    @ResponseStatus(HttpStatus.OK)
    public Response moveElevator(int moveElevator) {
        return new Response(Instant.now(), HttpStatus.OK.value(), HttpStatus.OK,
                "Move Elevator Inside", Map.of("Elevator", serviceElevator.callInside(moveElevator)));
    }

    @GetMapping("/show")
    @ResponseStatus(HttpStatus.OK)
    public Response showInfo() {
        return new Response(Instant.now(), HttpStatus.OK.value(), HttpStatus.OK,
                "Show Elevator Information", Map.of("Elevator", serviceElevator.showInfo()));
    }
}
