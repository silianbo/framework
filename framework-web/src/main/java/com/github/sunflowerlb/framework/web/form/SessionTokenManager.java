package com.github.sunflowerlb.framework.web.form;

import javax.servlet.http.HttpSession;

import com.github.sunflowerlb.framework.web.servlet.HttpServletHolder;

public class SessionTokenManager extends AbstractTokenManager {

	@Override
	public String newToken() {
		HttpSession session = HttpServletHolder.getCurrentRequest().getSession();
		String token = super.newToken();
		session.setAttribute(token, token);
		return token;
	}

	@Override
	public boolean checkToken(String token) {
		HttpSession session = HttpServletHolder.getCurrentRequest().getSession();
		return session.getAttribute(token) != null;
	}

	@Override
	public synchronized boolean checkAndDelToken(String token) {
		HttpSession session = HttpServletHolder.getCurrentRequest().getSession();
		if (checkToken(token)) {
			session.removeAttribute(token);
			return true;
		}
		return false;
	}

}
