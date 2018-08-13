package com.dj.dto;

import java.util.Date;

/**
 * 数据传输层
 * 暴露秒杀的URL
 */
public class Exposer {

    private boolean exposed;//秒杀是否开启

    private  String md5;//加密

    private Integer sId;

    private long now;//系统时间

    private long start;//秒杀开启时间

    private long end;//秒杀结束时间

    public Exposer(boolean exposed, String md5, Integer sId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.sId = sId;
    }

    public Exposer(boolean exposed, Integer sId, long now, long start, long end) {
        this.exposed = exposed;
        this.sId = sId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, Integer sId) {
        this.exposed = exposed;
        this.sId = sId;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", sId=" + sId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
