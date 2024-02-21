package kr.co.daeddongadmin.toiletMap.controller;

import kr.co.daeddongadmin.toiletMap.service.ToiletService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class test {
    @Autowired
    private ToiletService toiletService;

    public static void main(String[] args) {
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
                    paramMap.put("name",  row.getCell(2));
                    paramMap.put("latitiude",  row.getCell(18));
                    paramMap.put("longitude",  row.getCell(19));
                    paramMap.put("address",  row.getCell(3));
                    paramMap.put("si",  "서울특별시");
                    paramMap.put("gungu",  "");
                    paramMap.put("floor",  "");
                    paramMap.put("openTime",  row.getCell(16));
                    paramMap.put("closeTime",  row.getCell(16));
                    paramMap.put("manageAgency",  row.getCell(14));
                    paramMap.put("maNum",  row.getCell(15));
                    paramMap.put("tolietType",  row.getCell(1));
                    paramMap.put("countMan",  Integer.parseInt(row.getCell(5).toString())+Integer.parseInt(row.getCell(6).toString()));
                    paramMap.put("countWomen",  row.getCell(11));
                    paramMap.put("babyYn",  row.getCell(26));
                    paramMap.put("unusualYn",  row.getCell(7));
                    paramMap.put("cctvYn",  row.getCell(25));
                    paramMap.put("alarmYn",  row.getCell(23));
                    paramMap.put("pwdYn",  "N");
                    paramMap.put("pwd",  "");
                    paramMap.put("etc",  "");
                    paramMap.put("regId",  "jack");
                    paramMap.put("openYn",  "Y");
//                    new test(paramMap);
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

    // 열 인덱스를 엑셀의 열 이름으로 변환하는 메소드
    private static String getColumnName(int columnIndex) {
        StringBuilder columnName = new StringBuilder();
        while (columnIndex >= 0) {
            columnName.insert(0, (char) ('A' + columnIndex % 26));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnName.toString();
    }

}
