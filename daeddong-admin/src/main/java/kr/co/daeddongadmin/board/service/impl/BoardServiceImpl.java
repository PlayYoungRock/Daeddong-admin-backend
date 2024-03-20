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
	public List<Board> selectBoardList(String bbsId) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("bbsId", bbsId);
		return boardRepository.selectBoardList(paramMap);
	}

	@Override
	public Board selectBoardInfo(Board board) {
		return boardRepository.selectBoardInfo(board);
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


}
