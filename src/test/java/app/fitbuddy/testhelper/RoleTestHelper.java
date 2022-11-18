package app.fitbuddy.testhelper;

import app.fitbuddy.dto.role.RoleResponseDTO;
import app.fitbuddy.entity.Role;

public class RoleTestHelper {
	
	private static final Integer ID = 1;
	private static final String NAME = "roleName";
	
	public static Role getMockRole() {
		return getMockRole(ID, NAME);
	}
	
	public static Role getMockRole(Integer id, String name) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		return role;
	}
	
	public static boolean isEqual(RoleResponseDTO roleResponseDTO, Role role) {
		return (roleResponseDTO.getId().equals(role.getId()) &&
				roleResponseDTO.getName().equals(role.getName()));
	}
	
	public static boolean isEqual(Role role, RoleResponseDTO roleResponseDTO) {
		return isEqual(roleResponseDTO, role);
	}

}
