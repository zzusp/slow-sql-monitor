package com.slowsql.config;

import com.slowsql.plugin.Interceptor;
import com.slowsql.plugin.InterceptorChain;
import com.slowsql.plugin.LogInterceptor;

public class SlowSqlConfig {

    /**
     * 慢sql时长
     */
    private long slowMillis = 1000L;
    /**
     * 是否开启慢sql日志打印
     */
    private boolean logSlowSql = true;
    /**
     * 慢sql拦截器拓展
     */
    private InterceptorChain interceptorChain = new InterceptorChain();

    public SlowSqlConfig() {
    }

    public long getSlowMillis() {
        return slowMillis;
    }

    public void setSlowMillis(long slowMillis) {
        this.slowMillis = slowMillis;
    }

    public boolean isLogSlowSql() {
        return logSlowSql;
    }

    public void setLogSlowSql(boolean logSlowSql) {
        this.logSlowSql = logSlowSql;
        if (this.logSlowSql) {
            this.addInterceptor(new LogInterceptor());
        }
    }

    public InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

    public void addInterceptor(Interceptor interceptor) {
        this.interceptorChain.addInterceptor(interceptor);
    }
}
