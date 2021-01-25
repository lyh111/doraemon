/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.autosite.common.utils.excel.fieldtype;

import java.text.SimpleDateFormat;


/**
 * 日期类型转换
 *
 * @author ThinkGem
 * @version 2015-9-29
 * @example fieldType = DateType.class
 */
public class DateType {

    /**
     * 获取对象值（导入）
     */
    public static Object getValue(String val) {
        // return val == null ? "" : val.replaceAll(",", "");
        return val == null ? "" : val;
    }

    /**
     * 获取对象值（导出）
     */
    public static String setValue(Object val) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long lt = new Long(val);
//        Date date = new Date(lt);
        return val == null ? "" : simpleDateFormat.format(val);
    }

    /**
     * 获取对象值格式（导出）
     */
    public static String getDateFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 清理缓存
     */
    public static void clearCache() {

    }

}
