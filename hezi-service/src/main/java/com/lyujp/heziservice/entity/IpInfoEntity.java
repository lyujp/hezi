package com.lyujp.heziservice.entity;

import lombok.Data;

@Data
public class IpInfoEntity {
    private Long id;
    private String ip;
    private Long ipType;
    private String ipHex;
}
