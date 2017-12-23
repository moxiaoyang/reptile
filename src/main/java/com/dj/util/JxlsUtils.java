package com.dj.util;

import com.google.common.collect.Maps;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * EXCEL 工具类
 *
 * @author 莫小阳
 */
public class JxlsUtils {

    /**
     * 导出excel
     *
     * @param is    模板文件流
     * @param os    输出文件流
     * @param model 内容
     * @throws IOException
     */
    public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException {
        Context context = new Context();
        if (model != null) {
            for (String key : model.keySet()) {
                context.putVar(key, model.get(key));
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = Maps.newHashMap();
        //添加自定义功能
        funcs.put("utils", new JxlsUtils());
        evaluator.getJexlEngine().setFunctions(funcs);
        jxlsHelper.processTemplate(context, transformer);
    }

    /**
     * 导出excel
     *
     * @param xls   模板文件
     * @param out   输出文件
     * @param model 内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void exportExcel(File xls, File out, Map<String, Object> model) throws FileNotFoundException, IOException {
        exportExcel(new FileInputStream(xls), new FileOutputStream(out), model);
    }

    /**
     * 导出excel
     *
     * @param templateName 模板路径
     * @param os           输出流
     * @param model        内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void exportExcel(String templateName, OutputStream os, Map<String, Object> model) throws FileNotFoundException, IOException {
        File template = getTemplate(templateName);
        if (template != null) {
            exportExcel(new FileInputStream(template), os, model);
        }
    }


    /**
     * 获取jxls模版文件
     *
     * @param templatePath 模板路径
     * @return 模板文件File
     */
    public static File getTemplate(String templatePath) {
        File template = new File(templatePath);
        if (template.exists()) {
            return template;
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param fmt
     * @return
     */
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
