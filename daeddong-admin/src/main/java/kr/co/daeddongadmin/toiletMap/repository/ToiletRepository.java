package kr.co.daeddongadmin.toiletMap.repository;

import kr.co.daeddongadmin.toiletMap.domain.Toilet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ToiletRepository {
    List<Toilet> getToiletList(Map<String,Object> paramMap);

    void insertToilet(Map<String,Object> paramMap);
}
