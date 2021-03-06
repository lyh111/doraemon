package com.autosite.codegen.common.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class GenConfig {

    /**
     * 项目根路径
     */
    private String projectPath;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 作者
     */
    private String author;

    /**
     * 数据源 - 数据源路径
     */
    private String dsUrl;
    /**
     * 数据源 - 驱动名
     */
    private String dsDriverName;
    /**
     * 数据源 - 连接用户名
     */
    private String dsUname;
    /**
     * 数据源 - 连接密码
     */
    private String dsPass;

    /**
     * 包 - 所属包
     */
    private String pkParent;
    /**
     * 包 - 所属模块
     */
    private String pkModuleName;

    /**
     * 表名
     */
    private String tbName;

    /**
     * 是否去除表前缀
     */
    private Boolean isExPrefix;

    /**
     * 其他属性值
     */
    private Map<String,Map<String,Object>> fields;

    public boolean getIsExPrefix() {
        if (isExPrefix == null) {
            return false;
        }
        return isExPrefix;
    }
}
