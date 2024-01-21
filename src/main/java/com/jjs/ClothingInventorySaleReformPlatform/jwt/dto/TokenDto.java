package com.jjs.ClothingInventorySaleReformPlatform.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 클라이언트에 토큰을 보내기 위한 DTO
 * @author rimsong
 * grantType: JWT에 대한 인증 타입. 여기서는 Bearer를 사용한다. 이후 HHTP 헤더에 prefix로 붙여주는 타입
 */

@Builder
@Data
@AllArgsConstructor
public class TokenDto {

    private String grantType; // JWT에 대한 인증 타입. 여기서는 Bearer를 사용한다. 이후 HHTP 헤더에 prefix로 붙여주는 타입
    private String accessToken;
    private String refreshToken;
    private String email;

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    // Entity -> DTO
    public static TokenDto fromEntity(TokenDto member) {
        return TokenDto.builder()
                .grantType(member.grantType)
                .accessToken(member.accessToken)
                .refreshToken(member.refreshToken)
                .build();
    }
}
