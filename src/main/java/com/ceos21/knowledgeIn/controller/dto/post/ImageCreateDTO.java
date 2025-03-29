package com.ceos21.knowledgeIn.controller.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ImageCreateDTO {
    private String imageUrl;

    private Long postId;
}
