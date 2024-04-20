package kr.co.daeddongadmin.board.service.impl;

import kr.co.daeddongadmin.board.domain.Board;
import kr.co.daeddongadmin.board.repository.BoardRepository;
import kr.co.daeddongadmin.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;

	@Override
	public List<Board> selectBoardList(Map<String, Object> paramMap) {
		return boardRepository.selectBoardList(paramMap);
	}

	@Override
	public int selectBoardCount(Map<String, Object> paramMap) {
		return boardRepository.selectBoardCount(paramMap);
	}

	@Override
	public Board selectBoardInfo(String bbsId, String seq) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("seq", seq);
		paramMap.put("bbsId", bbsId);
		return boardRepository.selectBoardInfo(paramMap);
	}

	@Override
	public int insertBoard(Board board) {
		return boardRepository.insertBoard(board);
	}
	@Override
	public int updateBoard(Board board) {
		return boardRepository.updateBoard(board);
	}
	@Override
	public int incrementBoardViews(Board board) {
		return boardRepository.incrementBoardViews(board);
	}

	@Override
	public int test(int test){
		return 0;
	}


}
