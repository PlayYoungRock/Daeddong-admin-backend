package kr.co.daeddongadmin.auth.repository;

import kr.co.daeddongadmin.auth.domain.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthRepository {
    List<Auth> getSiGunguList(Map<String,Object> paramMap);
}
