package com.dj.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器
 */
public class DateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String s) {
        //实现，将日期串转换成日期类型（格式：yyyy-MM-dd HH:mm:ss）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转成直接返回
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //失败返回null
        return null;
    }
}
