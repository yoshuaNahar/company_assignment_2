package nl.gemvision.assignment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import nl.gemvision.assignment.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${nl.gemvision.assignment.jwtSecret}")
  private String jwtSecret;

  @Value("${nl.gemvision.assignment.jwtExpiration}")
  private int jwtExpiration;

  private UserDetailsService userDetailsService;

  @Autowired
  public JwtTokenProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @PostConstruct
  public void setJwtSecretB64Encoded() {
    this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
  }

  public String generateJwt(User user) {
    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("auth", user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
        .collect(Collectors.toList())
    );

    return Jwts.builder()
        .setIssuer("gemvision")
        .setSubject((user.getUsername()))
        .claim("scope", user.getRoles().stream().map(Enum::toString))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public boolean validateJws(String jws) {
    Jws<Claims> claims;

    try {
      claims = Jwts.parser()
          .setSigningKey(jwtSecret)
          .parseClaimsJws(jws);
    } catch (JwtException e) {
      throw new IllegalStateException("Expired or invalid JWT token");
    }

    String user = claims.getBody().getSubject();
    logger.info("jws token subject: {}", user);

    return true;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    logger.info("userDetails: {}", userDetails);
    logger.info("userDetails: {}", userDetails.getAuthorities());

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

}
