package kr.co.daeddongadmin.admin.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AdminLog {
    private String loginId;
    private String loginIp;
    private String loginResult;
    private String loginResultMsg;
    private String loginDate;
}
