package com.laszlojanku.fitbuddy.testhelper;

import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.jpa.entity.Role;

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
	
	public static boolean isEqual(RoleDto roleDto, Role role) {
		return (roleDto.getId().equals(role.getId()) &&
				roleDto.getName().equals(role.getName()));
	}
	
	public static boolean isEqual(Role role, RoleDto roleDto) {
		return isEqual(roleDto, role);
	}

}
