package com.dj.work;

import com.dj.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/11 0011
 */
public class DoWork {

    @Autowired
    private DataMapper dataMapper;

    public void init() {
        try {
            HtmlParse htmlParse = new HtmlParse(dataMapper);
            System.out.println("开始抓取数据" + htmlParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
