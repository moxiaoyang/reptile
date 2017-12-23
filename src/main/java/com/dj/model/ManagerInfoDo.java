package com.dj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class ManagerInfoDo {
    /**
     * 姓名
     */
    private String managername;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国家
     */
    private String country;

    /**
     * 年龄
     */
    private String age;

    /**
     * 学历
     */
    private String edu;

    /**
     * 行业
     */
    private String industry;

    /**
     * 职称
     */
    private String title;

    /**
     * 专业技术资格
     */
    private String technical;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 页面链接
     */
    private String stocknamelink;
}