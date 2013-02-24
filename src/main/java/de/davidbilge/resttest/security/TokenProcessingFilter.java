package de.davidbilge.resttest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class TokenProcessingFilter extends GenericFilterBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenProcessingFilter.class);

	private AuthenticationManager authenticationManager;

	private TokenService tokenService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only handle HTTP requests");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String tokenHeader = httpRequest.getHeader("token");

		if (tokenHeader != null && !"".equals(tokenHeader)) {
			LOGGER.info("Found token header with value \"" + tokenHeader + "\".");
			Authentication authentication = tokenService.getAuthentication(tokenHeader);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				LOGGER.info("Authenticated user " + authentication.getName());
			}
		}

		// Continue chain
		chain.doFilter(request, response);
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

}
