package org.example.springsecurity_amigos_jwt.configuration.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springsecurity_amigos_jwt.entity.UserEntity;
import org.example.springsecurity_amigos_jwt.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwkService;
    UserDetailsService userDetailsService; ///it is bean that we have customized in the config class

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authenticationHeader = request.getHeader("Authorization"); //this string contains to the 'Bearer ' text and our jwt token

        if(authenticationHeader==null || !authenticationHeader.startsWith("Bearer ")){ //now we are sure the request contains the Jwt token on the header
            filterChain.doFilter(request,response);
            return;
        }

        final String jwkToken = authenticationHeader.substring(7);
        final String userEmail = jwkService.extractUserName(jwkToken);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null ){ // it checks is there an authenticated user
            UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userEmail);

            if (jwkService.isTokenValid(jwkToken , user)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user , null , user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
