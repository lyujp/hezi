package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserInfoEntity {
    private Long id;
    private String username;
    private String nickname;
    private String password_sha512;
    private String avatar;
    private OffsetDateTime createAt;
    private Long status;
}
