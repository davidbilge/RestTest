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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class TokenProcessingFilter extends GenericFilterBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenProcessingFilter.class);

	private AuthenticationManager authenticationManager;

	private UserDetailsService userDetailsService;

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
		}

		// Very dumb authentication scheme for first try
		UserDetails userDetails = getUserDetailsService().loadUserByUsername("Test-User");
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), "Password123");
		authToken.setDetails(new WebAuthenticationDetailsSource()
				.buildDetails((HttpServletRequest) request));
		SecurityContextHolder.getContext().setAuthentication(
				getAuthenticationManager().authenticate(authToken));

		LOGGER.info("Authenticated user " + userDetails.getUsername());

		// Continue chain
		chain.doFilter(request, response);
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
