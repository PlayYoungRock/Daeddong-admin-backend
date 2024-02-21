package kr.co.daeddongadmin.toiletMap.service.impl;

import kr.co.daeddongadmin.toiletMap.domain.Toilet;
import kr.co.daeddongadmin.toiletMap.repository.ToiletRepository;
import kr.co.daeddongadmin.toiletMap.service.ToiletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ToiletServiceImpl implements ToiletService {
	@Autowired
	private ToiletRepository toiletRepository;

	/**
	 * 진행중인 이벤트 리스트
	 */
	@Override
	public List<Toilet> getToiletList(int index, int count) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("index", index);
		paramMap.put("count", count);
		return toiletRepository.getToiletList(paramMap);
	}

	@Override
	public void insertToilet(Map<String, Object> paramMap){
		toiletRepository.insertToilet(paramMap);
	}

}
