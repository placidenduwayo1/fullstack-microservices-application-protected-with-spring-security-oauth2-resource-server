package fr.placide.springsecurityservice.securitymanager.jwtgenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTGeneratorServiceImp implements JWTGeneratorService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;
    @Value("${spring.application.name}")
    private String issuer;

    public static final short ACCESS_TOKEN=120;
    public static final short REFRESH_TOKEN=60*48;

    @Override
    public ResponseEntity<Map<String, Object>> generateToken(DtoToken dtoToken) {
        String subject = null;
        List<String> roles = Collections.emptyList();
        Map<String, Object> tokensId = new HashMap<>();
        if (dtoToken.getGrantType().equals("generate-access-token-from-username-and-password")) {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dtoToken.getUsername(), dtoToken.getPwd()));
            subject = auth.getName();
            roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
        } else if (dtoToken.getGrantType().equals("generate-access-token-from-refresh-token")) {
            if (dtoToken.getRefreshToken() == null) {
                return new ResponseEntity<>(Map.of("refresh-token-err", "missing refresh token"), HttpStatus.UNAUTHORIZED);
            }
            Jwt jwt;
            try {
                jwt = jwtDecoder.decode(dtoToken.getRefreshToken());
                subject = jwt.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
                roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
            } catch (JwtException e) {
                return new ResponseEntity<>(Map.of("token-error", e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }

        Instant instant = Instant.now();
        assert subject != null;
        JwtClaimsSet jwtClaimsSet1 = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(dtoToken.isWithRefreshToken() ? ACCESS_TOKEN : REFRESH_TOKEN, ChronoUnit.MINUTES))
                .issuer(issuer)
                .claim("scope", roles)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet1)).getTokenValue();

        if (dtoToken.isWithRefreshToken()) {
            JwtClaimsSet jwtClaimsSet2 = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuer(issuer)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(REFRESH_TOKEN, ChronoUnit.MINUTES))
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet2)).getTokenValue();
            tokensId.put("refresh-token", jwtRefreshToken);
        }

        tokensId.put("access-token", jwtAccessToken);
        return new ResponseEntity<>(tokensId, HttpStatus.OK);
    }
}
