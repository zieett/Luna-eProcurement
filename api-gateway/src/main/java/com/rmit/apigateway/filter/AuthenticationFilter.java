package com.rmit.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.apigateway.exception.InvalidTokenException;
import com.rmit.apigateway.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private JWTUtil jwtUtil;
    public AuthenticationFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = null;
            if(routeValidator.isSecured.test(exchange.getRequest())){
                //header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new InvalidTokenException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!= null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
                    jwtUtil.validateToken(authHeader);
//                    restTemplate.getForObject("http://auth-service/auth/validateToken?token="+authHeader,String.class);
                        request = exchange.getRequest()
                            .mutate()
                            .header("userInfo",
                                new ObjectMapper().writeValueAsString(jwtUtil.extractAllClaims(authHeader)))
                            .build();
                }
                catch (ExpiredJwtException e){
                    throw new InvalidTokenException(e.getMessage(),e);
                }
                catch (RuntimeException | SignatureException | JsonProcessingException e){
                    log.error(e.getMessage());
                    throw new InvalidTokenException(e.getMessage(),e);
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        }));
    }

    public static class Config{

    }
}
