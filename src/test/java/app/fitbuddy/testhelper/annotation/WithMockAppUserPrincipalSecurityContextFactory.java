package app.fitbuddy.testhelper.annotation;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Role;
import app.fitbuddy.security.AppUserPrincipal;

public class WithMockAppUserPrincipalSecurityContextFactory 
		implements WithSecurityContextFactory<WithMockAppUserPrincipal> {

	@Override
	public SecurityContext createSecurityContext(WithMockAppUserPrincipal withMockAppUserPrincipal) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		
		Role role = new Role();
		role.setId(1);
		role.setName(withMockAppUserPrincipal.authority());
		
		AppUser appUser = new AppUser();
		appUser.setId(withMockAppUserPrincipal.id());
		appUser.setName(withMockAppUserPrincipal.name());
		appUser.setPassword(withMockAppUserPrincipal.password());
		appUser.setRole(role);		
		
		AppUserPrincipal principal = new AppUserPrincipal(appUser);
		
		List<GrantedAuthority> authorities = List.of(
				new SimpleGrantedAuthority(withMockAppUserPrincipal.authority()));
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, 
				withMockAppUserPrincipal.password(), authorities);
		
		context.setAuthentication(authentication);
		
		return context;
	}

}
