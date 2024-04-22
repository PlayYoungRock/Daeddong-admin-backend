package kr.co.daeddongadmin.board.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.daeddongadmin.board.domain.Board;
import kr.co.daeddongadmin.board.repository.BoardRepository;
import kr.co.daeddongadmin.board.service.BoardService;
import kr.co.daeddongadmin.common.CommonUtil;
import kr.co.daeddongadmin.common.FileUtil;
import kr.co.daeddongadmin.toilet.domain.Toilet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/board/{bbsId}List")
    @ResponseBody
    public Map<String,Object> boardList(@PathVariable String bbsId, HttpServletRequest request){
        if (bbsId == null) {
            bbsId = "notice";
        }
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> paramMap = CommonUtil.customMap(request);
        paramMap.put("bbsId",bbsId);
        //TODO : 게시판 목록 상단 고정 추가 예정
        //TODO : 로그인 유저 권한 추가 예정
        List<Board> boardList = boardService.selectBoardList(paramMap);	//게시글 목록
        int boardCount = boardService.selectBoardCount(paramMap);
        if(!boardList.isEmpty()){
            resultMap.put("resultCode","0000");
            resultMap.put("boardCount",boardCount);
            resultMap.put("boardList",boardList);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터 없음");
        }
        return resultMap;

    }

    @GetMapping(value = "/board/{bbsId}Info")
    @ResponseBody
    public Map<String,Object> boardInfo(@PathVariable String bbsId,@RequestParam String seq) throws RuntimeException {
        Map<String,Object> resultMap = new HashMap<>();
        //TODO : 게시판 목록 상단 고정 추가 예정
        //TODO : 로그인 유저 권한 추가 예정
        Board boardInfo = boardService.selectBoardInfo(bbsId,seq);	//게시글 목록
        if(boardInfo != null){
            boardService.incrementBoardViews(boardInfo.getSeq());
            resultMap.put("resultCode","0000");
            resultMap.put("boardInfo",boardInfo);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }
        return resultMap;

    }

    @PostMapping(value = "/board/insert{bbsId}")
    @ResponseBody
    public Map<String,Object> insertBoard(@PathVariable String bbsId,
                                          @RequestPart Board board,
                                          @RequestPart("file") MultipartFile file
    ) {
        Map<String,Object> resultMap = new HashMap<>();
        int result = boardService.insertBoard(bbsId,board,file);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","등록 실패.");
        }

        return resultMap;
    }

    @PatchMapping(value = "/board/update{bbsId}")
    @ResponseBody
    public Map<String,Object> updateBoard(@PathVariable String bbsId,
                                          @RequestPart Board board,
                                          @RequestPart("file") MultipartFile file
    ){
        Map<String,Object> resultMap = new HashMap<>();
        int result = boardService.updateBoard(bbsId,board,file);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","수정 실패.");
        }

        return resultMap;
    }

    @DeleteMapping(value = "/board/deleteBoard")
    @ResponseBody
    public Map<String,Object> deleteBoard(@RequestPart Board board){
        Map<String,Object> resultMap = new HashMap<>();
        int result = boardService.deleteBoard(board);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","삭제되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","삭가 실패.");
        }

        return resultMap;
    }

}
