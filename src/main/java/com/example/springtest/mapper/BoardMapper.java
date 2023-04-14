package com.example.springtest.mapper;

import com.example.springtest.domain.BoardDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void saveBoard(BoardDAO boardDAO);

    List<BoardDAO> getAllBoard();

    BoardDAO findByBoardId(long id);

    void updateBoard(BoardDAO boardDAO);

    void deleteByBoardId(long boardId);

    void deleteAll();
}
