package com.example.fulldev.api.v1;

import com.example.fulldev.dto.TokenDTO;
import com.example.fulldev.dto.TokenGetDTO;
import com.example.fulldev.dto.enumeration.LoginType;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.service.WxAuthenticatianservice;
import com.example.fulldev.util.JwtToken;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "token")
@RestController
public class TokenController {
    @Autowired
    private WxAuthenticatianservice wxAuthenticatianservice;

    @RequestMapping("/get")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData) {
        Map map = new HashedMap();
        // System.out.println(userData.getType());
        String token = null;
        switch (userData.getType()) {
            case USER_WX:
                token = wxAuthenticatianservice.code2Session(userData.getAccount());
                break;
            case USER_EMAIL:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return map;
    }

    ;

    @RequestMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody TokenDTO token) {
        Map<String, Boolean> map = new HashMap<>();
        Boolean valid = JwtToken.verifyToken(token.getToken());
        map.put("is_valid", valid);
        return map;
    }
}
