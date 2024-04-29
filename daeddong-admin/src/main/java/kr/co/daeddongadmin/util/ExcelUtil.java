//package kr.co.daeddongadmin.util;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class ExcelUtil {
//    {
//
//        private int totalRowCount = 0; // 전체 행 개수
//        private int successRowCount = 0;  // 처리완료 된 데이터 개수
//        private int currentStateCount = 0; // 진행중인 데이터 행 위치
//        //위의 4개 변수들을 다 더한 값이다.
//        //for문이 돌면서 현재 몇개가 처리되었는지 알게 해주는 변수
//        private String currentState="READY"; //현재 상태, 준비단계
//
//        public int getTotalRowCount() {
//        return totalRowCount;
//    }
//        public void setTotalRowCount(int totalRowCount) {
//        this.totalRowCount = totalRowCount;
//    }
//
//        public int getSuccessRowCount() {
//        return successRowCount;
//    }
//        public void setSuccessRowCount(int successRowCount) {
//        this.successRowCount = successRowCount;
//    }
//
//        public int getCurrentStateCount() {
//        return currentStateCount;
//    }
//        public void setCurrentStateCount(int currentStateCount) {
//        this.currentStateCount = currentStateCount;
//    }
//
//        public String getCurrentState() {
//        return currentState;
//    }
//        public void setCurrentState(String currentState) {
//        this.currentState = currentState;
//    }
//
//        /**
//         * 엑셀 업로드
//         * @param searchVO
//         * @return
//         * @throws IOException
//         */
//        public List<Map<String,Obejct>> getListMapFromXls(Map<String, MultipartFile> files, String fieldName) throws IOException{
//        return getListMapFromXls(files, fieldName, 1, 2);
//    }
//
//        /**
//         * 엑셀 업로드
//         * @param searchVO
//         * @return
//         * @throws IOException
//         */
//        public List<ManpaMap> getListMapFromXls(Map<String, MultipartFile> files, String fieldName, int paramRow, int startRow) throws IOException {
//
//        List<ManpaMap> datalist = new ArrayList<ManpaMap>();
//        if(startRow == 0) startRow = 2;
//        if(paramRow == 0) paramRow = 1;
//        if(startRow <= paramRow){
//            //System.out.println("엑셀 설정값이 올바르지 않습니다. startRow는 반드시 paramRow보다 커야함.");
//            return datalist;
//        }
//
//        String tempFolder = "XLSTEMP";
//
//        HashMap<?, ?> hmFile = CommUtil.fileUpload(files, fieldName, tempFolder);
//        String file_name = System.getProperty("file.separator") + tempFolder+System.getProperty("file.separator") +(String) hmFile.get("F_SAVENAME");
//        int cellNo = 0;
//        String position = "";
//        int realrow = 0;
//
//        if(file_name != null && !file_name.equals("")) {
//
//            String tempFullPath = CommUtil.getFileRoot("")+file_name;
//            InputStream inp = null;
//            try {
//
//                inp = new FileInputStream(tempFullPath);
//                POIFSFileSystem fileSystem = new POIFSFileSystem(inp);
//                HSSFWorkbook wb = new HSSFWorkbook(fileSystem);
//                HSSFSheet sheet = wb.getSheetAt(0);
//                Iterator<Row> rows = sheet.rowIterator();
//                //HSSFRow row = (HSSFRow) rows.next();
//                totalRowCount = (sheet.getLastRowNum()+2) - startRow;
//                String[] paramName = new String[1];
//
//                setCurrentState("START"); // (프로그레스) 엑셀을 읽어와서 전체 행 개수를 가지고 오게 되면 상태를 START로 변경해준다.
//
//                while(rows.hasNext()){
//                    realrow++; // (프로그레스) 현재 진행중인 카운트
//                    HSSFRow row = (HSSFRow)rows.next();
//                    cellNo = row.getFirstCellNum(); // cellNo 리셋
//                    int cellsize = row.getLastCellNum()-row.getFirstCellNum();
//                    //Iterator<Cell> cells = row.cellIterator();
//                    if(realrow == paramRow){
//                        paramName = new String[cellsize];
//                        for(int i = 0; i < cellsize;i++){
//                            HSSFCell cell =  row.getCell(cellNo);// 0 이 먼저 대입된 후에 ++ 진행
//                            paramName[i] = getExcelCellValueIntString(cell).trim();
//                            //System.out.println("["+i+"] 파라메터네임 : "+paramName[i]);
//                            cellNo++;
//                        }
//                    }else if(realrow >= startRow){
//                        currentStateCount++; // (프로그레스) 현재 진행중인 카운트
//                        ManpaMap dataMap = new ManpaMap();
//                        for(int i = 0; i < cellsize;i++){
//                            position = paramName[i];
//                            HSSFCell cell =  row.getCell(cellNo);// 0 이 먼저 대입된 후에 ++ 진행
//                            dataMap.put(position,getExcelCellValueIntString(cell).trim());
//                            //System.out.println("["+paramName[i]+"] = "+getExcelCellValueIntString(cell).trim());
//                            cellNo++;
//                        }
//                        datalist.add(dataMap);
//                    }
//                    /*테스트용 일부러 delay시키기위함*/
///*                	try {
//                		TimeUnit.MILLISECONDS.sleep(50);
//	    			} catch (InterruptedException e) {
//	    				LOGGER.error("Time Sleep Error!");
//	    			}*/
//                    successRowCount++; // (프로그레스) 처리완료 된 데이터 카운트
//                }
//
//            } catch(IOException e) {
//                LOGGER.error((currentStateCount) + "번째 데이터 " +  position + " 항목에 문제가 있습니다.");
//                return datalist;
//            } finally {
//                CommUtil.fileDel(tempFullPath);
//                if(inp != null)
//                    try {
//                        inp.close();
//                    } catch (IOException e) {
//                        LOGGER.error("inp FileInputStream close IOException 발생");
//                    }
//            }
//            setCurrentState("FINISH"); // (프로그레스) 엑셀업로드가 완료되면 FINISH
//            //System.out.println("엑셀업로드가 정상처리되었습니다.");
//            try {
//                TimeUnit.SECONDS.sleep(2);
//
//            } catch (InterruptedException e) {
//                LOGGER.error("Time Sleep Error!");
//            }
//        }
//        return datalist;
//    }
//
//        @SuppressWarnings("deprecation")
//        public String getExcelCellValueIntString(HSSFCell cell) {
//        String cellValue = "";
//        if (cell == null) {
//            return cellValue;
//        }
//
//        int type = cell.getCellType();
//
//        switch (type) {
//            case HSSFCell.CELL_TYPE_BLANK:
//                break;
//            case HSSFCell.CELL_TYPE_NUMERIC:
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                    cellValue = formatter.format(cell.getDateCellValue());
//                }
//                else {
//                    cellValue = ""+(long)cell.getNumericCellValue();
//                }
//                break;
//            case HSSFCell.CELL_TYPE_STRING:
//                cellValue = cell.getStringCellValue();
//                break;
//            case HSSFCell.CELL_TYPE_ERROR:
//                cellValue = Byte.toString(cell.getErrorCellValue());
//                break;
//            case HSSFCell.CELL_TYPE_BOOLEAN:
//                cellValue = Boolean.toString(cell.getBooleanCellValue());
//                break;
//            case HSSFCell.CELL_TYPE_FORMULA:
//                cellValue = cell.getCellFormula();
//                break;
//        }
//        return cellValue;
//    }
//
//        @SuppressWarnings("deprecation")
//        public String getExcelCellValueStringXSSFCell(XSSFCell cell) {
//        String cellValue = "";
//        if (cell == null) {
//            return cellValue;
//        }
//
//        int type = cell.getCellType();
//        switch (type) {
//            case XSSFCell.CELL_TYPE_BLANK:
//                break;
//            case XSSFCell.CELL_TYPE_NUMERIC:
//                cellValue = Double.toString(cell.getNumericCellValue());
//                break;
//            case XSSFCell.CELL_TYPE_STRING:
//                cellValue = cell.getStringCellValue();
//                break;
//            case XSSFCell.CELL_TYPE_ERROR:
//                cellValue = Byte.toString(cell.getErrorCellValue());
//                break;
//            case XSSFCell.CELL_TYPE_BOOLEAN:
//                cellValue = Boolean.toString(cell.getBooleanCellValue());
//                break;
//            case XSSFCell.CELL_TYPE_FORMULA:
//                cellValue = cell.getCellFormula();
//                break;
//        }
//        return cellValue;
//    }
//
//        public ManpaMap getProgressValue() {
//
//        ManpaMap returnMap = new ManpaMap();
//        returnMap.put("state", currentState);
//
//        int resultData = 0;
//
//        BigDecimal aGd = new BigDecimal(currentStateCount);
//        BigDecimal bGd = new BigDecimal(totalRowCount);
//
//        resultData = aGd.divide(bGd, 2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).intValue();
//
//        returnMap.put("curCnt", currentStateCount);
//        returnMap.put("totCnt", totalRowCount);
//        returnMap.put("resultData", resultData);
//
//        return returnMap;
//    }
//
//        public void resetProgressValue() {
//        this.totalRowCount = 0; // 전체 행 개수
//        this.successRowCount = 0;  // 처리완료 된 데이터 개수
//        this.currentStateCount = 0; // 진행중인 데이터 행 위치
//        this.currentState="READY"; //현재 상태, 준비단계
//    }
//    }
//}
