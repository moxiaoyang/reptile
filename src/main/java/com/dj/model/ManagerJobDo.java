package com.dj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class ManagerJobDo {
    /**
     * ID
     */
    private Integer id;

    /**
     * 链接
     */
    private String stocknamelink;

    /**
     * 职务
     */
    private String job;

    /**
     * 任职日期
     */
    private String begintime;

    /**
     * 离职日期
     */
    private String endtime;
}