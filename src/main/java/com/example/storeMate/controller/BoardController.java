package com.example.storeMate.controller;

import com.example.storeMate.auth.service.AuthService;
import com.example.storeMate.base.exception.BoardException;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.BoardRequestDto;
import com.example.storeMate.domain.dto.BoardResponseDto;
import com.example.storeMate.domain.entity.Board;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.domain.entity.Role;
import com.example.storeMate.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<RsData<BoardResponseDto>> createBoard(@RequestBody @Valid BoardRequestDto boardRequestDto,
                                                                @RequestHeader("Authorization") String authorizeHeader) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        if (member.getRole() == Role.USER) {
            throw new BoardException.Forbidden("게시판 생성 권한이 없습니다.");
        }

        Board board = boardService.createBoard(boardRequestDto);

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId());
        boardResponseDto.setName(board.getName());
        boardResponseDto.setDescription(board.getDescription());
        boardResponseDto.setBoardType(board.getBoardType());
        boardResponseDto.setActive(board.isActive());
        boardResponseDto.setCreatedAt(board.getCreatedAt());

        return ResponseEntity.ok(new RsData<>("200", "게시판이 정상적으로 생성되었습니다.", boardResponseDto));

    }

    @GetMapping
    public ResponseEntity<RsData<List<BoardResponseDto>>> boardList() {

        List<Board> boardList = boardService.findAll();

        List<BoardResponseDto> boardResponseDtoList = boardList.stream()
                .map(
                        board -> new BoardResponseDto(
                                board.getId(),
                                board.getName(),
                                board.getDescription(),
                                board.getBoardType(),
                                board.isActive(),
                                board.getCreatedAt(),
                                board.getUpdatedAt()
                        )
                ).toList();

        return ResponseEntity.ok(new RsData<List<BoardResponseDto>>("200", "게시글 목록이 정상적으로 조회되었습니다.", boardResponseDtoList));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RsData<BoardResponseDto>> BoardUpdate(@PathVariable("id") Long id,
                                                                @Valid @RequestBody BoardRequestDto boardRequestDto,
                                                                @RequestHeader("Authorization") String authorizeHeader) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        Board board = boardService.findById(id);

        if (!member.getRole().getValue().equals("ROLE_ADMIN")) {
            throw new BoardException.Forbidden("게시판 수정 권한이 없습니다.");
        }

        boardService.updateBoard(board, boardRequestDto);

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(board.getId());
        boardResponseDto.setName(board.getName());
        boardResponseDto.setBoardType(board.getBoardType());
        boardResponseDto.setActive(board.isActive());
        boardResponseDto.setCreatedAt(board.getCreatedAt());
        boardResponseDto.setUpdatedAt(board.getUpdatedAt());
        boardResponseDto.setDescription(board.getDescription());

        return ResponseEntity.ok(new RsData<BoardResponseDto>("200", "정상적으로 게시판을 수정하였습니다.", boardResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<BoardResponseDto>> deleteBoard(@PathVariable("id") Long id,
                                                                @RequestHeader("Authorization") String authorizeHeader) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        Board board = boardService.findById(id);

        if (!member.getRole().getValue().equals("ROLE_ADMIN")) {
            throw new BoardException.Forbidden("게시판 삭제 권한이 없습니다.");
        }

        boardService.deleteBoard(board);

        return ResponseEntity.ok(new RsData<>("200", "정상적으로 게시판을 삭제하였습니다."));
    }
}
