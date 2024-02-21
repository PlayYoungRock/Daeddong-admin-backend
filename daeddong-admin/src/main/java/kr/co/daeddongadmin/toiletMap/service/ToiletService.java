package kr.co.daeddongadmin.toiletMap.service;

import kr.co.daeddongadmin.toiletMap.domain.Toilet;
import java.util.List;
import java.util.Map;

public interface ToiletService {
    public List<Toilet> getToiletList(int index, int count);

    void insertToilet(Map<String, Object> paramMap);
}
