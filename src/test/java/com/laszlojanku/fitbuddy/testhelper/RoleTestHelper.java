package com.laszlojanku.fitbuddy.testhelper;

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

}
