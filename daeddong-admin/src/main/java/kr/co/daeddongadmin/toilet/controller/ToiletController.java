package kr.co.daeddongadmin.toilet.controller;

import kr.co.daeddongadmin.common.CommonUtil;
import kr.co.daeddongadmin.toilet.domain.Toilet;
import kr.co.daeddongadmin.toilet.service.ToiletService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    @GetMapping("/naver")
    @ResponseBody
    public void naver(){
        String clientId = "f2lsyu7q3s"; // 네이버 API 클라이언트 ID
        String clientSecret = "9rpNGcPW0iSe0TKpRdPAFyQ6XaIShZ1yYOs2bffM"; // 네이버 API 클라이언트 시크릿
        Map<String,Object> paramMap = new HashMap<>();
        for(int i=0; i<10000; i++){
        Toilet to = toiletService.getIsNullSidoToiletList();

        try {
            String address = to.getAddress();
            if(StringUtil.isNotBlank(address)) {
                String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + URLEncoder.encode(address, "UTF-8");
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                // API 응답 결과를 파싱하여 시도, 시군구 정보 추출
                String responseBody = response.toString();
                // 파싱 코드 작성 (JSON 형식의 응답 데이터를 분석하여 주소 정보 추출)
                // 예시: JSON 파싱 라이브러리(Gson, Jackson 등)를 사용하여 응답 데이터를 객체로 변환하고 필요한 정보 추출
// JSON 문자열을 JSONObject로 파싱
                JSONObject jsonObject = new JSONObject(responseBody);
                int totcnt = jsonObject.getJSONObject("meta").getInt("totalCount");
                if (totcnt > 0) {
                    // addresses 배열에서 첫 번째 주소 정보 추출
                    JSONArray addresses = jsonObject.getJSONArray("addresses");
                    JSONObject addressInfo = addresses.getJSONObject(0);

                    // 필요한 정보 추출
                    String sido = addressInfo.getJSONArray("addressElements").getJSONObject(0).getString("longName");
                    String gungu = addressInfo.getJSONArray("addressElements").getJSONObject(1).getString("longName");
                    String xCoord = addressInfo.getString("x");
                    String yCoord = addressInfo.getString("y");
                    paramMap.put("sido", sido);
                    paramMap.put("sigungu", gungu);
                    paramMap.put("x", xCoord);
                    paramMap.put("y", yCoord);
                    paramMap.put("seq", to.getSeq());
                    toiletService.updateSido(paramMap);
                } else {
                    paramMap.put("sido", "none");
                    paramMap.put("sigungu", "none");
                    paramMap.put("x", "none");
                    paramMap.put("y", "none");
                    paramMap.put("seq", to.getSeq());
                    toiletService.updateSido(paramMap);
                }
            }else{
                paramMap.put("sido", "delete");
                paramMap.put("sigungu", "delete");
                paramMap.put("x", "delete");
                paramMap.put("y", "delete");
                paramMap.put("seq", to.getSeq());
                toiletService.updateSido(paramMap);
            }

        } catch (Exception e) {
            System.out.println("API 호출에 실패하였습니다.");
            e.printStackTrace();
        }
        }
        System.out.println("종료");
    }


}
