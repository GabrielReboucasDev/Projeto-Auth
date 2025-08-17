package com.developer.auth.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

	ADMIN("admin"), USER("user");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

}
