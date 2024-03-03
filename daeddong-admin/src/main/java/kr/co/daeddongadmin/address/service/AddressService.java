package kr.co.daeddongadmin.address.service;

import kr.co.daeddongadmin.address.domain.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {
    public List<Address> getSiGunguList(Map<String, Object> paramMap);

}
