package com.dj.work;

import com.alibaba.fastjson.JSONObject;
import com.dj.mapper.DataMapper;
import com.dj.mapper.ManagerMapper;
import com.dj.model.DataDo;
import com.dj.model.ManagerDo;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class HtmlParseManagerTest extends BaseTest {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Test
    public void test_001() {
        ManagerDo managerDo = new ManagerDo();
        managerDo.setStocknameLink("sss");
        managerDo.setPanelrate("ssdsdsd");
        ManagerDo managerDo1 = new ManagerDo();
        managerDo1.setStocknameLink("sss");
        managerDo1.setPanelrate("ssdsdsd");
        HashSet<ManagerDo> objects = Sets.newHashSet();
        objects.add(managerDo);
        objects.add(managerDo1);
        System.out.println(objects);

    }


    @Test
    public void test11() {
        ManagerDo managerDo = new ManagerDo();
        int insert = managerMapper.insert(managerDo);
        System.out.println(insert);
        DataDo dataDo = new DataDo();
        int insert1 = dataMapper.insert(dataDo);
        System.out.println(insert1);
    }


    @Test
    public void test_02() {
        HtmlParseManager htmlParseManager = new HtmlParseManager(managerMapper);
        htmlParseManager.startWork();
    }


    @Test
    public void test_01() {
        String json = "[{panelrate:'邬红华',StockNameLink:'p000785-8801324602.shtml',industry:'副总经理',Number:'2016-08-25',Numdate:'--',Stockdisc:'博士研究生',latestprice:'7.04',Rcompany:'--',Association:'--'},{panelrate:'张万新',StockNameLink:'p000785-8801360983.shtml',industry:'副总经理',Number:'2011-06-27',Numdate:'--',Stockdisc:'本科',latestprice:'49.98',Rcompany:'--',Association:'--'},{panelrate:'王海',StockNameLink:'p000785-8801360985.shtml',industry:'副总经理',Number:'2011-06-27',Numdate:'--',Stockdisc:'硕士及研究生',latestprice:'47.82',Rcompany:'--',Association:'--'},{panelrate:'孔�]',StockNameLink:'p000785-8801401604.shtml',industry:'副总经理',Number:'2015-09-15',Numdate:'--',Stockdisc:'本科',latestprice:'26.97',Rcompany:'--',Association:'--'},{panelrate:'刘自力',StockNameLink:'p000785-8801490077.shtml',industry:'副总经理',Number:'2011-06-27',Numdate:'--',Stockdisc:'硕士及研究生',latestprice:'49.28',Rcompany:'--',Association:'--'},{panelrate:'王纯',StockNameLink:'p000785-8801491878.shtml',industry:'总经理',Number:'2015-09-15',Numdate:'--',Stockdisc:'硕士及研究生',latestprice:'26.37',Rcompany:'--',Association:'--'},{panelrate:'易国华',StockNameLink:'p000785-8801504827.shtml',industry:'副总经理',Number:'2006-05-26',Numdate:'--',Stockdisc:'本科',latestprice:'47.82',Rcompany:'--',Association:'--'},{panelrate:'易国华',StockNameLink:'p000785-8801504827.shtml',industry:'董秘',Number:'2006-05-26',Numdate:'--',Stockdisc:'本科',latestprice:'47.82',Rcompany:'--',Association:'--'}]";
        List<ManagerDo> list;
        list = JSONObject.parseArray(json, ManagerDo.class);
        System.out.println(list);
    }

    @Test
    public void parseManagerUrl() {
        List<ManagerDo> ManagerDos = HtmlParseManager.parseManagerUrl("000002", "003");
    }
}