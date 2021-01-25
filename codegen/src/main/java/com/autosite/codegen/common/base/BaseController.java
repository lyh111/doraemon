package com.autosite.codegen.common.base;

import com.autosite.common.collect.MapUtils;
import com.autosite.common.lang.StringUtils;
import com.autosite.common.mapper.JsonMapper;
import com.autosite.common.mapper.XmlMapper;
import com.autosite.common.web.http.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BaseController {

    public static String renderResult(String result, String message, Object data) {
        Map<String, Object> resultMap = MapUtils.newHashMap();
        resultMap.put("result", result);
        resultMap.put("message", message);
        if (data != null) {
            if (data instanceof Map) {
                resultMap.putAll((Map<String, Object>) data);
            } else {
                resultMap.put("data", data);
            }
        }
        HttpServletRequest request = ServletUtils.getRequest();
        String uri = request.getRequestURI();
        if (StringUtils.endsWithIgnoreCase(uri, ".xml")) {
            return XmlMapper.toXml(resultMap);
        } else {
            String functionName = request.getParameter("__callback");
            if (StringUtils.isNotBlank(functionName)) {
                return JsonMapper.toJsonp(functionName, resultMap);
            } else {
                return JsonMapper.toJson(resultMap);
            }
        }

    }
}
