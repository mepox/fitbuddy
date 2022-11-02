package com.laszlojanku.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto {
	
	private Integer id;
	@NotBlank
	@Size(min = 4, max = 16)
	private String name;

}
