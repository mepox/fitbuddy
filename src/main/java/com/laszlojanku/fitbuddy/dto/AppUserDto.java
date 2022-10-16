package com.laszlojanku.fitbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUserDto {
	
	private Integer id;
	private String name;
	private String rolename;
	
}
