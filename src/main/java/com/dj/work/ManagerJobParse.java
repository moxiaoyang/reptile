package com.dj.work;

import com.dj.mapper.ManagerJobMapper;
import com.dj.mapper.ManagerMapper;
import com.dj.model.ManagerDo;
import com.dj.model.ManagerJobDo;
import com.dj.util.HttpUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取职位信息
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/23 0023
 */

@Slf4j
public class ManagerJobParse {

    /**
     * 请求地址
     */
    private static final String PARSE_URL = "http://stockdata.stock.hexun.com/gszl/";


    private ManagerJobMapper managerJobMapper;


    private ManagerMapper managerMapper;


    public ManagerJobParse(ManagerJobMapper managerJobMapper, ManagerMapper managerMapper) {
        this.managerJobMapper = managerJobMapper;
        this.managerMapper = managerMapper;
    }

    /**
     * 开始工作
     */
    public void startWork() {
        log.info("call 开始解析数据信息···");

        if (Boolean.TRUE) {
            List<ManagerDo> data = managerMapper.selectAllByT();
            foreachData(data);
        }

        if (Boolean.FALSE) {
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
            for (int i = 0; i <= 18770; i++) {
                List<ManagerDo> data;
                int begin;
                int end;
                if (i % 500 == 0 && i > 0) {
                    begin = i - 500 + 1;
                    end = i;
                    data = managerMapper.selectAll(begin, end);
                    log.info("call begin = {},end = {}, size = {}", begin, end, data.size());
                    final List<ManagerDo> finalData = data;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            foreachData(finalData);
                        }
                    });
                }
                if (i == 18770) {
                    begin = 18501;
                    end = 18770;
                    data = managerMapper.selectAll(begin, end);
                    log.info("call begin = {},end = {}, size = {}", begin, end, data.size());
                    final List<ManagerDo> finalData = data;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            foreachData(finalData);
                        }
                    });
                }
            }
        }
        log.info("call 解析数据信息结束···");
    }

    public void foreachData(List<ManagerDo> list) {
        for (ManagerDo managerDo : list) {
            inserDB(managerDo.getStocknameLink());
        }
    }

    public void inserDB(String url) {
        List<ManagerJobDo> list = parseManagerJobInfo(url);
        if (list != null && list.size() > 0) {
            managerJobMapper.insertList(list);
            log.info("call 入库成功，数据量：{}", list.size());
        }
    }

    public static List<ManagerJobDo> parseManagerJobInfo(String url) {

        List<ManagerJobDo> list;
        try {
            //初始化一个httpclient
            HttpClient client = new DefaultHttpClient();
            //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
            HttpResponse rawHtml = HttpUtils.getRawHtml(client, PARSE_URL.concat(url));
            String entity = EntityUtils.toString(rawHtml.getEntity(), "utf-8");
            Document doc = Jsoup.parse(entity);
            Elements table = doc.getElementsByClass("datetab");
            Elements trs = table.select("tr");
            list = Lists.newArrayList();
            for (Element tr : trs) {
                ManagerJobDo managerJobDo = new ManagerJobDo();
                Elements tds = tr.select("td");
                int i = 0;
                for (Element td : tds) {
                    String text = td.text();
                    log.info("i = {},text = {}", i++, text);
                    if (i == 2) {
                        managerJobDo.setJob(text);
                    } else if (i == 3) {
                        managerJobDo.setBegintime(text);
                    } else if (i == 4) {
                        managerJobDo.setEndtime(text);
                    }
                }
                if (i == 5) {
                    managerJobDo.setStocknamelink(url);
                    list.add(managerJobDo);
                }
            }
        } catch (Exception e) {
            log.error("call 解析发生异常，信息：{}", e);
            return null;
        }
        return list;
    }


}
