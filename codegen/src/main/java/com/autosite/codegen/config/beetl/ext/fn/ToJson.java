package com.autosite.codegen.config.beetl.ext.fn;

import com.autosite.common.mapper.JsonMapper;
import org.beetl.core.Context;
import org.beetl.core.Function;

public class ToJson implements Function {
    public ToJson() {
    }

    public Object call(Object[] paras, Context ctx) {
        Object a;
        return (a = paras[0]) == null ? "" : JsonMapper.toJson(a);
    }
}
