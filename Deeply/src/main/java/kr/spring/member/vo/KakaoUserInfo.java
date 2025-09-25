package kr.spring.member.vo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String,Object> attributes;
	
	public KakaoUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return String.valueOf((Long)attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return (String)((Map)attributes.get("kakao_account")).get("email");
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getNick_name() {
		return (String)((Map)attributes.get("properties")).get("nickname");
	}

}




