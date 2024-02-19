package kr.co.daeddongadmin.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    private String name;

    @GetMapping("/test")
    @ResponseBody
    public Map<String,Object> main(@RequestParam String Sido){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("화장실명","은평구청 공용화장실");
            resultMap.put("위치",Sido);
        return resultMap;
    }
}
