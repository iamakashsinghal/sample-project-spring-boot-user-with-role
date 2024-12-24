package com.loma.kkr.common.config;


import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.entity.UserInfo;
import com.loma.kkr.common.repositories.RoleMasterRepository;
import com.loma.kkr.common.repositories.UserInfoRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

/**
 * Jwt Utility class purpose is create token, validate token
 * @author Akash
 */
@Component
public class JwtUtil implements InitializingBean {

	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.audience}")
	private String audience;

	@Value("${jwt.base64-secret}")
	private String base64Secret;

	@Value("${jwt.token-validity-in-seconds}")
	private long tokenValidityInMilliseconds;

	@Value("${jwt.token-validity-in-seconds-for-remember-me}")
	private long tokenValidityInMillisecondsForRememberMe;

	private Key key;

	private static final String AUTHORITIES_KEY = "auth";
	private static final String JWT_TYP_HEADER = "auth";
	private static final String JWT_TYP_HEADER_VALUE = "JWT";
	

	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
    UserInfoRepository userInfoRepository;

	/**
	 *
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);

		tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
		tokenValidityInMillisecondsForRememberMe = tokenValidityInMillisecondsForRememberMe * 1000;

	}

	// retrieve username from jwt token
	/**
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	/**
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	/**
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * @param authentication
	 * @param rememberMe
	 * @return
	 */
	public String createToken(Authentication authentication, boolean rememberMe) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		Date validity;
		if (rememberMe) {
			validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
		} else {
			validity = new Date(now + this.tokenValidityInMilliseconds);
		}

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.setIssuedAt(new Date(now)).setIssuer(issuer).setAudience(audience)
				.setHeaderParam(JWT_TYP_HEADER, JWT_TYP_HEADER_VALUE).signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity).compact();
	}

	/**
	 * @param userDetails
	 * @param rememberMe
	 * @return
	 */
	public String createToken(UserDetails userDetails, boolean rememberMe) {
//		String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//				.collect(Collectors.joining(","));
		UserInfo user = userInfoRepository.findByUserEmail(userDetails.getUsername());
        List<RoleMaster> roleMasters = roleMasterRepository.getRoleData(user.getUserId());
		
		List<GrantedAuthority> grantedAuthorities = roleMasters.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRoleName());
		}).collect(Collectors.toList());

		long now = (new Date()).getTime();

		Date validity;
		if (rememberMe) {
			validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);

		} else {
			validity = new Date(now + this.tokenValidityInMilliseconds);
		}

		return Jwts.builder().setSubject(userDetails.getUsername()).claim(AUTHORITIES_KEY, grantedAuthorities)
				.setIssuedAt(new Date(now)).setIssuer(issuer).setAudience(audience)
				.setHeaderParam(JWT_TYP_HEADER, JWT_TYP_HEADER_VALUE).signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity).compact();
	}

	// validate token
	/**
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public static void main(String[] args) {
		String base64Encoded = Encoders.BASE64
				.encode("LomaAppJWTSecret_LomaAppJWTSecret_LomaAppJWTSecret_LomaAppJWTSecret".getBytes());
		System.out.println(base64Encoded);

		byte[] base64Decoded = Decoders.BASE64.decode(base64Encoded);
		System.out.println(new String(base64Decoded));

		Key key = Keys.hmacShaKeyFor(base64Decoded);
		String token = Jwts.builder().setSubject("Username").claim(AUTHORITIES_KEY, "ROLE_USER").setIssuedAt(new Date())
				.setIssuer("loma-kkr").setAudience("loma-client").setHeaderParam(JWT_TYP_HEADER, JWT_TYP_HEADER_VALUE)
				.claim("active", "true").signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(new Date(new Date().getTime() + 60 * 60 * 1000)).compact();

		System.out.println("Generated JWT token - " + token);
	}
}