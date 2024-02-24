package kr.co.daeddongadmin.toilet.service;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import java.util.List;
import java.util.Map;

public interface ToiletService {
    public List<Toilet> getToiletList(int index, int count);

    Toilet getToiletInfo(String seq);

    void insertToilet(Map<String, Object> paramMap);
}
