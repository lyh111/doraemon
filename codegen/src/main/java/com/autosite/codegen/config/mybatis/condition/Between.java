package com.autosite.codegen.config.mybatis.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Between<T> {
    private T bLeft;
    private T bRight;
}
