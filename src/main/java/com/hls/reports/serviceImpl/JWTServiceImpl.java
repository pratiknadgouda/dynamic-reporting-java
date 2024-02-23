package com.hls.reports.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hls.reports.response.JwtResponse;
import com.hls.reports.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	@Value("${rp.jwtSecret}")
	private String	jwtSecret;
	@Value("${rp.jwtExpirationMs}")
	private Long	expires_in;
	
//	public String generateToken(UserDetails userDetails) {
//		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(
//				new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//	}

	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(key);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
	}

	private boolean isTokenExpired(String token) {	
		return extractClaim(token, Claims:: getExpiration).before(new Date());
	}
	public JwtResponse generateToken(String email, String userName, String role) {
		String token = createToken(role, email, userName);
		return JwtResponse.builder().expires_in(expires_in).access_token(token).build();
	}

	private String createToken(String claims, String email, String userName) {
		return Jwts.builder().claim("role", claims).setSubject(userName).claim("email", email)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expires_in))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
}
