package kr.spring.member.vo;

public interface OAuth2UserInfo {
	public String getProviderId();
	public String getProvider();
	public String getEmail();
	public String getName();
	public String getNick_name();
}
