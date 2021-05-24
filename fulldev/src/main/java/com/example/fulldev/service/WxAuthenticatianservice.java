package com.example.fulldev.service;

import com.example.fulldev.exception.ParameterException;
import com.example.fulldev.model.User;
import com.example.fulldev.repository.UserRepository;
import com.example.fulldev.util.JwtToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticatianservice {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;
    @Value("${wx.code2session}")
    private String code2sessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("'${wx.appsecret}")
    private String appsecret;

    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2sessionUrl, this.appid, this.appsecret.subSequence(1, this.appsecret.length()), code);
        RestTemplate restTemplate = new RestTemplate();
        String sessionText = restTemplate.getForObject(url, String.class);
        Map session = new HashMap();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openid = (String) session.get("openid");
        if (null == openid) {
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openid);
        if (userOptional.isPresent()) {
            //返回令牌
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = new User();
        user.setOpenid(openid);
        userRepository.save(user);
        //返回jwt令牌
        return JwtToken.makeToken(user.getId());
    }
}
