package com.lb.framework.web.form;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class AbstractTokenManager implements TokenManager {

	@Override
	public String newToken() {
		String token = RandomStringUtils.randomAlphabetic(12);
		while (checkToken(token)) {
			token = RandomStringUtils.randomAlphabetic(12);
		}
		return token;
	}
}
