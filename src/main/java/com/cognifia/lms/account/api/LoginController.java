package com.cognifia.lms.account.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.cognifia.lms.account.dto.LoginDTO;
import com.cognifia.lms.account.service.LoginService;

@RestController
@AllArgsConstructor
@RequestMapping("account/login")
@Tag(name = "User login Interface", description = "the API with login ")
public class LoginController {

    private LoginService service;

    @PostMapping(value = "",
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login information", description = "This will generate token String by login ")
    public LoginDTO login(
            @Parameter(description = "emailAddress", required = true) @RequestParam("username") String emailAddress,
            @Parameter(description = "password", required = true) @RequestParam("password") String password) {
        return service.login(emailAddress, password);
    }

    @PostMapping(value = "/string",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login information", description = "This will generate token String by login ")
    public String loginString(
            @Parameter(description = "emailAddress", required = true) @RequestParam("username") String emailAddress,
            @Parameter(description = "password", required = true) @RequestParam("password") String password) {
        return "success";
    }

}

