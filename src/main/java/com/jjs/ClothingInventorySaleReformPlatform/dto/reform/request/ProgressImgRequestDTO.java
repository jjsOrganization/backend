package com.jjs.ClothingInventorySaleReformPlatform.dto.reform.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProgressImgRequestDTO {

    private Long estimateId;
    private MultipartFile imgUrl;
}
