package kr.co.daeddongadmin.toilet.repository;

import kr.co.daeddongadmin.toilet.domain.Toilet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ToiletRepository {
    List<Toilet> getToiletList(Map<String,Object> paramMap);
    int getToiletCount(Map<String,Object> paramMap);
    Toilet getToiletInfo(Map<String,Object> paramMap);
    Toilet getIsNullSidoToiletList();
    int deleteToilet(String seq);

    int insertToilet(Toilet toilet);
    void updateSido(Map<String,Object> paramMap);
}
