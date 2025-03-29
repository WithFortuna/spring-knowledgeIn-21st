package com.ceos21.knowledgeIn.service;

import com.ceos21.knowledgeIn.controller.dto.post.ImageCreateDTO;
import com.ceos21.knowledgeIn.controller.dto.post.ImageResponseDTO;
import com.ceos21.knowledgeIn.domain.post.Image;
import com.ceos21.knowledgeIn.domain.post.Post;
import com.ceos21.knowledgeIn.repository.ImageRepository;
import com.ceos21.knowledgeIn.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.UpdateCountOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    //create
    @Transactional
    public Long createImage(ImageCreateDTO dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();

        Image entity = Image.create(post, dto.getImageUrl());

        return imageRepository.save(entity).getId();
    }

    //read
    public ImageResponseDTO findImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow();

        return ImageResponseDTO.from(image);
    }

    public List<ImageResponseDTO> findAllImages() {
        return imageRepository.findAll()
                .stream()
                .map(image -> ImageResponseDTO.from(image))
                .collect(Collectors.toList());
    }

    //update
    //delete
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
