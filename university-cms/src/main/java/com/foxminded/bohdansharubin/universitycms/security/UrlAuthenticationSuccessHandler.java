package com.foxminded.bohdansharubin.universitycms.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.universitycms.models.Roles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private Map<String, String> roleTargetUrlMap;
	private RedirectStrategy redirectStrategy;
	
	public UrlAuthenticationSuccessHandler() {
		redirectStrategy = new DefaultRedirectStrategy();
		roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put(Roles.ROLE_ADMIN.getAuthority(), "/admin-panel");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		
		handle(request, response, authentication);
	}
	
	 private void handle(final HttpServletRequest request,
		final HttpServletResponse response,
		final Authentication authentication) throws IOException {
		 
	    final String targetUrl = determineTargetUrl(authentication);
	    if (response.isCommitted()) {
	    	log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
	        return;
	    }
	    redirectStrategy.sendRedirect(request, response, targetUrl);
	 }
	 
	 private String determineTargetUrl(final Authentication authentication) {
		 return authentication.getAuthorities()
			.stream()
	        .map(authority -> roleTargetUrlMap.getOrDefault(authority.getAuthority(), "/user/home"))
	        .findFirst()
	        .orElseThrow(() -> new IllegalStateException());
	    }

}
