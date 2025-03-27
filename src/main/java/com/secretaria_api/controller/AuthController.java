package com.secretaria_api.controller;

import com.secretaria_api.dto.AuthResponse;
import com.secretaria_api.dto.AuthenticationDTO;
import com.secretaria_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Operações de login e gestão de tokens")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*@PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }*/


    @Operation(
            summary = "Autenticar usuário",
            description = "Gera um token JWT válido para credenciais corretas",
            responses = {@ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                                            value = """
                        {"authorization": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }"""))),
                        @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                            content = @Content(examples = @ExampleObject(value = """
                        {"status": 401, "error": "Unauthorized",   "message": "Credenciais inválidas" }""")))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais de acesso", required = true, content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """ 
                                    {"login": "alexandre","senha": "123456"}""")))
            @RequestBody AuthenticationDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }



    @Operation(summary = "Gerar novo Access Token", description = "Gera um novo access token com base no refresh token válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Novo token gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "403", description = "Refresh token inválido ou expirado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}