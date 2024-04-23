package kr.co.daeddongadmin.admin.repository;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.AdminLog;
import kr.co.daeddongadmin.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface AdminRepository {
    Optional<Admin> findByUsername(String username);

    void updatePasswordFailCount(String username);

    void updatePasswordReset(String username);

    void insertAdminLoginLog(AdminLog adminLog);

    Admin selectPasswordFailCount(String username);

    List<Admin> selectAdminList(Map<String,Object> paramMap);
    int selectAdminCount(Map<String,Object> paramMap);
    Admin selectAdminInfo(String id);

    int insertAdmin(Admin admin);
    int updateAdmin(Admin admin);
    int deleteAdmin(String id);
}
