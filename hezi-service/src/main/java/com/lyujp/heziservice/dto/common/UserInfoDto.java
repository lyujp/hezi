package com.lyujp.heziservice.dto.common;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private OffsetDateTime createAt;
    private Long status;
    private String salt;
}
