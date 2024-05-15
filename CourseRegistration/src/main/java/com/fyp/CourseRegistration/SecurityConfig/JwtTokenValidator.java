package com.fyp.CourseRegistration.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator  extends OncePerRequestFilter {
//    @Autowired
//    private JwtConstant jwtConstant;
    public JwtTokenValidator(){ }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request);
        System.out.println("request hit to the jwt token validator" + request);
        String jwt = request.getHeader(JwtConstant.Auth_header);
        System.out.println(jwt);
        if(jwt != null){
            jwt = jwt.substring(7);
          try {
              SecretKey key = Keys.hmacShaKeyFor(JwtConstant.secret_Key.getBytes());
              Claims claims = Jwts.parser()
                      .setSigningKey(key)
                      .build()
                      .parseClaimsJws(jwt)
                      .getBody();
              String username = claims.getSubject();
              System.out.println("username from jwtToken validator " + username);
              String authorities = String.valueOf(claims.get("authorities"));
              System.out.println("authorities before converting from jwtToken validator " + authorities);
              List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
              Authentication authentication = new UsernamePasswordAuthenticationToken(
                      username,
                      null,
                      auths
              );
              System.out.println("authentication variable in jwt token validator" + authentication);
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }catch(Exception e){
                throw new BadCredentialsException("Invalid Token " + e);
          }

        }
        filterChain.doFilter(request, response);

    }
}
