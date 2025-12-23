package com.lyujp.heziservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.lyujp.heziservice.config.ABS_CONFIG;
import com.lyujp.heziservice.dto.ResDto;
import com.lyujp.heziservice.dto.UserInfoDto;
import com.lyujp.heziservice.mapper.UserMapper;
import com.lyujp.heziservice.util.IpHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @RequestMapping("/login/{username}/{password}")
    public ResDto<Void> login(@PathVariable String username, @PathVariable String password, HttpServletRequest httpServletRequest){
        Long failedCount = userMapper.getFailedLoginAttempt(IpHelper.getIp(httpServletRequest), ABS_CONFIG.LOGIN_ATTEMPT_MINUTES);
        if(failedCount > 10) return ResDto.fail(403,"超过最大尝试次数");
        String userSalt = userMapper.getUserSalt(username);
        if(userSalt == null){
            return ResDto.fail("用户名或密码错误");
        }
        Long userId = userMapper.login(username, DigestUtil.sha512Hex(password+userSalt));
        if(userId != null){
            StpUtil.login(userId);
            userMapper.addLoginLog(IpHelper.getIp(httpServletRequest),1L,userId);
            return ResDto.success(StpUtil.getTokenValue());
        }else{
            userMapper.addLoginLog(IpHelper.getIp(httpServletRequest),0L,null);
            return ResDto.fail(403,"用户名或密码错误");
        }
    }

    @RequestMapping("/logout")
    public ResDto<Void> logout(){
        StpUtil.logout();
        return ResDto.success();
    }

    @RequestMapping("/login/add_admin/{username}/{password}")
    public ResDto<Void> addAdmin(@PathVariable String username,@PathVariable String password){
        Long userCount = userMapper.getUserCount();
        if(userCount == 0){
            Random random = new Random();
            String salt = String.valueOf(random.nextInt(10000,99999));
            userMapper.addAdmin(username, DigestUtil.sha512Hex(password + salt), salt);
            return ResDto.success();
        }
        return ResDto.fail(500);
    }

    @RequestMapping("/admin/userinfo")
    public ResDto<UserInfoDto> getUserInfo(){
        return ResDto.success(userMapper.getUserInfo(Long.parseLong(StpUtil.getLoginId(-1).toString())));
    }

    @RequestMapping("/check_login")
    public ResDto<Void> checkLogin(){
        if(StpUtil.isLogin()){
            return ResDto.success(StpUtil.getTokenValue());
        }else{
            return ResDto.fail(403);
        }
    }
}
