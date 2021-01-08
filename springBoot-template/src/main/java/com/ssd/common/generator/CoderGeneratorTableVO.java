package com.ssd.common.generator;

import lombok.Data;

/**
 * TODO
 *
 * @author lus
 * @Date 2021/1/8 0008 14
 */
@Data
public class CoderGeneratorTableVO {
    private String tableName;
    private String entityPrefix;

    public CoderGeneratorTableVO(String tableName, String entityPrefix) {
        this.tableName = tableName;
        this.entityPrefix = entityPrefix;
    }
}
