package br.com.michel.hercules.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.michel.hercules.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${michel.hercules.jjwt.expiration}")
	private String expiration;
	@Value("${michel.hercules.jjwt.secret}")
	private String secret;
	
	public String getToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
					.setIssuer("Hercules API")
					.setSubject(user.getId().toString())
					.setIssuedAt(today)
					.setExpiration(expirationDate)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		
	}
	
	public boolean isTokenValid(String token) {
		
		try {
			Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token);
			
			return true;
		} catch(Exception e) {
			return false;
		}
		
	}
	
	public Long getUserId(String token) {
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Long.parseLong(body.getSubject());
	}
	
}
