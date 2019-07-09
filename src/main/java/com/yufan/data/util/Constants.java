package com.yufan.data.util;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/16 14:26
 * 功能介绍:
 */
public enum Constants {

    tb_visit_business("tb_visit_business", "insert into tb_h5_channel(visit_date, business_code, visit_count, response_time, sys_code) values ");

    private String tableName;
    private String tabSql;

    Constants(String tableName, String tabSql) {
        this.tableName = tableName;
        this.tabSql = tabSql;
    }

    public static String getTabSql(String tableName) {
        for (Constants c : Constants.values()) {
            if (c.tableName.equals(tableName)) {
                return c.tabSql;
            }
        }
        return null;
    }

}
