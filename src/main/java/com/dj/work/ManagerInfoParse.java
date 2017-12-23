package com.dj.work;

import com.alibaba.fastjson.JSON;
import com.dj.mapper.ManagerInfoMapper;
import com.dj.mapper.ManagerMapper;
import com.dj.model.ManagerDo;
import com.dj.model.ManagerInfoDo;
import com.dj.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 开始解析高管信息
 * <p>
 * 1.
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/22 0022
 */
@Slf4j
public class ManagerInfoParse {


    private ManagerInfoMapper managerInfoMapper;

    private ManagerMapper managerMapper;

    public ManagerInfoParse(ManagerInfoMapper managerInfoMapper, ManagerMapper managerMapper) {
        this.managerInfoMapper = managerInfoMapper;
        this.managerMapper = managerMapper;
    }

    /**
     * 请求地址
     */
    private static final String PARSE_URL = "http://stockdata.stock.hexun.com/gszl/";

    private static final String MANAGER_INFO_TEMP = "{\"age\":\"${ageInfo}\",\"country\":\"${countryInfo}\",\"edu\":\"${eduInfo}\",\"industry\":\"${industryInfo}\",\"introduction\":\"${introduction}\",\"sex\":\"${sexInfo}\",\"technical\":\"${technicalInfo}\",\"title\":\"${titleInfo}\"}";


    /**
     * 开始工作
     */
    public void startWork() {
        log.info("call 开始解析数据信息···");
        if (Boolean.TRUE) {
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
            for (int i = 0; i <= 389; i++) {
                List<ManagerDo> data;
                int begin;
                int end;
                if (i % 100 == 0 && i > 0) {
                    begin = i - 100 + 1;
                    end = i;
                    data = managerMapper.selectAll(begin, end);
                    log.info("call begin = {},end = {}, size = {}", begin, end, data.size());
                    final List<ManagerDo> finalData = data;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            eachList(finalData);
                        }
                    });
                }
                if (i == 389) {
                    begin = 301;
                    end = 389;
                    data = managerMapper.selectAll(begin, end);
                    log.info("call begin = {},end = {}, size = {}", begin, end, data.size());
                    final List<ManagerDo> finalData = data;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            eachList(finalData);
                        }
                    });
                }
            }
        }
        log.info("call 解析数据信息结束···");
    }


    /**
     * 循环爬取数据
     *
     * @param data
     */
    public void eachList(List<ManagerDo> data) {
        for (ManagerDo managerDo : data) {
            ManagerInfoDo managerInfoDo = parseManagerInfoDo(managerDo.getStocknameLink());
            managerInfoDo.setStocknamelink(managerDo.getStocknameLink());
            inserIntoDB(managerInfoDo);
        }
    }


    private void inserIntoDB(ManagerInfoDo managerInfoDo) {
        managerInfoMapper.insert(managerInfoDo);
        log.info("call 插入数据库成功");
    }


    /**
     * 解析管理者个人信息
     */
    public static ManagerInfoDo parseManagerInfoDo(String mangerUrl) {
        log.info("call 开始解析地址：{}", mangerUrl);
        ManagerInfoDo managerInfoDo;
        try {
            //初始化一个httpclient
            HttpClient client = new DefaultHttpClient();
            //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
            String url = PARSE_URL.concat(mangerUrl);
            HttpResponse rawHtml = HttpUtils.getRawHtml(client, url);
            String entity = EntityUtils.toString(rawHtml.getEntity(), "utf-8");
            Document doc = Jsoup.parse(entity);
            Elements elementsByClass = doc.getElementsByClass("name_01");
            String managerName = elementsByClass.text();
            log.info("call 结果：{}", managerName);
            if (StringUtils.isEmpty(managerName)) {
                log.info("call 数据有误");
                return new ManagerInfoDo();
            }
            Elements gr_table = doc.getElementsByClass("gr_table");
            Elements trs = gr_table.select("tr");
            int flag = 0;
            String agentInfo = MANAGER_INFO_TEMP;
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    flag++;
                    String text = td.text().replace("\"", "").replace("\"", "");
                    log.info("call flag = {},text:{}", flag, text);
                    if (flag == 2) {
                        agentInfo = agentInfo.replace("${sexInfo}", text);
                    } else if (flag == 4) {
                        agentInfo = agentInfo.replace("${countryInfo}", text);
                    } else if (flag == 6) {
                        agentInfo = agentInfo.replace("${ageInfo}", text);
                    } else if (flag == 8) {
                        agentInfo = agentInfo.replace("${eduInfo}", text);
                    } else if (flag == 10) {
                        agentInfo = agentInfo.replace("${industryInfo}", text);
                    } else if (flag == 12) {
                        agentInfo = agentInfo.replace("${titleInfo}", text);
                    } else if (flag == 14) {
                        agentInfo = agentInfo.replace("${technicalInfo}", text);
                    } else if (flag == 18) {
                        agentInfo = agentInfo.replace("${introduction}", text);
                    }
                }
            }
            managerInfoDo = JSON.parseObject(agentInfo, ManagerInfoDo.class);
            managerInfoDo.setManagername(managerName);
            log.info("call 管理者信息：{}", managerInfoDo);
        } catch (Exception e) {
            log.error("call 解析发生异常，信息：{}", e);
            return new ManagerInfoDo();
        }
        return managerInfoDo;
    }


}
