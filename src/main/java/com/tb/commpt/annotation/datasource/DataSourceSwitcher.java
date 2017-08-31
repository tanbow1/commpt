package com.tb.commpt.annotation.datasource;

/**
 * Created by Tanbo on 2017/8/31.
 */
public class DataSourceSwitcher {
    @SuppressWarnings("rawtypes")
    private static final ThreadLocal contextHolder = new ThreadLocal();

    @SuppressWarnings("unchecked")
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static void setMaster() {
        clearDataSource();
    }

    public static void setSlave() {
        setDataSource("slave");
    }

    public static void setSecondaryMaster() {
        setDataSource("secondary.master");
    }

    public static void setSecondarySlave() {
        setDataSource("secondary.slave");
    }

    public static String getDataSource() {
        return (String) contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
