package com.lyujp.heziservice.entity;

import lombok.Data;

@Data
public class WorkBench {
    private Long id;
    private String title;
    private String subtitle;
    private String img;
    private String url;
    private Long categoryId;
    private Long status;
}
