package com.sparta.board2.service;

import com.sparta.board2.dto.comment.request.CommentSaveRequestDto;
import com.sparta.board2.dto.comment.response.CommentResponseDto;
import com.sparta.board2.dto.comment.response.CommentSaveResponseDto;
import com.sparta.board2.entity.Board;
import com.sparta.board2.entity.Comment;
import com.sparta.board2.repository.BoardRepository;
import com.sparta.board2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveResponseDto saveComment(long boardId, CommentSaveRequestDto commentSaveRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("보드 못 찾음"));
        Comment newComment = new Comment(commentSaveRequestDto.getContent(), board);
        Comment savedComment = commentRepository.save(newComment);
        return new CommentSaveResponseDto(savedComment.getId(),savedComment.getContents());
    }

    public List<CommentResponseDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> commentSaveResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment.getId(),comment.getContents());
            commentSaveResponseDtos.add(commentResponseDto);
        }
        return commentSaveResponseDtos;
    }
}
