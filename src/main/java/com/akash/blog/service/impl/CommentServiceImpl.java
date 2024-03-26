package com.akash.blog.service.impl;

import com.akash.blog.entity.Comment;
import com.akash.blog.entity.Post;
import com.akash.blog.exception.BlogAPIException;
import com.akash.blog.exception.ResourceNotFoundException;
import com.akash.blog.payload.CommentDto;
import com.akash.blog.repository.CommentRepository;
import com.akash.blog.repository.PostRepository;
import com.akash.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    //Do not need to specify @Autowired because of it has only one constructor
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    //entity to DTO
    private CommentDto mapToDTO(Comment comment){
        //CommentDto commentDto=new CommentDto();
        //commentDto.setId(comment.getId());
        //commentDto.setName(comment.getName());
        //commentDto.setEmail(comment.getEmail());
        //commentDto.setBody(comment.getBody());

        CommentDto commentDto=mapper.map(comment,CommentDto.class);
        return commentDto;
    }

    //DTO to entity
    private Comment mapToEntity(CommentDto commentDto){
        //Comment comment=new Comment();
        //comment.setId(commentDto.getId());
        //comment.setName(commentDto.getName());
        //comment.setEmail(commentDto.getEmail());
        //comment.setBody(commentDto.getBody());

        Comment comment=mapper.map(commentDto,Comment.class);
        return comment;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to database
        Comment newComment = commentRepository.save(comment);
        //return entity to DTO
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments=commentRepository.findByPostId(postId);
        //convert list of comment entities to list of comment dto
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDtoRequest) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post.");
        }

        comment.setName(commentDtoRequest.getName());
        comment.setEmail(commentDtoRequest.getEmail());
        comment.setBody(commentDtoRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post.");
        }

        commentRepository.delete(comment);
    }
}
