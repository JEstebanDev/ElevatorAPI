package com.jestebandev.AscensorAPI.controller;

import com.jestebandev.AscensorAPI.model.Call;
import com.jestebandev.AscensorAPI.model.Response;
import com.jestebandev.AscensorAPI.service.ServiceElevator;
import jdk.jfr.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ControllerElevator {
    @Autowired
    private final ServiceElevator serviceElevator;

    @PostMapping("/callElevator")
    @ResponseStatus(HttpStatus.OK)
    public Response callElevator(Call call) {
        serviceElevator.callOutSide(call);
        return new Response(Instant.now(), HttpStatus.OK.value(), HttpStatus.OK,
                "Move Elevator Inside", Map.of("Elevator", "Wait elevator..."));
    }

    @PostMapping("/moveElevator")
    @ResponseStatus(HttpStatus.OK)
    public Response moveElevator(int moveElevator) {
        serviceElevator.callInside(moveElevator);
        return new Response(Instant.now(), HttpStatus.OK.value(), HttpStatus.OK,
                "Move Elevator Inside", Map.of("Elevator", "Moving elevator to" + moveElevator));
    }

    @GetMapping("/show")
    @ResponseStatus(HttpStatus.OK)
    public Response showInfo() {
        return new Response(Instant.now(), HttpStatus.OK.value(), HttpStatus.OK,
                "Show Elevator Information", Map.of("Elevator", serviceElevator.showInfo()));
    }
}
