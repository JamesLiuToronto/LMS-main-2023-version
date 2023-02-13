package com.cognifia.lms.tokenprocess;

import com.cognifia.lms.common.dto.ResultStatus;
import com.cognifia.lms.common.dto.SimpleResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("process")
@Tag(name = "This is token process general interface", description = "the API with token process general interface")
public class TokenProcessController {

    @Autowired
    TokenProcessPort service;

    @Autowired
    TokenServicePort tokenService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{token}")
    @Operation(summary = "the API with token process general",
               description = "This service is the API with token process general")
    public SimpleResultDTO tokenProcess(@Parameter(description = "token", required = true) @PathVariable("token") String token) {
        service.process(token);
        return SimpleResultDTO.builder()
                .status(ResultStatus.SUCCESS.name())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/encrypt-message/{message}")
    @Operation(summary = "the API with generate encrypted message",
               description = "This service is the API encrypting, message")
    public String getEncryptedMessage(
            @Parameter(description = "message", required = true) @PathVariable("message") String message) {
        return tokenService.getEncryptedMessage(message);
    }
}

