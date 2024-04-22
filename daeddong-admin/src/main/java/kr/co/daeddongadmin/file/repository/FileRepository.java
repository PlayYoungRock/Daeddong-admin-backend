package kr.co.daeddongadmin.file.repository;

import kr.co.daeddongadmin.file.domain.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileRepository {
    int insertFile(File file);
}
