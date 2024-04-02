package kr.co.daeddongadmin.toilet.controller;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import kr.co.daeddongadmin.toilet.service.ToiletService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping("/toiletList")
    @ResponseBody
    public Map<String,Object> getToiletList(@RequestParam(value="index", defaultValue="0")int index,
                                            @RequestParam(value="count", defaultValue="10")int count,
                                            @RequestParam(value="gungu", defaultValue="")String gungu,
                                            @RequestParam(value="sido", defaultValue="")String sido,
                                            @RequestParam(value="searchWord", defaultValue="")String searchWord
    ){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int totalCount = toiletService.getToiletCount(gungu,searchWord);
        List<Toilet> toiletList = toiletService.getToiletList(index,count,gungu,searchWord,sido);
            resultMap.put("resultCode","0000");
            resultMap.put("totalCount",totalCount);
            resultMap.put("toiletList",toiletList);
        return resultMap;
    }

    @GetMapping("/toiletInfo")
    @ResponseBody
    public Map<String,Object> getToiletInfo(@RequestParam(value="seq", defaultValue="0")String seq){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Toilet toiletInfo = toiletService.getToiletInfo(seq);
        if(toiletInfo != null){
            resultMap.put("resultCode","0000");
            resultMap.put("toiletInfo",toiletInfo);
        }else{
            resultMap.put("resultCode","9999");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }

        return resultMap;
    }

    @DeleteMapping("/deleteToilet")
    @ResponseBody
    public Map<String,Object> deleteToilet(@RequestParam(value="seq", defaultValue="0")String seq){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        int deleteResult = toiletService.deleteToilet(seq);
        if(deleteResult > 0){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","삭제되었습니다.");
        }else{
            resultMap.put("resultCode","9999");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }

        return resultMap;
    }

    @GetMapping("/insert")
    @ResponseBody
    public void insert() {
        try {
            // 엑셀 파일 경로
            String excelFilePath = "/Users/three/Downloads/17.xlsx";

            // 파일을 읽기 위한 FileInputStream 생성
//            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            InputStream inputStream = new FileInputStream(excelFilePath);

            // Workbook 생성 (엑셀 파일 읽기)
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 첫 번째 시트 가져오기
            // 첫 번째 시트 가져오기
            Sheet sheet = workbook.getSheetAt(0);

            // 첫 번째 행은 건너뛰기 (A부터 AD까지)

            // 첫 번째 행은 건너뛰기 (A부터 AD까지)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    // 각 열의 데이터 추출하여 Map에 담기
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("name", new String(row.getCell(3).toString().getBytes(StandardCharsets.UTF_8)));
                    paramMap.put("address", row.getCell(4).toString().split(",")[0]);
                    paramMap.put("openTime", row.getCell(18).toString());
                    paramMap.put("closeTime", row.getCell(18).toString());
                    //관리기간
                    paramMap.put("manageAgency", row.getCell(15).toString());
                    //관리기간 번호
                    paramMap.put("maNum", row.getCell(16).toString());
                    paramMap.put("toiletType", row.getCell(1).toString());
                    paramMap.put("countMan", row.getCell(6).toString());
                    paramMap.put("countWomen", row.getCell(12).toString());
                    paramMap.put("babyYn", row.getCell(28).toString());
                    //장애인화장실 여부
                    paramMap.put("unusualYn", row.getCell(8).toString());
                    paramMap.put("cctvYn", row.getCell(27).toString());
                    paramMap.put("alarmYn", row.getCell(25).toString());
                    paramMap.put("pwdYn", "N");
                    paramMap.put("pwd", "");
                    paramMap.put("etc", "");
                    paramMap.put("regId", "jack");
                    paramMap.put("openYn", "Y");
                    toiletService.insertToilet(paramMap);
//                    System.out.println("paramMap = " + paramMap);
                }
            }

            // Workbook 및 InputStream 닫기
            workbook.close();
            inputStream.close();
            System.out.println("종료");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("e = " + e.getMessage());
        }
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
                System.out.println("address = " + address);
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
                System.out.println("totcnt = " + totcnt);
                if (totcnt > 0) {


                    // addresses 배열에서 첫 번째 주소 정보 추출
                    JSONArray addresses = jsonObject.getJSONArray("addresses");
                    JSONObject addressInfo = addresses.getJSONObject(0);

                    // 필요한 정보 추출
                    String sido = addressInfo.getJSONArray("addressElements").getJSONObject(0).getString("longName");
                    String gungu = addressInfo.getJSONArray("addressElements").getJSONObject(1).getString("longName");
                    String xCoord = addressInfo.getString("x");
                    String yCoord = addressInfo.getString("y");
                    System.out.println("gungu = " + gungu);
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
