package kr.co.daeddongadmin.board.service;

import kr.co.daeddongadmin.board.domain.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> selectBoardList(Map<String, Object> paramMap);
    int selectBoardCount(Map<String, Object> paramMap);
    Board selectBoardInfo(String bbsId, String seq);

    int insertBoard(String bbsId, Board board, MultipartFile file);
    int updateBoard(String bbsId, Board board, MultipartFile file);
    int deleteBoard(Board board);
    void incrementBoardViews(String seq);
}
