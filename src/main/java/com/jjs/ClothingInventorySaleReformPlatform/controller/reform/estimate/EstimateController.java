package com.jjs.ClothingInventorySaleReformPlatform.controller.reform.estimate;

import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.estimate.EstimateStatus;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.estimate.ClientResponse;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.estimate.request.EstimateRequestDTO;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.estimate.response.EstimateResponseDTO;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.reformrequest.ReformRequestResponseDTO;
import com.jjs.ClothingInventorySaleReformPlatform.dto.response.Response;
import com.jjs.ClothingInventorySaleReformPlatform.service.reform.progressmanagement.ProgressManagementService;
import com.jjs.ClothingInventorySaleReformPlatform.service.reform.estimate.EstimateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "리폼 수락, 견적서 작성", description = "리폼 수락 및 견적서 작성,수정 API 입니다.")
public class EstimateController {

    private final Response response;
    private final EstimateService estimateService;
    private final ProgressManagementService progressManagementService;

    @GetMapping("/estimate/designer/requestForm")
    @Operation(summary = "디자이너 요청받은 의뢰 조회", description = "디자이너가 요청받은 모든 의뢰를 조회합니다.")
    public ResponseEntity<?> getAllRequestForm() {
        List<ReformRequestResponseDTO> reformRequestResponseDTOList = estimateService.getAllRequestList();

        return response.success(reformRequestResponseDTOList);
    }

    @GetMapping("/estimate/designer/requestForm/{requestNumber}")
    @Operation(summary = "요청받은 의뢰 상세 페이지 이동", description = "요청받은 의뢰의 상세 내역을 조회합니다.")
    public ResponseEntity<?> getRequestFormByNumber(@PathVariable Long requestNumber){
        List<ReformRequestResponseDTO> requestListByNumber = estimateService.getRequestListByNumber(requestNumber);

        return response.success(requestListByNumber);
    }

    @PostMapping("/estimate/designer/requestForm/{requestNumber}")
    @Operation(summary = "요청받은 의뢰 상태 변경", description = "요청받은 의뢰의 상태를 변경합니다(수락 or 거절)")
    public ResponseEntity<?> updateRequestStatus(@PathVariable Long requestNumber, @RequestBody ClientResponse clientResponse) throws Exception {
        try{
            estimateService.updateRequestStatus(requestNumber,clientResponse.getClientResponse());
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return response.fail("상태 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response.success("상태 변경 완료.");
    }

    @PostMapping(value = "/estimate/designer/estimateForm/{requestNumber}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "디자이너의 견적서 작성", description = "디자이너가 견적서를 작성합니다.")
    public ResponseEntity<?> sendEstimate(@ModelAttribute EstimateRequestDTO estimateRequestDTO, @PathVariable Long requestNumber) {
        try {
            estimateService.saveEstimate(estimateRequestDTO, requestNumber);
            return response.success("견적서 등록 성공.", HttpStatus.OK);
        } catch (Exception e) {
            log.error("리폼 요청 사항 저장 에러",e);
            return response.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/estimate/designer/estimateForm/{estimateNumber}")
    @Operation(summary = "디자이너의 견적서 조회", description = "디자이너가 견적서를 수정할 때, 기존의 정보를 불러오는 용도로 사용한다.")
    public ResponseEntity<?> getEstimateDetails(@PathVariable Long estimateNumber) {
        try {
            EstimateResponseDTO estimateResponseDTO = estimateService.getEstimate(estimateNumber);
            return response.success(estimateResponseDTO, "견적서 조회 성공", HttpStatus.OK);
        } catch (Exception e) {
            log.error("견적서 조회 실패", e);
            return response.fail("견적서 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/estimate/designer/estimateForm/{estimateNumber}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "디자이너의 견적서 수정", description = "디자이너가 견적서를 수정한다. getEstimateDetails으로 조회하고 수정하면 전체 내용이 덮어쓰기 형식으로 저장된다.")
    public ResponseEntity<?> updateEstimateDetails(@PathVariable Long estimateNumber, @ModelAttribute EstimateRequestDTO estimateRequestDTO) {
        try {
            estimateService.updateEstimate(estimateRequestDTO, estimateNumber);
            return response.success("의뢰 수정 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return response.fail("의뢰 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return response.fail("의뢰 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage());
            return response.fail("의뢰 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/estimate/purchaser/{estimateNumber}/{status}")
    @Operation(summary = "구매자의 견적서 수락 및 거절", description = "구매자는 견적서를 확인하고 수락 및 거절한다.")
    public ResponseEntity<?> selectEstimateStatus(@PathVariable Long estimateNumber, @PathVariable EstimateStatus status) {
        try {
            estimateService.selEstimateStatus(estimateNumber, status);
            if (status == EstimateStatus.REQUEST_ACCEPTED) {
                progressManagementService.setProgressStart(estimateNumber);
            }
            return response.success(status, "견적서 상태 수정 완료", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return response.fail("견적서 상태 수정 실패1", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage());
            return response.fail("견적서 상태 수정 실패2", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
