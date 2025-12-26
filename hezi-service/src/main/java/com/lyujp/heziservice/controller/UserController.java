package com.lyujp.heziservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.lyujp.heziservice.config.ABS_CONFIG;
import com.lyujp.heziservice.dto.common.LoginDto;
import com.lyujp.heziservice.dto.common.ResDto;
import com.lyujp.heziservice.dto.common.UserInfoDto;
import com.lyujp.heziservice.mapper.UserMapper;
import com.lyujp.heziservice.util.IpHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResDto<Void> login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest){
        if(loginDto == null){
            return ResDto.fail("参数不得为空");
        }
        String username = "";
        String password = "";
        try{
            username = loginDto.getUsername();
            password = loginDto.getPassword();
            if(username == null || username.isBlank() || password == null || password.isBlank()){
                return ResDto.fail("用户名或密码不得为空");
            }
        }catch (Exception ignore){
            return ResDto.fail("参数错误");
        }
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

    @PostMapping("/logout")
    public ResDto<Void> logout(){
        StpUtil.logout();
        return ResDto.success();
    }

    @RequestMapping("/login/add_admin/{username}/{password}")
    public ResDto<Void> addAdmin(@PathVariable String username,@PathVariable String password){
        Long userCount = userMapper.getUserCount();
        if(userCount == 0){
            if(username == null || username.isBlank() || password == null || password.isBlank()){
                return ResDto.fail(500,"参数不得为空");
            }
            Random random = new Random();
            String salt = String.valueOf(random.nextInt(10000,99999));
            userMapper.addAdmin(username, DigestUtil.sha512Hex(password + salt), salt);
            return ResDto.success();
        }
        return ResDto.fail(500);
    }

    @RequestMapping("/admin/user/userinfo")
    public ResDto<UserInfoDto> getUserInfo(){
        return ResDto.success(userMapper.getUserInfo(Long.parseLong(StpUtil.getLoginId(-1).toString())));
    }

    @PostMapping("/check_login")
    public ResDto<Void> checkLogin(){
        if(StpUtil.isLogin()){
            return ResDto.success(StpUtil.getTokenValue());
        }else{
            return ResDto.fail(403);
        }
    }
}
