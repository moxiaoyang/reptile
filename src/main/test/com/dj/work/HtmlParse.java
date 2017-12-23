package com.dj.work;

import com.dj.mapper.DataMapper;
import com.dj.mapper.ManagerInfoMapper;
import com.dj.model.DataDo;
import com.dj.model.ManagerInfoAll;
import com.dj.util.HttpUtils;
import com.dj.util.JxlsUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试类
 */
public class HtmlParse extends BaseTest {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private ManagerInfoMapper managerInfoMapper;

    /**
     * 导出 EXCEL
     */
    @Test
    public void test_05() throws IOException {
        List<DataDo> allData = dataMapper.getAllData();
        Map<String, Object> exportData = Maps.newHashMap();
        exportData.put("list", allData);
        JxlsUtils.exportExcel(new File("C:\\Users\\Administrator\\Desktop\\data.xlsx"), new File("C:\\Users\\Administrator\\Desktop\\mydata.xlsx"), exportData);
        System.out.println("导出成功~");
    }


    /**
     * 导出 EXCEL
     */
    @Test
    public void test_06() throws IOException {
        List<ManagerInfoAll> managerInfoAlls = managerInfoMapper.queryAll();
        Map<String, Object> exportData = Maps.newHashMap();
        exportData.put("list", managerInfoAlls);
        JxlsUtils.exportExcel(new
                        File("C:\\Users\\莫小阳\\Desktop\\data.xlsx"),
                new File("C:\\Users\\莫小阳\\Desktop\\mydata.xlsx"), exportData);
        System.out.println("导出成功~");
    }


