package kr.co.daeddongadmin.admin.controller;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.service.AdminService;
import kr.co.daeddongadmin.common.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/adminList")
    @ResponseBody
    public Map<String,Object> adminList(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> paramMap = CommonUtil.customMap(request);
        List<Admin> adminList = adminService.selectAdminList(paramMap);	//게시글 목록
        int adminCount = adminService.selectAdminCount(paramMap);
        if(!adminList.isEmpty()){
            resultMap.put("resultCode","0000");
            resultMap.put("adminCount",adminCount);
            resultMap.put("adminList",adminList);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터 없음");
        }
        return resultMap;

    }

    @GetMapping(value = "/adminInfo")
    @ResponseBody
    public Map<String,Object> adminInfo(@RequestParam String id) throws RuntimeException {
        Map<String,Object> resultMap = new HashMap<>();
        Admin adminInfo = adminService.selectAdminInfo(id);	//게시글 목록
        if(adminInfo != null){
            resultMap.put("resultCode","0000");
            resultMap.put("adminInfo",adminInfo);
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","데이터가 없습니다.");
        }
        return resultMap;

    }

    @PostMapping(value = "/insertAdmin")
    @ResponseBody
    public Map<String,Object> insertAdmin(@RequestBody Admin admin) {
        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.insertAdmin(admin);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","등록 실패.");
        }

        return resultMap;
    }

    @PatchMapping(value = "/updateAdmin")
    @ResponseBody
    public Map<String,Object> updateBoard(@RequestBody Admin admin){
        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.updateAdmin(admin);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","등록되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","수정 실패.");
        }

        return resultMap;
    }

    @DeleteMapping(value = "/deleteAdmin")
    @ResponseBody
    public Map<String,Object> deleteAdmin(@RequestBody String id){
        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.deleteAdmin(id);
        if(result == 1){
            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","삭제되었습니다.");
        }else{
            resultMap.put("resultCode","1001");
            resultMap.put("resultMsg","삭제 실패.");
        }

        return resultMap;
    }


}
