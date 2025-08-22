package com.example.uberprojectauthservice.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService  implements CommandLineRunner{

	@Value("${jwt.expiry}")
	private int expiry;

	@Value("${jwt.secret}")
	private String SECRET;

	// This method creates a brand new JWT_TOKEN based on a payload
	private String createToken(Map<String, Object> payload, String email) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiry * 1000L);
		

		return Jwts.builder().claims(payload).issuedAt(new Date(System.currentTimeMillis())).expiration(expiryDate)
				.subject(email).signWith(getSignKey()).compact();
	}
	
	private Claims extractAllpayloads(String token) {
		 return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public <T> T extractClaim(String token, Function<Claims, T>claimsResolver) {
		final Claims claims= extractAllpayloads(token);
		return claimsResolver.apply(claims);
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//this method checks if the token expiry was before the current timestamp or not 
	private Boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
	}
	
	private Key getSignKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}
	
	private Boolean validateToken(String token, String email) {
		final String userEmailFetchedFromToken = extractEmail(token);
		return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token) );
	}
	
	private Object extractPayload(String token, String payloadKey) {
		 Claims claim = extractAllpayloads(token);
		 return (Object)claim.get(payloadKey);
	}
	
	@Override
	public void run(String... args ) throws Exception{
		Map<String,Object > mp = new HashMap<>();
		mp.put("email", "a@b.com");
		mp.put("phoneNumber", "9299292929");
		
		String result = createToken(mp, "Sudhanshu");
		System.out.println("Generated token is: "+ result);
		System.out.println(extractPayload(result, "email").toString());
	}
}
