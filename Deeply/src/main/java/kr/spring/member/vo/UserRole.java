package kr.spring.member.vo;

import lombok.Getter;

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER"),ARTIST("ROLE_ARTIST");
	
	private String value;
	
	UserRole(String value){
		this.value = value;
	}
}


