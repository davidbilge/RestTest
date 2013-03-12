package de.davidbilge.resttest.security;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class InMemoryTokenService implements TokenService {
	private Map<String, Token> authenticationCache = new HashMap<>();

	private AuthenticationManager authenticationManager;

	private int tokenExpirationSeconds;

	@PostConstruct
	public void init() {
		// Provide a default authentication token. For testing purposes only!
		authenticate(new UsernamePasswordAuthenticationToken("Test-User", "Password123"), "___TEST_TOKEN___");
	}

	@Override
	public Authentication getAuthentication(String tokenCode) {
		Token token = authenticationCache.get(tokenCode);
		if (token == null) {
			return null;
		} else if (token.isExpired()) {
			authenticationCache.remove(tokenCode);
			return null;
		} else {
			return token.getAuthentication();
		}
	}

	@Override
	public void authenticate(Authentication authentication, String tokenCode) {
		Authentication previousAuth = getAuthentication(tokenCode);

		Authentication authdAuthentication;
		if (previousAuth == null) {
			authdAuthentication = authenticationManager.authenticate(authentication);
		} else {
			authdAuthentication = previousAuth;
		}

		Token t = new Token();
		t.setAuthenticatedUser(authdAuthentication.getName());
		t.setAuthentication(authdAuthentication);
		t.setExpiration(DateTime.now().plusSeconds(getTokenExpirationSeconds()));

		authenticationCache.put(tokenCode, t);
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public int getTokenExpirationSeconds() {
		return tokenExpirationSeconds;
	}

	public void setTokenExpirationSeconds(int tokenExpirationSeconds) {
		this.tokenExpirationSeconds = tokenExpirationSeconds;
	}

}
