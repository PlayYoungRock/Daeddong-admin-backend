package kr.co.daeddongadmin.board.service;

import kr.co.daeddongadmin.board.domain.Board;

import java.util.List;
import java.util.Map;

public interface BoardService {
    public List<Board> selectBoardList(Map<String, Object> paramMap);
    public int selectBoardCount(Map<String, Object> paramMap);
    public Board selectBoardInfo(String bbsId, String seq);

    public int insertBoard(Board board);
    public int updateBoard(Board board);
    public int incrementBoardViews(Board board);

    public int test(int test);

}
