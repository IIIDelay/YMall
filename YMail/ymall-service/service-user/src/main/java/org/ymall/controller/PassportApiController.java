/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.controller;

import com.alibaba.fastjson.JSONObject;
import common.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.entity.UserInfo;
import org.ymall.service.IUserService;
import result.ServiceResponse;
import utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/passport")
public class PassportApiController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @PostMapping("login")
    public ServiceResponse<Map<String, Object>> login(@RequestBody UserInfo userInfo, HttpServletRequest request,
                                                      HttpServletResponse response) {
        System.out.println("进入控制器！");
        UserInfo info = userService.login(userInfo);

        if (info != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            HashMap<String, Object> map = new HashMap<>();
            map.put("nickName", info.getNickName());
            map.put("token", token);

            JSONObject userJson = new JSONObject();
            userJson.put("userId", info.getId().toString());
            userJson.put("ip", IpUtil.getIpAddress(request));
            redisTemplate.opsForValue().set(RedisConstants.USER_LOGIN_KEY_PREFIX + token, userJson.toJSONString(),
                RedisConstants.USERKEY_TIMEOUT, TimeUnit.SECONDS);
            return ServiceResponse.ok(map);
        } else {
            ServiceResponse<Map<String, Object>> sp = ServiceResponse.fail();
            sp.setMessage("this user name or password error.");
            return sp;
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("logout")
    public ServiceResponse<Void> logout(HttpServletRequest request) {
        redisTemplate.delete(RedisConstants.USER_LOGIN_KEY_PREFIX + request.getHeader("token"));
        return ServiceResponse.ok();
    }
}
