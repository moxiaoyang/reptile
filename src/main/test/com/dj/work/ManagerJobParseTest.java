package com.dj.work;

import com.dj.mapper.ManagerJobMapper;
import com.dj.mapper.ManagerMapper;
import com.dj.model.ManagerJobDo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ManagerJobParseTest extends BaseTest {

    @Autowired
    private ManagerJobMapper managerJobMapper;

    @Autowired
    private ManagerMapper managerMapper;


    @Test
    public void test_001() {
        ManagerJobParse managerJobParse = new ManagerJobParse(managerJobMapper, managerMapper);
        managerJobParse.startWork();
    }


    @Test
    public void parseManagerJobInfo() {
        ManagerJobDo managerJobDo = new ManagerJobDo();
     /*   managerJobDo.setStocknamelink("ddd");
        managerJobDo.setEndtime("dfd");
        managerJobDo.setEndtime("dfsdfsd");
        managerJobDo.setJob("fdsf");*/
        List<ManagerJobDo> objects = Lists.newArrayList();
        managerJobMapper.insertList(objects);
    }

    @Test
    public void test_01() {
        ManagerJobParse.parseManagerJobInfo("p000006-8801479023.shtml");
    }


}