package kr.co.daeddongadmin.address.controller;

import kr.co.daeddongadmin.address.domain.Address;
import kr.co.daeddongadmin.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/sigunguList")
    @ResponseBody
    public Map<String,Object> getSigunguList(){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Address> sigunguList = addressService.getSiGunguList(resultMap);
            resultMap.put("resultCode","0000");
            resultMap.put("sigunguList",sigunguList);
        return resultMap;
    }

}
