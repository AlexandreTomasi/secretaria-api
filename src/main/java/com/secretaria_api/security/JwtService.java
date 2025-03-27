package com.secretaria_api.security;

import com.secretaria_api.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${secretaria.jwtSecret}")
    private String secretKey;

    @Value("${secretaria.jwtExpirationMs}")
    private int jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Map<String, String> generateToken(Usuario userDetails) {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome",userDetails.getNome());
        dados.put("id",userDetails.getId());
        dados.put("email",userDetails.getEmail());
        return generateToken(dados, userDetails);
    }

    public Map<String, String> generateToken(Map<String, Object> extraClaims, Usuario userDetails) {
        String accessToken = Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getLogin())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getLogin())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration+jwtExpiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public Map<String, String> refreshToken(String refreshToken) {
        // 1. Validação básica do token
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new RuntimeException("Refresh token não fornecido");
        }

        // 2. Verifica a validade do token (assinatura e expiração)
        if (!this.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }

        // 3. Extrai o username do token
        Usuario user = new Usuario();
        Claims claims = extractAllClaims(refreshToken);
        user.setLogin(claims.get("sub").toString());
        user.setNome(claims.get("nome").toString());
        user.setId(Long.parseLong(claims.get("id").toString()));
        if(claims.get("email") != null){
            user.setEmail(claims.get("email").toString());
        }

        return generateToken(user);
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}