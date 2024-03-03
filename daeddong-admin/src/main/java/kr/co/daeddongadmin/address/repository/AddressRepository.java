package kr.co.daeddongadmin.address.repository;

import kr.co.daeddongadmin.address.domain.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddressRepository {
    List<Address> getSiGunguList(Map<String,Object> paramMap);
}
