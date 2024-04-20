package kr.co.daeddongadmin.board.controller;

import kr.co.daeddongadmin.board.domain.Board;
import kr.co.daeddongadmin.board.repository.BoardRepository;
import kr.co.daeddongadmin.board.service.BoardService;
import kr.co.daeddongadmin.common.CommonUtil;
import kr.co.daeddongadmin.toilet.domain.Toilet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    public Map<String,Object> insertBoard(@PathVariable String bbsId,@RequestBody Board board,
//                                     final MultipartHttpServletRequest multiRequest,
                              HttpServletRequest request) throws IOException, SQLException, RuntimeException, NoSuchAlgorithmException {
        Map<String,Object> resultMap = new HashMap<>();
        /* 파일 아이디는 시 분 초 랜덤숫자까지 섞여서 아이디를 생성*/
        String file_id = UUID.randomUUID().toString();
        /* 첨부파일 사전 검증 */
//        int fileCount = Integer.parseInt( CommonUtil.isNull(paramMap.get("fileCount"), "1") );
//        for(int i=1; i<=fileCount; i++) {
//            String errMsg = CommonUtil.fileUploadBeforeCheck(multiRequest.getFileMap(), 10485760, "file"+i+"_name", "", false, "");
//            if(!"".equals(errMsg)){
//                return CommUtil.doComplete(model, "오류", errMsg, "history.back();");
//            }
//        }

//        paramMap.put("bbs_Id", bbsId);
//        paramMap.put("file_id", file_id);
//        paramMap.put("input_nm", inputNm);
//        paramMap.put("input_id", CommUtil.getMngrMemId());
        int result = boardService.insertBoard(board);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","9999");
            resultMap.put("resultMsg","등록 실패.");
        }

//        /* 게시물이 저장되면 파일 저장*/
//        if(result ==1) {
//            int file_seq = 1;
//            for(int i=1; i<=fileCount; i++) {
//                HashMap hmFile = CommUtil.fileUpload(multiRequest.getFileMap(), "file"+i+"_name", bbsId);
//
//                String file_name = "";
//                String file_org_name = "";
//                String file_path = "";
//                if(hmFile != null) {
//                    file_name = hmFile.get("F_SAVENAME").toString();
//                    file_org_name = hmFile.get("F_ORGNAME").toString();
//                    file_path = bbsId;
//
//                    paramMap.put("file_id", file_id);
//                    paramMap.put("file_seq", file_seq);
//                    paramMap.put("fileName", file_name);
//                    paramMap.put("fileOrgName", file_org_name);
//                    paramMap.put("filePath", file_path);
//                    paramMap.put("input_id", CommUtil.getMngrMemId());
//
//                    int file = fileService.insertFile(paramMap);
//                    file_seq++;
//                }
//            }
//        }
        return resultMap;
    }

    @PostMapping(value = "/board/updateBoard")
    @ResponseBody
    public Map<String,Object> updateBoard(@RequestBody Board board,
//                                          final MultipartHttpServletRequest multiRequest,
                                          HttpServletRequest request) throws IOException, SQLException, RuntimeException, NoSuchAlgorithmException {
        Map<String,Object> resultMap = new HashMap<>();
        /* 파일 아이디는 시 분 초 랜덤숫자까지 섞여서 아이디를 생성*/
        String file_id = UUID.randomUUID().toString();
        /* 첨부파일 사전 검증 */
//        int fileCount = Integer.parseInt( CommonUtil.isNull(paramMap.get("fileCount"), "1") );
//        for(int i=1; i<=fileCount; i++) {
//            String errMsg = CommonUtil.fileUploadBeforeCheck(multiRequest.getFileMap(), 10485760, "file"+i+"_name", "", false, "");
//            if(!"".equals(errMsg)){
//                return CommUtil.doComplete(model, "오류", errMsg, "history.back();");
//            }
//        }

//        paramMap.put("bbs_Id", bbsId);
//        paramMap.put("file_id", file_id);
//        paramMap.put("input_nm", inputNm);
//        paramMap.put("input_id", CommUtil.getMngrMemId());
        int result = boardService.updateBoard(board);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","9999");
            resultMap.put("resultMsg","등록 실패.");
        }

//        /* 게시물이 저장되면 파일 저장*/
//        if(result ==1) {
//            int file_seq = 1;
//            for(int i=1; i<=fileCount; i++) {
//                HashMap hmFile = CommUtil.fileUpload(multiRequest.getFileMap(), "file"+i+"_name", bbsId);
//
//                String file_name = "";
//                String file_org_name = "";
//                String file_path = "";
//                if(hmFile != null) {
//                    file_name = hmFile.get("F_SAVENAME").toString();
//                    file_org_name = hmFile.get("F_ORGNAME").toString();
//                    file_path = bbsId;
//
//                    paramMap.put("file_id", file_id);
//                    paramMap.put("file_seq", file_seq);
//                    paramMap.put("fileName", file_name);
//                    paramMap.put("fileOrgName", file_org_name);
//                    paramMap.put("filePath", file_path);
//                    paramMap.put("input_id", CommUtil.getMngrMemId());
//
//                    int file = fileService.insertFile(paramMap);
//                    file_seq++;
//                }
//            }
//        }
        return resultMap;
    }

    @PostMapping(value = "/board/incrementBoardViews")
    @ResponseBody
    public Map<String,Object> incrementBoardViews(@RequestBody Board board) throws RuntimeException, NoSuchAlgorithmException {
        Map<String,Object> resultMap = new HashMap<>();
        int result = boardService.incrementBoardViews(board);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","9999");
            resultMap.put("resultMsg","등록 실패.");
        }
        return resultMap;
    }




}
