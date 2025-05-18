package com.example.demo.controllers;

import com.example.demo.config.JwtService;
import com.example.demo.models.types.LoginRequest;
import com.example.demo.utils.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ResponseService responseService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, ResponseService responseService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.responseService = responseService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        String jwt = jwtService.generateToken(loginRequest.getUsername());
        return responseService.generateResponse("JWT success", jwt);
    }

}
