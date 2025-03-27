package com.sujeet.Blog.App.service;

import com.sujeet.Blog.App.dto.LoginDTO;
import com.sujeet.Blog.App.dto.RegisterDTO;
import com.sujeet.Blog.App.dto.UserDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDto);

    UserDTO getProfile(String token);
}
