package com.jjs.ClothingInventorySaleReformPlatform.domain.user.controller;

import com.jjs.ClothingInventorySaleReformPlatform.domain.user.dto.updateRequest.patchUser.*;
import com.jjs.ClothingInventorySaleReformPlatform.global.common.returnResponse.Response;
import com.jjs.ClothingInventorySaleReformPlatform.global.jwt.provider.JwtTokenProvider;
import com.jjs.ClothingInventorySaleReformPlatform.domain.user.service.UserService;
import com.jjs.ClothingInventorySaleReformPlatform.domain.user.service.UserUpdateService;
import com.jjs.ClothingInventorySaleReformPlatform.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "회원 정보 수정 컨트롤러", description = "로그인, 로그인 테스트, 회원가입 API")
public class UserUpdateController {

    private final UserService userService;
    private final UserUpdateService userUpdateService;
    private final CartService cartService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;

    /**
     * 구매자, 판매자, 디자이너
     */
    @PatchMapping("/auth/update/phoneNumber")
    @Operation(summary = "전화번호 변경", description = "회원 정보 수정 시, 회원의 전화번호를 변경한다. 구매자, 판매자, 디자이너 공용으로 사용")
    public ResponseEntity<?> updatePhoneNumber(@RequestBody UpdatePhoneNumberDTO updateDTO) {
        userUpdateService.updatePhoneNumber(updateDTO);
        return response.success("전화번호 수정 완료", HttpStatus.OK);
    }

    /**
     * 구매자, 판매자, 디자이너
     */
    @PatchMapping("/auth/update/password")
    @Operation(summary = "비밀번호 변경", description = "회원 정보 수정 시, 회원의 비밀번호를 변경한다. 구매자, 판매자, 디자이너 공용으로 사용")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updateDTO) {
        userUpdateService.updatePassword(updateDTO);
        return response.success("비밀번호 변경 완료", HttpStatus.OK);
    }

    /**
     * 구매자
     */
    @PatchMapping("/auth/update-purchaser/address")
    @Operation(summary = "주소 변경", description = "회원 정보 수정 시, 회원의 주소를 변경한다. 구매자만 해당")
    public ResponseEntity<?> updatePurchaserAddress(@RequestBody UpdateAddressDTO updateDTO) {
        userUpdateService.updatePurchaserAddress(updateDTO);
        return response.success("주소 수정 완료", HttpStatus.OK);
    }

    /**
     * 판매자
     */
    @PatchMapping("/auth/update-seller/storeName")
    @Operation(summary = "매장 이름 변경", description = "회원 정보 수정 시, 회원의 매장 이름을 변경한다. 판매자만 해당")
    public ResponseEntity<?> updateSellerStoreName(@RequestBody UpdateStoreNameDTO updateDTO) {
        userUpdateService.updateSellerStoreName(updateDTO);
        return response.success("매장 이름 수정 완료", HttpStatus.OK);
    }

    /**
     * 판매자
     */
    @PatchMapping("/auth/update-seller/storeAddress")
    @Operation(summary = "매장 주소 변경", description = "회원 정보 수정 시, 회원의 매장 주소를 변경한다. 판매자만 해당")
    public ResponseEntity<?> updateSellerStoreAddress(@RequestBody UpdateStoreAddressDTO updateDTO) {
        userUpdateService.updateSellerStoreAddress(updateDTO);
        return response.success("매장 주소 수정 완료", HttpStatus.OK);
    }

    /**
     * 판매자
     */
    @PatchMapping("/auth/update-seller/businessNumber")
    @Operation(summary = "사업자 번호 변경", description = "회원 정보 수정 시, 회원의 사업자 번호를 변경한다. 판매자만 해당")
    public ResponseEntity<?> updateSellerBusinessNumber(@RequestBody UpdateBusinessNumberDTO updateDTO) {
        userUpdateService.updateSellerBusinessNumber(updateDTO);
        return response.success("사업자 번호 수정 완료", HttpStatus.OK);
    }

    /**
     * 디자이너
     */
    @PatchMapping("/auth/update-designer/address")
    @Operation(summary = "주소 변경", description = "회원 정보 수정 시, 회원의 주소를 변경한다. 디자이너만 해당")
    public ResponseEntity<?> updateDesignerAddress(@RequestBody UpdateAddressDTO updateDTO) {
        userUpdateService.updateDesignerAddress(updateDTO);
        return response.success("주소 수정 완료", HttpStatus.OK);
    }

}
