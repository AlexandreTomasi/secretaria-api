package com.secretaria_api.service;

import com.secretaria_api.dto.AuthResponse;
import com.secretaria_api.dto.AuthenticationDTO;
import com.secretaria_api.repository.UsuarioRepository;
import com.secretaria_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String criptografa(String senha){
        return passwordEncoder.encode(senha);
    }

    /*public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }*/

    public AuthResponse login(AuthenticationDTO authenticationDTO) {
        var user = usuarioRepository.findByLogin(authenticationDTO.getLogin())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        // Verificação manual da senha
        if (!passwordEncoder.matches(authenticationDTO.getSenha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        Map<String, String> jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken.get("accessToken"), jwtToken.get("refreshToken"));
    }

    public AuthResponse refresh(String token){
        Map<String, String> jwtToken = jwtService.refreshToken(token);
        return new AuthResponse(jwtToken.get("accessToken"), jwtToken.get("refreshToken"));
    }

}