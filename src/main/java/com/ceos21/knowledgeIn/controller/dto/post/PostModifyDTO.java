package com.ceos21.knowledgeIn.controller.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class PostModifyDTO {
    private String title;

    private String content;

    private List<Long> imageIds;

    private List<Long> previousHashTagIds;

    private List<String> newHashTagStrings;
}
