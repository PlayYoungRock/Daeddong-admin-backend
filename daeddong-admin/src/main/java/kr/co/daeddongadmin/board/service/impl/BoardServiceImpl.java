package kr.co.daeddongadmin.board.service.impl;

import kr.co.daeddongadmin.board.domain.Board;
import kr.co.daeddongadmin.board.repository.BoardRepository;
import kr.co.daeddongadmin.board.service.BoardService;
import kr.co.daeddongadmin.util.FileUtil;
import kr.co.daeddongadmin.file.domain.File;
import kr.co.daeddongadmin.file.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FileUtil fileUtil;

	@Override
	public List<Board> selectBoardList(Map<String, Object> paramMap) {
		paramMap.put("index",Integer.parseInt(paramMap.get("index").toString()));
		paramMap.put("count",Integer.parseInt(paramMap.get("count").toString()));
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
	public int insertBoard(String bbsId, Board board, MultipartFile file) {
		String fileId = "";
		if(file != null){
			fileId = UUID.randomUUID().toString();
			board.setFileId(fileId);
		}
		board.setBbsId(bbsId);
		int boardResult = boardRepository.insertBoard(board);
		if(boardResult >0 && file != null){
			File fileVo = fileUtil.uploadFile(file);
			fileVo.setFileId(fileId);
			fileRepository.insertFile(fileVo);
		}
		return boardResult;
	}
	@Override
	public int updateBoard(String bbsId, Board board, MultipartFile file) {
		String fileId = "";
		if(file != null){
			fileId = UUID.randomUUID().toString();
			board.setFileId(fileId);
		}
		board.setBbsId(bbsId);
		int boardResult = boardRepository.updateBoard(board);
		if(boardResult >0 && file != null){
			File fileVo = fileUtil.uploadFile(file);
			fileVo.setFileId(fileId);
			fileRepository.insertFile(fileVo);
		}
		return boardResult;
	}

	@Override
	public int deleteBoard(Board board) {
		return boardRepository.deleteBoard(board);
	}
	@Override
	public void incrementBoardViews(String seq) {
		boardRepository.incrementBoardViews(seq);
	}

}
