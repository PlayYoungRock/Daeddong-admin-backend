package kr.co.daeddongadmin.util;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class NaverUtil {

    public static Map<String, Object> getAddressInfo(String address,String clientId, String clientSecret) {
        System.out.println("clientId = " + clientId);
        Map<String, Object> paramMap = new HashMap<>();
        try {
            if (StringUtil.isNotBlank(address)) {
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
                } else {
                    paramMap.put("sido", "none");
                    paramMap.put("sigungu", "none");
                    paramMap.put("x", "none");
                    paramMap.put("y", "none");
                }
            } else {
                paramMap.put("sido", "delete");
                paramMap.put("sigungu", "delete");
                paramMap.put("x", "delete");
                paramMap.put("y", "delete");
            }

        } catch (Exception e) {
            System.out.println("API 호출에 실패하였습니다.");
            e.printStackTrace();
        }
        return paramMap;
    }

}
