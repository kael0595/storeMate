package com.example.storeMate.service;

import com.example.storeMate.domain.dto.BoardRequestDto;
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
}
