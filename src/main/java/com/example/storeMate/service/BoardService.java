package com.example.storeMate.service;

import com.example.storeMate.base.exception.BoardException;
import com.example.storeMate.domain.dto.BoardRequestDto;
import com.example.storeMate.domain.dto.BoardResponseDto;
import com.example.storeMate.domain.entity.Board;
import com.example.storeMate.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board();
        board.setName(boardRequestDto.getName());
        board.setDescription(boardRequestDto.getDescription());
        board.setBoardType(boardRequestDto.getBoardType());
        board.setActive(true);
        board.setCreatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardException.NotFound("존재하지 않는 게시판입니다."));
    }

    public void updateBoard(Board board, BoardRequestDto boardRequestDto) {
        board.setId(board.getId());
        board.setName(boardRequestDto.getName());
        board.setDescription(boardRequestDto.getDescription());
        board.setBoardType(boardRequestDto.getBoardType());
        board.setActive(boardRequestDto.isActive());
        board.setCreatedAt(board.getCreatedAt());
        board.setUpdatedAt(LocalDateTime.now());
    }
}
