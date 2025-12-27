package com.lyujp.heziservice.mapper;

import com.lyujp.heziservice.dto.common.LoginDto;
import com.lyujp.heziservice.dto.common.UserInfoDto;

public interface UserMapper {
    Long login(LoginDto loginDto);
    void addLoginLog(String ip, Long success, Long userId);
    Long getFailedLoginAttempt(String ip, Long minutes);
    UserInfoDto getUserInfo(Long userId);
    String getUserSalt(String username);
    Long getUserCount();
    void addAdmin(String username, String password, String salt);
}
