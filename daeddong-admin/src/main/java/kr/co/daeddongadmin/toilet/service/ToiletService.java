package kr.co.daeddongadmin.toilet.service;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import java.util.List;
import java.util.Map;

public interface ToiletService {
    List<Toilet> getToiletList(Map<String, Object> paramMap);
    int getToiletCount(Map<String, Object> paramMap);
    Toilet getToiletInfo(String seq);
    Toilet getIsNullSidoToiletList();
    int deleteToilet(String seq);
    int insertToilet(Toilet toilet);
    int updateToilet(Toilet toilet);
    void updateSido(Map<String, Object> paramMap);
}
