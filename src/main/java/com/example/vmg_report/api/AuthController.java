package com.example.vmg_report.api;

import com.example.vmg_report.model.JwtResponseDto;
import com.example.vmg_report.model.user.UserDto;
import com.example.vmg_report.service.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(userDto.getEmail())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