    @Test
    public void test_04() throws IOException {

        List<String> dataList = IOUtils.readLines(new FileReader("C:\\Users\\Administrator\\Desktop\\全球"));
        HashSet<String> dataSet = Sets.newHashSet();
        dataSet.addAll(dataList);
        dataList.clear();
        dataList.addAll(dataSet);
        IOUtils.writeLines(dataList, null, new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\code.txt")));

        System.out.println(dataList.size());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i <= 3140; i++) {
            List<String> data;
            int begin;
            int end;
            if (i % 500 == 0 && i > 0) {
                begin = i - 500;
                end = i;
                data = dataList.subList(begin, end);
                final List<String> finalData = data;
                fixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("111111111111111111111");
                        test_03(finalData);
                    }
                });
            }
            if (i == 3140) {
                begin = 3000;
                end = 3140;
                data = dataList.subList(begin, end);
                final List<String> finalData1 = data;
                fixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        test_03(finalData1);
                    }
                });
            }

        }


    }

    public void test_03(List<String> list) {
        List<String> years = Arrays.asList("2010", "2012", "2013", "2014", "2015", "2016");
        for (String d : list) {
            for (String s : years) {
                test_02(s, d);
            }
        }
    }


    public void test_02(String year, String code) {


        try {
            //初始化一个httpclient
            HttpClient client = new DefaultHttpClient();
            //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
            String url = "http://stockdata.stock.hexun.com/zrbg/stock_bg.aspx?date=".concat(year).concat("-12-31&code=").concat(code);
            HttpResponse rawHtml = HttpUtils.getRawHtml(client, url);
            String entity = EntityUtils.toString(rawHtml.getEntity(), "utf-8");
            Document doc = Jsoup.parse(entity);
            Elements elements = doc.getElementsByTag("li");
            DataDo allData = new DataDo();
            allData.setCode(code);
            allData.setYear(year);
            for (Element e : elements) {
                String text = e.text();
                if (text.startsWith("评级得分")) {
                    allData.setPjdf(text.substring(text.indexOf("：") + 1));
                } else if (text.startsWith("行业整体评级得分")) {
                    allData.setHyztpjdf(text.substring(text.indexOf("：") + 1));
                } else if (text.startsWith("全市场整体评级得分")) {
                    allData.setQscztpjdf(text.substring(text.indexOf("：") + 1));
                } else if (text.startsWith("行业内得分排名")) {
                    allData.setHyndfpm(text.substring(text.indexOf("：") + 1));
                } else if (text.startsWith("全市场得分排名")) {
                    allData.setQscdfpm(text.substring(text.indexOf("：") + 1));
                } else if (text.startsWith("净资产收益率")) {
                    allData.setJzcsyl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("总资产收益率")) {
                    allData.setZzcsyl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("主营业务利润率")) {
                    allData.setZyywlll(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("每股收益")) {
                    allData.setMgsy(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("每股未分配利润")) {
                    allData.setMgwfpll(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("成本费用利润率")) {
                    allData.setCbfylll(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("速动比率")) {
                    allData.setSdbl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("流动比率")) {
                    allData.setLdbl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("现金比率")) {
                    allData.setXjbl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("股东权益比率")) {
                    allData.setGdqybl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("资产负债率")) {
                    allData.setZcfzl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("分红融资比")) {
                    allData.setFhrzb(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("股息率")) {
                    allData.setGxl(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("分红占可分配利润比")) {
                    allData.setFhzkfpllb(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("交易所对公司和相关责任人处罚次数")) {
                    allData.setJysdgshxgzrrcfcs(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("产品开发支出")) {
                    allData.setCpkfzc(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("技术创新理念")) {
                    allData.setCxjsln(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("技术创新项目数")) {
                    allData.setJscxxms(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("职工人均收入")) {
                    allData.setZgrjsr(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("员工培训")) {
                    allData.setYgpx(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("安全检查")) {
                    allData.setAqjc(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("安全培训")) {
                    allData.setAqpx(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("慰问意识")) {
                    allData.setWwys(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("慰问人")) {
                    allData.setWwr(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("慰问金")) {
                    allData.setWwj(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("质量管理意识")) {
                    allData.setZlglys(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("质量管理体系证书")) {
                    allData.setZlgltxzs(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("客户满意度调查")) {
                    allData.setKhmyddc(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("供应商公平竞争")) {
                    allData.setGysgpjz(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("反商业贿赂培训")) {
                    allData.setFsyhlpx(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("环保意识")) {
                    allData.setHbys(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("环境管理体系认证")) {
                    allData.setHjgltxrz(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("环保投入金额")) {
                    allData.setHbtrje(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("排污种类数")) {
                    allData.setPwzls(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("节约能源种类数")) {
                    allData.setJynyzls(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("所得税占利润总额比")) {
                    allData.setSdszllzeb(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                } else if (text.startsWith("公益捐赠金额")) {
                    allData.setGyjzje(text.substring(text.indexOf("(") + 1, text.indexOf(")") - 1));
                }
            }
            Elements elementsByClass = doc.select(".as_title");
            for (Element e : elementsByClass) {
                String t = e.text();
                if (t.startsWith("股东责任")) {
                    allData.setGdzrhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("员工责任")) {
                    allData.setYgzrhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("供应商、客户和消费者权益责任")) {
                    allData.setGyskhxfzhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("环境责任")) {
                    allData.setHjzrhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("社会责任")) {
                    allData.setShzrhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                }
            }


            Elements elementsByClass1 = doc.select(".c666666");
            for (Element e : elementsByClass1) {
                String t = e.text();
                if (t.startsWith("盈利")) {
                    allData.setYlhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("偿债")) {
                    allData.setCzhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("回报")) {
                    allData.setHbhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("信批")) {
                    allData.setXphj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("创新")) {
                    allData.setCxhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("绩效")) {
                    allData.setJxhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("安全")) {
                    allData.setAqhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("关爱员工")) {
                    allData.setGayghj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("产品质量")) {
                    allData.setCpzlhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("诚信互惠")) {
                    allData.setCxhhhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("环境治理")) {
                    allData.setHjzlhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                } else if (t.startsWith("贡献价值")) {
                    allData.setGxjzhj(t.substring(t.indexOf("(") + 1, t.indexOf(")") - 1));
                }
            }
            dataMapper.insert(allData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("成功");

    }


}
