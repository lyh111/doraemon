package com.autosite.codegen.config.mybatis.annotation;


import com.beust.jcommander.internal.Lists;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Condition {
    /**
     * 条件的关键字
     */
    String keyword() default "eq";
    /**
     * 数据表列
     */
    String field() default "";

    enum KeywordsEnum{
        Compare{
            public List<String> getKeyword() {
                return Lists.newArrayList("eq","ge","gt","le","lt","like","notLike","likeLeft","likeRight","between");
            }
        },
        Compare_Ext{
            public List<String> getKeyword() {
                return Lists.newArrayList("between");
            }
        },
        Func{
            public List<String> getKeyword() {
                return Lists.newArrayList("in","notIn","groupBy","orderByAsc","orderByDesc");
            }
        };

        public abstract List<String> getKeyword();
    }
}
