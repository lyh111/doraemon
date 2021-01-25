package com.autosite.codegen.config.beetl.ext.fn;

import com.autosite.common.lang.ObjectUtils;
import com.autosite.common.lang.StringUtils;
import com.autosite.common.mapper.JsonMapper;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Map;

public class FromJson implements Function {
    public FromJson() {
    }

    public Object call(Object[] paras, Context ctx) {
        String a;
        if (StringUtils.isBlank(a = ObjectUtils.toString(paras[0]))) {
            return null;
        } else {
            String _a;
            String var10000 = _a = ObjectUtils.toString(paras.length < 2 ? "" : paras[1]);
            String[] var10001 = new String[2];
            boolean var10003 = true;
            var10001[0] = "";
            var10001[1] = "list";
            if (StringUtils.inString(var10000, var10001)) {
                return JsonMapper.fromJsonForMapList(a);
            } else if (StringUtils.equals(_a, "map")) {
                return JsonMapper.fromJson(a, Map.class);
            } else {
                try {
                    Class _class = Class.forName(_a);
                    return JsonMapper.fromJson(a, _class);
                } catch (ClassNotFoundException var6) {
                    return null;
                }
            }
        }
    }
}
