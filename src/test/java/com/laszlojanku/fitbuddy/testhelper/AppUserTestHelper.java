package com.laszlojanku.fitbuddy.testhelper;

import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;

public class AppUserTestHelper {
	
	private static final Integer ID = 1;
	private static final String NAME = "userName";
	private static final String PASSWORD = "password";	
	
	public static AppUser getMockAppUser() {
		return getMockAppUser(ID, NAME, PASSWORD, RoleTestHelper.getMockRole()); 
	}
	
	public static AppUser getMockAppUser(Integer id, String name, String password) {
		return getMockAppUser(id, name, password, RoleTestHelper.getMockRole()); 
	}
	
	public static AppUser getMockAppUser(Integer id, String name, String password, Role role) {
		AppUser appUser = new AppUser();
		appUser.setId(id);
		appUser.setName(name);
		appUser.setPassword(password);
		appUser.setRole(role);
		return appUser;
	}
	
}
