package kr.co.daeddongadmin.board.repository;

import kr.co.daeddongadmin.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardRepository {
    List<Board> selectBoardList(Map<String,Object> paramMap);
    Board selectBoardInfo(Board board);

    int insertBoard(Board board);
    int updateBoard(Board board);
    int incrementBoardViews(Board board);
}
