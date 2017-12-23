package com.dj.work;

import com.dj.mapper.ManagerJobMapper;
import com.dj.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 解析管理者工作信息
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/23 0023
 */
@Component
public class ManagerJobParseWork {

    @Autowired
    private ManagerJobMapper managerJobMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @PostConstruct
    public void init() {
        try {
            ManagerJobParse managerJobParse = new ManagerJobParse(managerJobMapper, managerMapper);
            managerJobParse.startWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
