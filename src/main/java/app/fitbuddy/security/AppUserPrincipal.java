package app.fitbuddy.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import app.fitbuddy.entity.AppUser;

public class AppUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private final AppUser appUser;
	
	public AppUserPrincipal(AppUser appUser) {
		this.appUser = appUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(appUser.getRole().getName()));
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	@Override
	public String getUsername() {
		return appUser.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Integer getId() {
		return appUser.getId();
	}

}
