package kr.co.daeddongadmin.board.service;

import kr.co.daeddongadmin.board.domain.Board;

import java.util.List;
import java.util.Map;

public interface BoardService {
    public List<Board> selectBoardList(String bbsId);
    public Board selectBoardInfo(Board board);

    public int insertBoard(Board board);
    public int updateBoard(Board board);
    public int incrementBoardViews(Board board);

}
