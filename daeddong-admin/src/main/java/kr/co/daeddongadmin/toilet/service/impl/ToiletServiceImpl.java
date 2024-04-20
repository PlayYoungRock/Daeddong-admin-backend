package kr.co.daeddongadmin.toilet.service.impl;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import kr.co.daeddongadmin.toilet.repository.ToiletRepository;
import kr.co.daeddongadmin.toilet.service.ToiletService;
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

	@Override
	public List<Toilet> getToiletList(Map<String, Object> paramMap) {
		return toiletRepository.getToiletList(paramMap);
	}

	@Override
	public int getToiletCount(Map<String, Object> paramMap) {
		return toiletRepository.getToiletCount(paramMap);
	}

	@Override
	public Toilet getToiletInfo(String seq) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("seq", seq);
		return toiletRepository.getToiletInfo(paramMap);
	}
	@Override
	public Toilet getIsNullSidoToiletList() {
		return toiletRepository.getIsNullSidoToiletList();
	}

	@Override
	public int deleteToilet(String seq) {
		return toiletRepository.deleteToilet(seq);
	}
	@Override
	public int insertToilet(Toilet toilet) {return toiletRepository.insertToilet(toilet);}
	@Override
	public int updateToilet(Toilet toilet) {return toiletRepository.updateToilet(toilet);}
	@Override
	public void updateSido(Map<String, Object> paramMap){
		toiletRepository.updateSido(paramMap);
	}

}
