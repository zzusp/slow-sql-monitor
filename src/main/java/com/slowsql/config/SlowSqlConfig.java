package com.slowsql.config;

import com.slowsql.plugin.Interceptor;

import java.util.ArrayList;
import java.util.List;

public class SlowSqlConfig {

    /** 慢sql时长 */
    private long slowMillis = 1000L;
    /** 慢sql拦截器拓展 */
    private List<Interceptor> interceptors = new ArrayList<>();

    public long getSlowMillis() {
        return slowMillis;
    }

    public void setSlowMillis(long slowMillis) {
        this.slowMillis = slowMillis;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void addInterceptors(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}
