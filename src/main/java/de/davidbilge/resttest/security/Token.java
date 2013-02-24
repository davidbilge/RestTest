package de.davidbilge.resttest.security;

import org.joda.time.DateTime;
import org.springframework.security.core.Authentication;

public final class Token {
	private DateTime expiration;
	private String authenticatedUser;
	private Authentication authentication;

	public boolean isExpired() {
		return expiration.isBeforeNow();
	}

	public DateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(DateTime expiration) {
		this.expiration = expiration;
	}

	public String getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(String authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
