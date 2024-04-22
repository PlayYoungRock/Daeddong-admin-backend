package kr.co.daeddongadmin.file.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class File {
    private String seq;
    private String fileId;
    private String inputDate;
    private String fileName;
    private String fileOrgName;
    private String filePath;
}
