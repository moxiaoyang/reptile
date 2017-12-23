package com.dj.work;

import com.dj.mapper.ManagerInfoMapper;
import com.dj.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/22 0022
 */
@Component
public class ManagerInfoParseWork {

    @Autowired
    private ManagerInfoMapper managerInfoMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @PostConstruct
    public void init() {
        try {
            ManagerInfoParse htmlParse = new ManagerInfoParse(managerInfoMapper, managerMapper);
            htmlParse.startWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
