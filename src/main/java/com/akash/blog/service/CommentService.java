package com.akash.blog.service;

import com.akash.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(Long postId,Long commentId);

    CommentDto updateComment(Long postId,long commentId, CommentDto commentDtoRequest);

    void deleteComment(Long postId,Long commentId);

}
