package com.fyp.CourseRegistration.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Component
public class JwtTokenProvider {
//    @Autowired
//    private JwtConstant jwtConstant;
    private SecretKey key;
//    private Authentication authentic;
//    public String getSecretKey(){
//        return jwtConstant.getSecretKey();
//    }
//    public String getAuthHeader(){
//        return jwtConstant.getAuth_header();
//    }
    public String generateToken(Authentication auth){
        key = Keys.hmacShaKeyFor(JwtConstant.secret_Key.getBytes()) ;

        String jwt = Jwts.builder()
                .setSubject(auth.getName()) // basically add a username here
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ 86400000))
                .claim("authorities", auth.getAuthorities()) // authorities for the specified roles
                .signWith(key)
                .compact();
        System.out.println( jwt);
        return jwt;

    }
    public String getUsernameFromToken(String token){
        key = Keys.hmacShaKeyFor(JwtConstant.secret_Key.getBytes());
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

    public List<String> findRole(String token){
        key = Keys.hmacShaKeyFor(JwtConstant.secret_Key.getBytes());
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<Map<String, String>> authorities = claims.get("authorities", List.class);

        // Extracting role data from authorities
        List<String> roles = authorities.stream()
                .map(authority -> authority.get("authority"))
                .collect(Collectors.toList());


        return roles;

    }
    public boolean getAuthorities(String token){
        key = Keys.hmacShaKeyFor(JwtConstant.secret_Key.getBytes());
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<?> authorities = (List<?>) claims.get("authorities");

    // Check if the list contains ROLE_STUDENT
    if (authorities != null) {
        for (Object authority : authorities) {
            if (authority instanceof Map<?,?>) {
                // If the authority is a map, try to extract the authority name
                Object authorityName = ((Map<?, ?>) authority).get("authority");
                if (authorityName instanceof String && "Role_STUDENT".equals(authorityName)) {
                    return true;
                }
            } else if (authority instanceof String && "Role_STUDENT".equals(authority)) {
                // If the authority is a string, check if it's ROLE_STUDENT
                return true;
            }
        }
    }

    return false;


    }







}
