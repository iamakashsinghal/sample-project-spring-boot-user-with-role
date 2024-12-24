package com.loma.kkr.common.config;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This is class is Jwt Authentication Success Handler Just handle the request and response
 * @author Akash
 */
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		// We do not need to do anything extra on REST authentication success, because
		// there is no page to redirect to
	}

}