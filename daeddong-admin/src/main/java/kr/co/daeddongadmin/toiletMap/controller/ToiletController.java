package kr.co.daeddongadmin.toiletMap.controller;

import kr.co.daeddongadmin.toiletMap.domain.Toilet;
import kr.co.daeddongadmin.toiletMap.service.ToiletService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping("/toiletList")
    @ResponseBody
    public Map<String,Object> main(@RequestParam(value="index", defaultValue="0")int index, @RequestParam(value="count", defaultValue="10")int count){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Toilet> toiletList = toiletService.getToiletList(index,count);
            resultMap.put("toiletList",toiletList);
        return resultMap;
    }

    @GetMapping("/insert")
    @ResponseBody
    public void insert() {
        try {
            // 엑셀 파일 경로
            String excelFilePath = "/Users/three/Downloads/123.xlsx";

            // 파일을 읽기 위한 FileInputStream 생성
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

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
                    paramMap.put("name", row.getCell(2).toString());
                    paramMap.put("latitiude", row.getCell(18).toString());
                    paramMap.put("longitude", row.getCell(19).toString());
                    paramMap.put("address", row.getCell(3).toString());
                    paramMap.put("si", "서울특별시");
                    paramMap.put("gungu", "");
                    paramMap.put("floor", "");
                    paramMap.put("openTime", row.getCell(16).toString());
                    paramMap.put("closeTime", row.getCell(16).toString());
                    paramMap.put("manageAgency", row.getCell(14).toString());
                    paramMap.put("maNum", row.getCell(15).toString());
                    paramMap.put("tolietType", row.getCell(1).toString());
                    paramMap.put("countMan", row.getCell(5).toString());
                    paramMap.put("countWomen", row.getCell(11).toString());
                    paramMap.put("babyYn", row.getCell(26).toString());
                    paramMap.put("unusualYn", row.getCell(7).toString());
                    paramMap.put("cctvYn", row.getCell(25).toString());
                    paramMap.put("alarmYn", row.getCell(23).toString());
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("e = " + e.getMessage());
        }
    }

}
