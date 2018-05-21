package com.shaibal.spring.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "login")
@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private HttpServletRequest request;
	
	@GetMapping(value = "/authenticate")
	public String authenticate() {
		GrantedAuthority authority =  new SimpleGrantedAuthority("ADMIN");
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		grantedAuths.add(authority);
		
		invalidateSession();		
		HttpSession session = request.getSession(true);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken("", "", grantedAuths);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        return getSessionId();
	}
	
	public String getSessionId() {
        return request.getSession(true).getId();
    }
	
	public void invalidateSession() {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
