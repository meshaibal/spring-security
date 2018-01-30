package com.shaibal.spring.security.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	static final String AUTH_TOKEN = "x-auth-token";

	public static final int STATUS_INVALID_SESSION = 419;
	
	@Autowired
    private SessionRepository sessionRepository;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		System.out.println(getAuthToken(request));
		//checkValidSession(getAuthToken(request));
		String authToken = getAuthToken(request);
		if (authToken != null && !checkValidSession(authToken)) {
            // Session is expired or invalid; return custom HTTP code.
            response.sendError(STATUS_INVALID_SESSION);
        } else if (exception != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
	}
	
	static String getAuthToken(HttpServletRequest request) {
        String authToken = null;
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            if (name.equalsIgnoreCase(AUTH_TOKEN)) {
                authToken = request.getHeader(name);
                break;
            }
        }
        return authToken;
    }
	
	boolean checkValidSession(String authToken) {
        boolean valid = false;
        Session session = sessionRepository.getSession(authToken);
        if (session == null) {
            //LOG.debug("Session not found: {}", authToken);
        } else if (session instanceof ExpiringSession) {
            ExpiringSession s = (ExpiringSession) session;
            if (s.isExpired()) {
                //LOG.debug("Session is expired: {}", authToken);
            } else {
                //LOG.debug("Session is valid: {}", authToken);
                valid = true;
            }
        }
        return valid;
    }

}
