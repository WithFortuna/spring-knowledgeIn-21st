package com.ceos21.knowledgeIn.domain.post;

import com.ceos21.knowledgeIn.domain.user.User;
import com.ceos21.knowledgeIn.repository.post.CommentRepository;
import com.ceos21.knowledgeIn.repository.post.PostRepository;
import com.ceos21.knowledgeIn.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CommentTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    void createComment() {
        //given
        User user = User.builder()
                .name("최근호")
                .nickname("olaf")
                .build();
        userRepository.save(user);

        Post post = Post.createQuestion("title", "content",user);
        postRepository.save(post);



        Comment comment = Comment.create(user, post, post.getContent());

        //when
        Long id = commentRepository.save(comment).getId();
        //then
        Optional<Comment> findComment = commentRepository.findById(id);
        assertThat(findComment).isPresent();
        assertThat(findComment.get().getPost()).isEqualTo(post);
        assertThat(findComment.get().getUser()).isEqualTo(user);
    }
    @Test
    void deleteComment() {
        //given
        User user = User.builder()
                .name("최근호")
                .nickname("olaf")
                .build();
        userRepository.save(user);

        Post post = Post.createQuestion("title", "content",user);
        postRepository.save(post);


        Comment comment = Comment.create(user, post, post.getContent());

        //when
        Long id = commentRepository.save(comment).getId();
        commentRepository.deleteById(id);
        //then
        Optional<Comment> findComment = commentRepository.findById(id);
        assertThat(findComment).isEmpty();
    }
}