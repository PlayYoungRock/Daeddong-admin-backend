package kr.co.daeddongadmin.admin.service;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.JwtToken;
import kr.co.daeddongadmin.board.domain.Board;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AdminService {
    JwtToken signIn(String username, String password, HttpServletRequest request);

    List<Admin> selectAdminList(Map<String, Object> paramMap);
    int selectAdminCount(Map<String, Object> paramMap);
    Admin selectAdminInfo(String id);

    int insertAdmin(Admin admin);
    int updateAdmin(Admin admin);
    int deleteAdmin(String id);
}
