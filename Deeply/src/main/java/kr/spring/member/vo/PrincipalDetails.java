package kr.spring.member.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
/*
 * 시큐리티가 /member/login 주소 요청이 오면 낚아채서 로그인을 진행
 * 로그인 진행이 완료되면 시큐리티 session을 만듬
 */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private Map<String, Object> attributes;
	MemberVO memberVO;
	ArtistVO artistVO;
	AgroupVO agroupVO;
	public PrincipalDetails(ArtistVO artistVO) {
		this.artistVO=artistVO;
	}
	public PrincipalDetails(MemberVO memberVO) {
		this.memberVO=memberVO;
	}
	
	public PrincipalDetails(AgroupVO agroupVO) {
		this.agroupVO=agroupVO;
	}
	public PrincipalDetails(MemberVO memberVO, Map<String, Object> attributes) {
		this.memberVO=memberVO;
		this.attributes=attributes;
	}
	
	public PrincipalDetails(AgroupVO agroupVO, Map<String, Object> attributes) {
		this.agroupVO=agroupVO;
		this.attributes=attributes;
	}
	
	
	public PrincipalDetails(ArtistVO artistVO, Map<String, Object> attributes) {
		this.artistVO=artistVO;
		this.attributes=attributes;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public ArtistVO getArtistVO() {
		return artistVO;
	}
	public AgroupVO agroupVO() {
		return agroupVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Collection<GrantedAuthority> collect = new ArrayList<>();

	    // 일반 사용자 (memberVO) 권한 처리
	    if (memberVO != null) {
	        collect.add(new GrantedAuthority() {
	            @Override
	            public String getAuthority() {
	                if (memberVO.getAuth() == 9) {
	                    return UserRole.ADMIN.getValue(); // 관리자 권한
	                } else {
	                    return UserRole.USER.getValue(); // 일반 사용자 권한
	                }
	            }
	        });
	    }

	    // 아티스트 (artistVO) 권한 처리
	    if (artistVO != null) {
	        collect.add(new GrantedAuthority() {
	            @Override
	            public String getAuthority() {
	                return UserRole.ARTIST.getValue(); // 아티스트 권한
	            }
	        });
	    }

	    return collect;
	}

	@Override // password
	public String getPassword() {
		if(memberVO!=null) return memberVO.getPasswd_hash();
		else return artistVO.getPasswd_hash();
	}

	@Override // name
	public String getUsername() {
		if(memberVO!=null) return memberVO.getId();
		else return artistVO.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override 
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override 
	public boolean isEnabled() {
		return true;
	}
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override 
	public String getName() {
		return null;

	}
	public boolean hasRole(String role) {
        return getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
}