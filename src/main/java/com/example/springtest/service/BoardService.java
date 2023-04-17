package com.example.springtest.service;


import com.example.springtest.domain.BoardDAO;
import com.example.springtest.domain.InputForm;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    BoardDAO saveFileBoard(InputForm input,Long memberId) throws IOException;

    BoardDAO saveTextBoard(InputForm input,Long memberId) throws IOException;

    List<BoardDAO> getAllBoard();

    BoardDAO getBoardById(long id);

    void updateBoard(BoardDAO boardDAO);

    void deleteBoard(long boardId);

    void deleteAll();
}
