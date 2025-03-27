package com.sujeet.Blog.App.controller;

import com.sujeet.Blog.App.dto.JwtAuthResponse;
import com.sujeet.Blog.App.dto.LoginDTO;
import com.sujeet.Blog.App.dto.RegisterDTO;
import com.sujeet.Blog.App.dto.UserDTO;
import com.sujeet.Blog.App.exception.ApiException;
import com.sujeet.Blog.App.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // login user
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
        String response = authService.login(loginDTO);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(response);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // register user
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if (token == null) throw new ApiException(HttpStatus.BAD_REQUEST, "jwt token not found");

        String[] tokenSplit = token.split(" ");

        if (tokenSplit.length < 2) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid jwt token");
        }

        UserDTO userDto = this.authService.getProfile(tokenSplit[1]);

        return ResponseEntity.ok(userDto);
    }
}