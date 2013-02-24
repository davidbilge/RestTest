package de.davidbilge.resttest.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.davidbilge.resttest.security.TokenService;

@Controller
@RequestMapping("/tokens")
public class TokenController {

	@Resource
	private TokenService tokenService;
	private SecureRandom prng;

	public TokenController() throws NoSuchAlgorithmException {
		prng = SecureRandom.getInstance("SHA1PRNG");
	}

	@Secured("ROLE_TRUSTED_AUTHENTICATOR")
	@RequestMapping(value = "/{username}/{tokenCode}", method = RequestMethod.PUT)
	public void authenticateWithToken(@PathVariable String tokenCode,
			@PathVariable String username, @RequestBody String password) {

		tokenService.authenticate(new UsernamePasswordAuthenticationToken(username, password),
				tokenCode);
	}

	@ResponseBody
	@RequestMapping(value = "/{username}/", method = RequestMethod.PUT)
	public String authenticate(@PathVariable String username, @RequestBody String password) {
		byte[] randomData = new byte[512];
		prng.nextBytes(randomData);
		String token = Base64.encodeBase64URLSafeString(Sha2Crypt.sha256Crypt(randomData)
				.getBytes());

		tokenService.authenticate(new UsernamePasswordAuthenticationToken(username, password),
				token);

		return token;
	}

}
