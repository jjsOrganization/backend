package com.jjs.ClothingInventorySaleReformPlatform.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;
    
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    private String role;


    /*
    public static PurchaserDTO toPurchaserDTO(Purchaser purchaser) {
        PurchaserDTO purchaserDTO = new PurchaserDTO();
        purchaserDTO.setEmail(purchaser.getEmail());
        purchaserDTO.setPassword(purchaser.getPassword());
        purchaserDTO.setNickname(purchaser.getNickname());
        purchaserDTO.setName(purchaser.getName());
        purchaserDTO.setAddress(purchaser.getAddress());
        purchaserDTO.setPhoneNumber(purchaser.getPhoneNumber());

        return purchaserDTO;
    }

     */

}
