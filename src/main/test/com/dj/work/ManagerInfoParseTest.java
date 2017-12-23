package com.dj.work;

import com.alibaba.fastjson.JSON;
import com.dj.mapper.ManagerInfoMapper;
import com.dj.mapper.ManagerMapper;
import com.dj.model.ManagerDo;
import com.dj.model.ManagerInfoDo;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerInfoParseTest extends BaseTest {

    @Autowired
    private ManagerInfoMapper managerInfoMapper;

    @Autowired
    private ManagerMapper managerMapper;


    @Test
    public void test_012() {
        ManagerInfoParse managerInfoParse = new ManagerInfoParse(managerInfoMapper, managerMapper);
        List<ManagerDo> list = managerMapper.selectAllByT();
        managerInfoParse.eachList(list);
        System.out.println("结束");
    }


    @Test
    public void test_02() {
        ManagerInfoParse managerInfoParse = new ManagerInfoParse(managerInfoMapper, managerMapper);
        managerInfoParse.startWork();
    }

    @Test
    public void test_002() {
        List<String> objects = Lists.newArrayList();
        objects.add("1");
        objects.add("2");
        List<String> list = objects.subList(1, 2);
        System.out.println(list);
    }

    @Test
    public void test_01() {
        ManagerInfoDo ManagerInfoDo = new ManagerInfoDo();
        ManagerInfoDo.setSex("男");
        ManagerInfoDo.setCountry("中国");
        ManagerInfoDo.setAge("112");
        ManagerInfoDo.setEdu("本科");
        ManagerInfoDo.setIndustry("互联网");
        ManagerInfoDo.setTitle("高级");
        ManagerInfoDo.setTechnical("一级");
        ManagerInfoDo.setIntroduction("简 介");
        String s = JSON.toJSONString(ManagerInfoDo);
        System.out.println(s);
    }


    @Test
    public void parseManagerInfoDo() throws IOException {
        ManagerInfoParse.parseManagerInfoDo("p603358-8801447989.shtml");
    }
}