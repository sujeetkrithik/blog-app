package com.sujeet.Blog.App.service;


import com.sujeet.Blog.App.dto.LoginDTO;
import com.sujeet.Blog.App.dto.RegisterDTO;
import com.sujeet.Blog.App.dto.UserDTO;
import com.sujeet.Blog.App.entity.Role;
import com.sujeet.Blog.App.entity.User;
import com.sujeet.Blog.App.exception.ApiException;
import com.sujeet.Blog.App.repository.RoleRepository;
import com.sujeet.Blog.App.repository.UserRepository;
import com.sujeet.Blog.App.security.JwtTokenProvider;
import com.sujeet.Blog.App.utils.UserRoles;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper mapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        // check if username or email already exists in database
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(UserRoles.ROLE_INDEX + UserRoles.USER).get();
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public UserDTO getProfile(String token) {
        String username = this.jwtTokenProvider.getUsername(token);

        User user = userRepository.findByUsernameOrEmail(username, username).
                orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user does not exist"));

        return mapper.map(user, UserDTO.class);
    }
}