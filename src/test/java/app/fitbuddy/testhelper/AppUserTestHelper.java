package app.fitbuddy.testhelper;

import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Role;

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
	
	public static boolean isEqual(AppUserResponseDTO appUserResponseDTO, AppUser appUser) {
		return (appUserResponseDTO.getId().equals(appUser.getId()) &&
				appUserResponseDTO.getName().equals(appUser.getName()) &&
				appUserResponseDTO.getPassword().equals(appUser.getPassword()) &&
				appUserResponseDTO.getRolename().equals(appUser.getRole().getName()));
	}
	
	public static boolean isEqual(AppUser appUser, AppUserResponseDTO appUserResponseDTO) {
		return isEqual(appUserResponseDTO, appUser);
	}
	
}
