package com.ceos21.knowledgeIn.domain.post;

import com.ceos21.knowledgeIn.domain.user.User;
import com.ceos21.knowledgeIn.repository.ImageRepository;
import com.ceos21.knowledgeIn.repository.post.PostRepository;
import com.ceos21.knowledgeIn.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ImageTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    Post post = null;
    User user = null;
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
    void createPostImage() {
        //given

        String imageUrl = "www.naver.com";
        Image image = Image.create(post, imageUrl);

        //when
        Long id = imageRepository.save(image).getId();
        em.flush();
        em.clear();

        //then
        Optional<Image> findPostImage = imageRepository.findById(id);
        assertTrue(findPostImage.isPresent());
    }

    @Test
    void deletePostImage() {
        //given
        String imageUrl = "www.naver.com";
        Image image = Image.create(post, imageUrl);

        //when
        Long id = imageRepository.save(image).getId();
        imageRepository.deleteById(id);
        em.flush();
        em.clear();

        //then
        Optional<Image> findPostImage = imageRepository.findById(id);
        assertTrue(findPostImage.isEmpty());
    }
}