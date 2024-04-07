package kr.co.daeddongadmin.common;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

}
