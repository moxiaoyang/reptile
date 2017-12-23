package com.dj.work;

import com.alibaba.fastjson.JSONObject;
import com.dj.mapper.ManagerMapper;
import com.dj.model.ManagerDo;
import com.dj.util.HttpUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.naming.ServiceUnavailableException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 页面解析
 * <p>
 * 1.  这个没有Work，直接运行单元测试
 * </p>
 *
 * @author 莫小阳
 * @version 1.0.0
 * @date 2017/12/21 0021
 */
@Slf4j
public class HtmlParseManager {


    /**
     * 数据操作层
     */
    private ManagerMapper managerMapper;

    public HtmlParseManager(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    /**
     * 请求地址
     */
    private static final String REQUER_URL = "http://stockdata.stock.hexun.com/gszl/data/jsondata/ggml.ashx";


    public void startWork() {
        try {
            log.info("开始抓取数据");
            List<String> dataList = IOUtils.readLines(new FileReader("E:\\CodeTest\\Java\\reptile-data\\codes.txt"));
            inserIntoDB(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("结束抓取数据");
    }


    /**
     * 插入数据库
     *
     * @param codes 代号
     */
    public void inserIntoDB(List<String> codes) {
        List<ManagerDo> allManagerDo;
        for (String s : codes) {
            allManagerDo = getAllManagerDo(s);
            // 去重
            HashSet<ManagerDo> dataSet = Sets.newHashSet();
            dataSet.addAll(allManagerDo);
            allManagerDo.clear();
            allManagerDo.addAll(dataSet);
            if (allManagerDo != null) {
                managerMapper.insertList(allManagerDo);
            }
            log.info("call 插入数据库成功：codes = {}", codes);
        }
    }


    public static List<ManagerDo> getAllManagerDo(String code) {
        List<String> types = Arrays.asList("001", "002", "003");
        List<ManagerDo> result = Lists.newArrayList();
        for (String type : types) {
            List<ManagerDo> managers = parseManagerUrl(code, type);
            result.addAll(managers);
        }
        return result;
    }


    /**
     * 解析高管信息链接地址
     *
     * @param no   编号
     * @param type 类型
     */
    public static List<ManagerDo> parseManagerUrl(String no, String type) {
        List<ManagerDo> list = null;
        try {
            HttpClient client = new DefaultHttpClient();
            String url = REQUER_URL.concat("?no=").concat(no).concat("&type").concat(type);
            HttpResponse rawHtml = HttpUtils.getRawHtml(client, url);
            String entity = EntityUtils.toString(rawHtml.getEntity(), "GBK");
            log.info("call 请求链接{}，返回数据：{}", url, entity);
            if (StringUtils.isEmpty(entity) || !entity.contains("[") || !entity.contains("]")) {
                throw new ServiceUnavailableException("返回数据不合法");
            }
            String json = entity.substring(entity.indexOf("["), entity.lastIndexOf("]") + 1);
            log.info("call 处理完数据，JSON = {}", json);
            list = JSONObject.parseArray(json, ManagerDo.class);
            log.info("call 获取总信息记录数：{}", list.size());
        } catch (Exception e) {
            log.error("call 获取数据失败，失败信息：{}", e);
        }
        return list;
    }


}
