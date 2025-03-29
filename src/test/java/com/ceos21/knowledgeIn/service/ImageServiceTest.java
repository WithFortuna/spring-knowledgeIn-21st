package com.ceos21.knowledgeIn.service;

import com.ceos21.knowledgeIn.controller.dto.post.ImageCreateDTO;
import com.ceos21.knowledgeIn.controller.dto.post.ImageResponseDTO;
import com.ceos21.knowledgeIn.domain.post.Image;
import com.ceos21.knowledgeIn.domain.post.Post;
import com.ceos21.knowledgeIn.domain.user.User;
import com.ceos21.knowledgeIn.repository.PostRepository;
import com.ceos21.knowledgeIn.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ImageServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    EntityManager em;

    User user;
    Post post;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("최근호")
                .nickname("olaf")
                .build();
        userRepository.save(user);

        post = Post.createQuestion("title", "content", user);
        postRepository.save(post);
    }

    @Test
    void createImage() {
        //given
        ImageCreateDTO imageDTO = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl("/임의경로")
                .build();

        //when
        Long id = imageService.createImage(imageDTO);
        //then
        assertThat(id).isNotNull();
    }

    @Test
    void findImage() {
        //given
        String imageUrl = "/임의경로";
        ImageCreateDTO imageDTO = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl(imageUrl)
                .build();
        Long id = imageService.createImage(imageDTO);

        //when
        em.flush();
        em.clear();
        ImageResponseDTO image = imageService.findImage(id);

        //then
        assertEquals(image.getImageUrl(),imageUrl);
    }

    @Test
    void findAllImages() {
        //given
        String imageUrl = "/임의경로";
        ImageCreateDTO imageDTO = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl(imageUrl)
                .build();
        Long id = imageService.createImage(imageDTO);

        String imageUrl2 = "/임의경로";
        ImageCreateDTO imageDTO2 = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl(imageUrl2)
                .build();
        Long id2 = imageService.createImage(imageDTO2);

        //when
        em.flush();
        em.clear();

        List<ImageResponseDTO> allImages = imageService.findAllImages();

        //then
        assertEquals(allImages.size(), 2);

        List<String> urls = allImages.stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
        assertThat(urls).containsExactlyInAnyOrder(imageUrl, imageUrl2);
    }

    @Test
    void deleteImage() {
        //given
        String imageUrl = "/임의경로";
        ImageCreateDTO imageDTO = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl(imageUrl)
                .build();
        Long id = imageService.createImage(imageDTO);

        String imageUrl2 = "/임의경로";
        ImageCreateDTO imageDTO2 = ImageCreateDTO.builder()
                .postId(post.getId())
                .imageUrl(imageUrl2)
                .build();
        Long id2 = imageService.createImage(imageDTO2);

        //when
        em.flush();
        em.clear();

        imageService.deleteImage(id);

        //then
        List<ImageResponseDTO> allImages = imageService.findAllImages();

        assertEquals(allImages.size(), 1);
        assertEquals(allImages.get(0).getImageUrl(), imageUrl2);
    }
}