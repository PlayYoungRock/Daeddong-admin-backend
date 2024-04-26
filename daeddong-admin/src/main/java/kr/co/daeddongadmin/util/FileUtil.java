package kr.co.daeddongadmin.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.daeddongadmin.file.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class FileUtil {
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public File uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일을 선택해주세요.");
        }

        // 파일 이름 가져오기
        String originalFilename = file.getOriginalFilename();

        // 파일 이름의 안전성을 위해 파일 이름을 검증하거나, 고유한 파일 이름을 생성합니다.
        if (originalFilename == null || originalFilename.contains("..")) {
            throw new IllegalArgumentException("잘못된 파일 이름입니다.");
        }

        try {
            // ObjectMetadata 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String fileName = CommonUtil.getDatePattern("yyyyMMdd")+CommonUtil.getRandomString(4);
            // 파일 업로드
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            File fileVo = new File();
            fileVo.setFilePath(amazonS3.getUrl(bucket, originalFilename).toString());
            fileVo.setFileOrgName(originalFilename);
            fileVo.setFileName(fileName);
            return fileVo;
        } catch (IOException e) {
            // IOException 예외 처리
            // 예외를 로깅하거나, 사용자에게 알리는 등의 처리
            System.err.println("파일 업로드 중 오류 발생: " + e.getMessage());
            // 필요한 경우 다른 예외를 던질 수 있습니다.
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
    }

}
