package kr.co.daeddongadmin.admin.repository;

import kr.co.daeddongadmin.admin.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Mapper
public interface AdminRepository {
    Optional<Admin> findByUsername(String username);
}
