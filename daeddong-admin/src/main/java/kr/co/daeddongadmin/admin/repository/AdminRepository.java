package kr.co.daeddongadmin.admin.repository;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.AdminLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Mapper
public interface AdminRepository {
    Optional<Admin> findByUsername(String username);

    void updatePasswordFailCount(String username);

    void updatePasswordReset(String username);

    void insertAdminLoginLog(AdminLog adminLog);

    Admin selectPasswordFailCount(String username);
}
