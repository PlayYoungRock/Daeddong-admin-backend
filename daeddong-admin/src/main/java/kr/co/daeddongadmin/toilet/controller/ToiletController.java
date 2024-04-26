package kr.co.daeddongadmin.toilet.controller;

import kr.co.daeddongadmin.util.CommonUtil;
import kr.co.daeddongadmin.toilet.domain.Toilet;
import kr.co.daeddongadmin.toilet.service.ToiletService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/toilet")
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    /*
	* 화장실 목록 조회
	* index : 게시글 페이지
	* count : 게시글 수
	* sido : 시도
	* gungu : 군구
	* searchWord : 검색어
어* */
    @GetMapping("/toiletList")
    @ResponseBody
    public Map<String,Object> getToiletList(HttpServletRequest request){
        Map<String,Object> paramMap = CommonUtil.customMap(request);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int totalCount = toiletService.getToiletCount(paramMap);
        List<Toilet> toiletList = toiletService.getToiletList(paramMap);
        if(!toiletList.isEmpty()){
            resultMap.put("resultCode","0000");
            resultMap.put("totalCount",totalCount);
            resultMap.put("toiletList",toiletList);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터 없음");
        }

       return resultMap;
    }

    /*
	* 화장실 상세 조회
	* seq : 화장실 고유번호
	*  */

    @GetMapping("/toiletInfo")
    @ResponseBody
    public Map<String,Object> getToiletInfo(@RequestParam(value="seq", defaultValue="0")String seq){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Toilet toiletInfo = toiletService.getToiletInfo(seq);
        if(toiletInfo != null){
            resultMap.put("resultCode","0000");
            resultMap.put("toiletInfo",toiletInfo);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }

        return resultMap;
    }

    /*
    * 화장실 삭제
    * seq : 화장실 고유번호
    * */
    @DeleteMapping("/deleteToilet")
    @ResponseBody
    public Map<String,Object> deleteToilet(@RequestParam(value="seq", defaultValue="0")String seq){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int deleteResult = toiletService.deleteToilet(seq);
        if(deleteResult > 0){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","삭제되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }

        return resultMap;
    }


    /*
    * 화장실 등록
    * */
    @PostMapping("/insertToilet")
    @ResponseBody
    public Map<String,Object> insertToilet(@RequestBody Toilet toilet){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int insertResult = toiletService.insertToilet(toilet);
        if(insertResult > 0){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록 되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","등록 실패.");
        }

        return resultMap;
    }

    @PatchMapping("/updateToilet")
    @ResponseBody
    public Map<String,Object> updateToilet(@RequestBody Toilet toilet){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int insertResult = toiletService.updateToilet(toilet);
        if(insertResult > 0){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","수정 되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","수정 실패.");
        }

        return resultMap;
    }


}
