package kr.co.daeddongadmin.address.service.impl;

import kr.co.daeddongadmin.address.domain.Address;
import kr.co.daeddongadmin.address.repository.AddressRepository;
import kr.co.daeddongadmin.address.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;

	/**
	 * 진행중인 이벤트 리스트
	 */
	@Override
	public List<Address> getSiGunguList(Map<String,Object> paramMap) {
		return addressRepository.getSiGunguList(paramMap);
	}

}
