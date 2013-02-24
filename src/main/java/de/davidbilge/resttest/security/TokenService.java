package de.davidbilge.resttest.security;

import org.springframework.security.core.Authentication;

public interface TokenService {

	Authentication getAuthentication(String token);

	void authenticate(Authentication authentication, String token);

}
