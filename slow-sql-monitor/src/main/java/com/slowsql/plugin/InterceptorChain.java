package com.slowsql.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();

    public InterceptorChain() {
    }

    public void addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(this.interceptors);
    }
}
