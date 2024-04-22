package com.slowsql.config;

import com.slowsql.plugin.InterceptorChain;
import com.slowsql.plugin.LogInterceptor;

public class SlowSqlConfig {

    /**
     * 慢sql时长
     */
    private long slowMillis = 1000L;
    /**
     * 是否开启慢sql执行计划
     */
    private boolean enableExplain = true;
    /**
     * 慢sql拦截器拓展
     */
    private InterceptorChain interceptorChain = new InterceptorChain();

    public SlowSqlConfig() {
        // 默认添加日志拦截
        this.interceptorChain.addInterceptor(new LogInterceptor());
    }

    public long getSlowMillis() {
        return slowMillis;
    }

    public void setSlowMillis(long slowMillis) {
        this.slowMillis = slowMillis;
    }

    public boolean isEnableExplain() {
        return enableExplain;
    }

    public void setEnableExplain(boolean enableExplain) {
        this.enableExplain = enableExplain;
    }

    public InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }
}
