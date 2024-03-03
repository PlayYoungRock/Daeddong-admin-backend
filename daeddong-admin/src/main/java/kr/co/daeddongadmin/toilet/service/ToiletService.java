package kr.co.daeddongadmin.toilet.service;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import java.util.List;
import java.util.Map;

public interface ToiletService {
    public List<Toilet> getToiletList(int index, int count, String gungu, String searchWord);

    int getToiletCount(String gungu, String searchWord);

    Toilet getToiletInfo(String seq);

    int deleteToilet(String seq);

    void insertToilet(Map<String, Object> paramMap);
}
