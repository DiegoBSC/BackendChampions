package com.sport.system.play.champion.championservice.security.provider;

import com.sport.system.play.champion.championservice.security.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    private String secret = "PrQ@LKZ%z$b@deuHMkmEU";


    public String createToken(User user) {
        Map<String, Object> claims;
        AtomicReference<String> rolesUser = new AtomicReference<>("");
        user.getRoles().forEach(e ->{
            rolesUser.set(rolesUser + "," + e.getName().name());
        });

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(rolesUser.get());

        claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("authorities",
                grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));

        Date now = new Date();
        Date expire = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();

    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String getUserName(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return "Bad token";
        }
    }
}
