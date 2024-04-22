package kr.co.daeddongadmin.common;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonUtil {

    /**
     * 지정된 이름의 파일 확장자를 검사함
     * @param files
     * @param fieldName <input type='file' name=fieldName
     * @return
     * @throws IOException
     */
    public static String fileUploadBeforeCheck(Map<String, MultipartFile> files, long size, String fieldName, String allowExt, boolean imageOnly, String required) throws IOException {
        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        boolean isRequired = false;
        while (itr.hasNext()) {
            Map.Entry<String, MultipartFile> entry = itr.next();

            file = entry.getValue();
            String orginFileName = file.getOriginalFilename();

            //--------------------------------------
            // 원 파일명이 없는 경우 처리
            // (첨부가 되지 않은 input file type)
            //--------------------------------------
            if ("".equals(orginFileName)) {
                continue;
            }
            ////------------------------------------

            long _size = file.getSize();
            int index = orginFileName.lastIndexOf(".");
            if(fieldName.equals(file.getName())) {
                isRequired = true;
                if(index < 0){ return "확장자가 없는 파일은 첨부 할 수 없습니다." ; } //확장자 없는 파일 첨부 금지.

                String fileExt = orginFileName.substring(index + 1);

/*				Boolean isRealExt = getRealFileExtCheck(multipartToFile(file),fileExt);
				if(!isRealExt) return "확장자 변조가 확인되었습니다. 올바른 파일을 업로드 해주시기 바랍니다.";*/

                if(regEx("JPG", fileExt.toLowerCase())  || fileExt.length() > 19 ) { //금지확장자에 해당하면
                    return "업로드 할 수 없는 확장자 입니다.";
                }
                if(_size > size) {
                    return String.format("첨부파일 용량이 초과 했습니다.\\n  용량제한 : %s, 첨부파일 용량 : %s  ", fileSize(size), fileSize(_size));
                }
                if("".equals(fileExt)) {
                    return "확장자가 없는 파일은 첨부 할 수 없습니다.";
                }
                if(!regEx(allowExt,fileExt.toLowerCase())) {
                    return String.format("첨부 가능한 파일 형식이 아닙니다.\\n가능한 파일 형식 : %s", allowExt.replace("|", ","));
                }
                if(imageOnly && // 이미지만 받도록 설정된 경우
                        !regEx("(image.jpg|image.jpeg|image.gif|image.png|image.bmp|image.x-png|image.pjpeg)",file.getContentType().toLowerCase())) {
                    return "이미지 파일만 첨부가 가능합니다.";
                }
            }
        }
        if("required".equals(required ) && isRequired == false) {
            return "파일을 첨부 해 주세요.";
        }
        return "";
    }

    /**
     * 정규식 검사 메소드(문자열이 지정된 패턴과 일치하는지 여부 검사)
     * @param ptn 패턴
     * @param str 검사할 문자열
     * @return true | false
     */
    public static boolean regEx(String ptn, String str) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ptn,java.util.regex.Pattern.CASE_INSENSITIVE | java.util.regex.Pattern.MULTILINE );
        java.util.regex.Matcher m = p.matcher(str);
        return m.find();
    }

    public static String fileSize(long fileSize ) {
        String strResult = "";
        if(fileSize> 1024000000){
            fileSize = fileSize / 1024000000;
            strResult = fileSize + " GB";
        }else if(fileSize > 1024000){
            fileSize = fileSize / 1024000;
            strResult = fileSize + " MB";
        }else if(fileSize > 1024){
            fileSize = fileSize / 1024;
            strResult = fileSize + " KB";
        }else{
            strResult = fileSize + " B";
        }
        return strResult;
    }

    public static HashMap fileUpload (
            Map<String, MultipartFile> files, String fieldName, String storePath, String siteCode, String rootPath, boolean isImage,
            String bcThumbwidth, String bcThumbheight, boolean ratio) throws IOException {

        if("".equals(siteCode)) siteCode = "COMMON";
        storePath = rootPath + System.getProperty("file.separator") + storePath;
        storePath = getFilePathReplace(storePath);
        File saveFolder = new File(storePath);

        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.setExecutable(false, true);
            saveFolder.setReadable(true);
            saveFolder.setWritable(true, true);
            saveFolder.mkdirs();
        }

        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        String filePath = "";
        HashMap hm = null;
        while (itr.hasNext()) {
            Map.Entry<String, MultipartFile> entry = itr.next();

            file = entry.getValue();
            String originalFileName = file.getOriginalFilename();

            //--------------------------------------
            // 원 파일명이 없는 경우 처리
            // (첨부가 되지 않은 input file type)
            //--------------------------------------
            if ("".equals(originalFileName)) {
                continue;
            }
            ////------------------------------------
            if(fieldName.equals(file.getName())) {
                hm = new HashMap();
                int index = originalFileName.lastIndexOf(".");
                String fileExt = originalFileName.substring(index + 1);
                String newName = "";
//                String newName = getUniqueFileName(storePath, fileExt); //날짜 20121011+랜덤8자리
                long _size = file.getSize();

                filePath = storePath + File.separator + newName + "." + fileExt ;
                filePath = getFilePathReplace(filePath);
                file.transferTo(new File(filePath));

                if(isImage) {
                    // 실제 이미지인지 확인
                    String mimeType = file.getContentType();
                    // 실제 이미지가 아니면 패스
                    if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/pjpeg") && !mimeType.equals("image/gif") && !mimeType.equals("image/x-png") && !mimeType.equals("image/png"))
                        return null;

                    thumbnail(storePath, newName+"."+fileExt, "B", Integer.parseInt(bcThumbwidth), Integer.parseInt(bcThumbheight), ratio) ;
                }

                hm.put("F_SAVENAME", newName + "." + fileExt);
                hm.put("F_EXT", fileExt);
                hm.put("F_FILESIZE", _size);
                hm.put("F_ORGNAME", originalFileName);
            }
        }

        return hm;
    }

    public static String getFilePathReplace(String str) {
        str = str.replace("?", "");
        str = str.replace("[*]", "");
        str = str.replace("[\"]", "");
        return str;
    }

    public static String thumbnail(String path, String filename, String tail, int width, int height, boolean ratio) {
//        if("".equals(isNull(path,"")) || "".equals(isNull(filename,""))) {
//            return "";
//        }
        path = getFilePathReplace(path);
        filename = getFilePathReplace(filename);
        tail = getFilePathReplace(tail);

        File saveFolder = new File(path);

        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.setExecutable(false, true);
            saveFolder.setReadable(true);
            saveFolder.setWritable(true, true);
            saveFolder.mkdirs();
        }
        String fileExt = filename.substring(filename.lastIndexOf(".") + 1);
        String toFilename = filename.substring(0, filename.lastIndexOf(".")) + tail + "." + fileExt;
        try {
            //원본파일, 사이즈, 저장파일 경로 + 파일명
//			Thumbnails.of(new File(path + System.getProperty("file.separator") + filename))
//							.size(width, height)
//							.imageType(BufferedImage.TYPE_INT_ARGB)
//							.toFile(saveFolder.getAbsolutePath() + System.getProperty("file.separator") + toFilename);
            String fullFileName = getFilePathReplace(path + System.getProperty("file.separator") + filename);
            java.awt.image.BufferedImage  originalImage = javax.imageio.ImageIO.read(new File(fullFileName));
//            java.awt.image.BufferedImage thumbnail = 	Thumbnails.of(originalImage)        .size(width, height).imageType(BufferedImage.TYPE_INT_ARGB)        .asBufferedImage();
//            Thumbnails.of(thumbnail).size(width, height).keepAspectRatio(ratio).toFile(saveFolder.getAbsolutePath() + System.getProperty("file.separator") + toFilename);

        } catch (IOException e) {
//            logger.error("예외 상황 발생");
        }
        return toFilename;
    }

    // Request Header에서 토큰 정보 추출
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String toSHA256 (String original) throws NoSuchAlgorithmException {
        String SHA = "";

            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(original.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        return SHA;
    }

    public static Map<String, Object> customMap(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<>();

        Enumeration<String> en = request.getParameterNames();
        while ( en.hasMoreElements() ){
            String key    = en.nextElement();
            String value  = request.getParameter(key);
            param.put(key , value);
        }
        return param;
    }

    /**
     * 오늘날짜를 패턴에 맞게 가져오기
     * @param pattern (yyyy-MM-dd : 2019-02-28 , yyyy-MM-dd HH:mm:ss.SSS : 2019-02-28 01:59:28.002
     * @return
     */
    public static String getDatePattern(String pattern) {
        String rtnStr = null;
        try {
            java.text.SimpleDateFormat sdfCurrent = new java.text.SimpleDateFormat(pattern, Locale.KOREA);
            java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
            rtnStr = sdfCurrent.format(ts.getTime());
        } catch (IllegalArgumentException e) {
//            logger.error("예외 상황 발생");
        }
        return rtnStr;
    }

    /**
     * 영문 대, 소문자, 숫자가 조합된 랜덤한 문자열을 구한다.
     * @param length 구하고자 하는 문자열 갯수
     * @return 입력된 길이의 랜덤 문자열
     */
    public static String getRandomString(int length) {
        String[] arrString = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","x","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","X"};
        java.util.Random rnd = new java.util.Random();
        String returnValue = "";
        for(int i =0; i< length; i++) {
            returnValue+= arrString[rnd.nextInt(arrString.length-1)];
        }
        return returnValue;

    }

    public static long gapTime(String baseTimeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime baseTime = LocalDateTime.parse(baseTimeStr, formatter);

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 시간 차이 계산
        Duration duration = Duration.between(baseTime, now);
        long minutesDifference = duration.toMinutes();

        return minutesDifference;
    }

    public static String getAdminIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

}
