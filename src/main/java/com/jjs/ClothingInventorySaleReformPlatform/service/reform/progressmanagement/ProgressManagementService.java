package com.jjs.ClothingInventorySaleReformPlatform.service.reform.progressmanagement;

import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.progressmanagement.ProgressStatus;
import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.progressmanagement.Progressmanagement;
import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.estimate.Estimate;
import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.estimate.EstimateStatus;
import com.jjs.ClothingInventorySaleReformPlatform.domain.reform.reformrequest.ReformRequest;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.progressmanagement.ProgressImgRequestDTO;
import com.jjs.ClothingInventorySaleReformPlatform.dto.reform.progressmanagement.ProgressImgResponseDTO;
import com.jjs.ClothingInventorySaleReformPlatform.repository.reform.progressmanagement.ProgressRepository;
import com.jjs.ClothingInventorySaleReformPlatform.repository.reform.estimate.EstimateRepository;
import com.jjs.ClothingInventorySaleReformPlatform.repository.reform.reformrequest.ReformRequestRepository;
import com.jjs.ClothingInventorySaleReformPlatform.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgressManagementService {

    private final S3Service s3Service;

    private final EstimateRepository estimateRepository;
    private final ProgressRepository progressRepository;
    private final ReformRequestRepository requestRepository;

    private String imageUploadPath = "ProgressManagement/";


    @Transactional
    public void saveFirstImg(ProgressImgRequestDTO imgRequestDTO) throws IOException {
        Progressmanagement progressmanagement = progressRepository.findByEstimateNumber_Id(imgRequestDTO.getEstimateId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 형상관리 정보가 없습니다."));

        progressmanagement.setFirstImgUrl(s3Service.uploadFile(imgRequestDTO.getImgUrl(), imageUploadPath));
        progressmanagement.setProgressStatus(ProgressStatus.REFORMING);
        progressRepository.save(progressmanagement);
    }

    @Transactional
    public void saveSecondImg(ProgressImgRequestDTO imgRequestDTO) throws IOException {
        Progressmanagement progressmanagement = progressRepository.findByEstimateNumber_Id(imgRequestDTO.getEstimateId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 형상관리 정보가 없습니다."));

        progressmanagement.setSecondImgUrl(s3Service.uploadFile(imgRequestDTO.getImgUrl(), imageUploadPath));
        progressRepository.save(progressmanagement);
    }

    @Transactional
    public void saveCompleteImg(ProgressImgRequestDTO imgRequestDTO) throws IOException {
        Progressmanagement progressmanagement = progressRepository.findByEstimateNumber_Id(imgRequestDTO.getEstimateId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 형상관리 정보가 없습니다."));

        progressmanagement.setCompleteImgUrl(s3Service.uploadFile(imgRequestDTO.getImgUrl(), imageUploadPath));
        progressmanagement.setProgressStatus(ProgressStatus.REFORM_COMPLETE);
        progressRepository.save(progressmanagement);
    }

    @Transactional
    public ProgressImgResponseDTO getProgressResponse(Long progressNumber) {
        Progressmanagement progressmanagement = progressRepository.findByEstimateNumber_Id(progressNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당되는 형상관리 정보가 없습니다."));
        ProgressImgResponseDTO responseDTO = new ProgressImgResponseDTO();
        responseDTO.setProgressId(progressmanagement.getId());
        responseDTO.setRequestId(progressmanagement.getRequestNumber().getId());
        responseDTO.setEstimateId(progressmanagement.getEstimateNumber().getId());
        responseDTO.setProductImgUrl(progressmanagement.getProductImgUrl());
        responseDTO.setFirstImgUrl(progressmanagement.getFirstImgUrl());
        responseDTO.setSecondImgUrl(progressmanagement.getSecondImgUrl());
        responseDTO.setCompleteImgUrl(progressmanagement.getCompleteImgUrl());

        return responseDTO;
    }
}
