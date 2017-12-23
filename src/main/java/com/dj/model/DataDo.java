package com.dj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class DataDo {
    /**
     * ID
     */
    private Long id;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 年份
     */
    private String year;

    /**
     * 评级得分
     */
    private String pjdf;

    /**
     * 行业整体评级得分
     */
    private String hyztpjdf;

    /**
     * 全市场整体评级得分
     */
    private String qscztpjdf;

    /**
     * 行业内得分排名
     */
    private String hyndfpm;

    /**
     * 全市场得分排名
     */
    private String qscdfpm;

    /**
     * 净资产收益率
     */
    private String jzcsyl;

    /**
     * 总资产收益率
     */
    private String zzcsyl;

    /**
     * 主营业务利润率
     */
    private String zyywlll;

    /**
     * 每股收益
     */
    private String mgsy;

    /**
     * 每股未分配利润
     */
    private String mgwfpll;

    /**
     * 成本费用利润率
     */
    private String cbfylll;

    /**
     * 盈利合计
     */
    private String ylhj;

    /**
     * 速动比率
     */
    private String sdbl;

    /**
     * 流动比率
     */
    private String ldbl;

    /**
     * 现金比率
     */
    private String xjbl;

    /**
     * 股东权益比率
     */
    private String gdqybl;

    /**
     * 资产负债率
     */
    private String zcfzl;

    /**
     * 偿债合计
     */
    private String czhj;

    /**
     * 分红融资比
     */
    private String fhrzb;

    /**
     * 股息率
     */
    private String gxl;

    /**
     * 分红占可分配利润比
     */
    private String fhzkfpllb;

    /**
     * 回报合计
     */
    private String hbhj;

    /**
     * 交易所对公司和相关责任人处罚次数
     */
    private String jysdgshxgzrrcfcs;

    /**
     * 信批合计
     */
    private String xphj;

    /**
     * 产品开发支出
     */
    private String cpkfzc;

    /**
     * 技术创新理念
     */
    private String cxjsln;

    /**
     * 技术创新项目数
     */
    private String jscxxms;

    /**
     * 创新合计
     */
    private String cxhj;

    /**
     * 股东责任合计
     */
    private String gdzrhj;

    /**
     * 职工人均收入
     */
    private String zgrjsr;

    /**
     * 员工培训
     */
    private String ygpx;

    /**
     * 绩效合计
     */
    private String jxhj;

    /**
     * 安全检查
     */
    private String aqjc;

    /**
     * 安全培训
     */
    private String aqpx;

    /**
     * 安全合计
     */
    private String aqhj;

    /**
     * 慰问意识
     */
    private String wwys;

    /**
     * 慰问人
     */
    private String wwr;

    /**
     * 慰问金
     */
    private String wwj;

    /**
     * 关爱员工合计
     */
    private String gayghj;

    /**
     * 员工责任合计
     */
    private String ygzrhj;

    /**
     * 质量管理意识
     */
    private String zlglys;

    /**
     * 质量管理体系证书
     */
    private String zlgltxzs;

    /**
     * 产品质量合计
     */
    private String cpzlhj;

    /**
     * 客户满意度调查
     */
    private String khmyddc;

    /**
     * 供应商公平竞争
     */
    private String gysgpjz;

    /**
     * 反商业贿赂培训
     */
    private String fsyhlpx;

    /**
     * 诚信互惠合计
     */
    private String cxhhhj;

    /**
     * 供应商、客户和消费者权益责任合计
     */
    private String gyskhxfzhj;

    /**
     * 环保意识
     */
    private String hbys;

    /**
     * 环境管理体系认证
     */
    private String hjgltxrz;

    /**
     * 环保投入金额
     */
    private String hbtrje;

    /**
     * 排污种类数
     */
    private String pwzls;

    /**
     * 节约能源种类数
     */
    private String jynyzls;

    /**
     * 环境治理合计
     */
    private String hjzlhj;

    /**
     * 环境责任合计
     */
    private String hjzrhj;

    /**
     * 所得税占利润总额比
     */
    private String sdszllzeb;

    /**
     * 公益捐赠金额
     */
    private String gyjzje;

    /**
     * 贡献价值合计
     */
    private String gxjzhj;

    /**
     * 社会责任合计
     */
    private String shzrhj;
}