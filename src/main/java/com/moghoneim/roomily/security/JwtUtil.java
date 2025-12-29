package com.moghoneim.roomily.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    private Environment env;

    private RSAPrivateKey getPrivateKey() throws Exception {
        String privateKeyPath = env.getProperty("jwt.private-key-path");
        File privateKeyFile = new File(privateKeyPath);
        byte[] keyBytes = Files.readAllBytes(privateKeyFile.toPath());

        // If the key is in PEM format, decode it
        String pem = new String(keyBytes, StandardCharsets.UTF_8);
        pem = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""); // Remove all whitespace

        byte[] decodedKey = Base64.getDecoder().decode(pem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(decodedKey));
    }

    private RSAPublicKey getPublicKey() throws Exception {
        String publicKeyPath = env.getProperty("jwt.public-key-path");
        File publicKeyFile = new File(publicKeyPath);
        byte[] keyBytes = Files.readAllBytes(publicKeyFile.toPath());

        // If the key is in PEM format, decode it
        String pem = new String(keyBytes, StandardCharsets.UTF_8);
        pem = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // Remove all whitespace

        byte[] decodedKey = Base64.getDecoder().decode(pem);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(decodedKey));
    }

    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) throws Exception {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expirationTime = new Date(now + 1000 * 60 * 60);
        return Jwts.
                builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws Exception {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

}

