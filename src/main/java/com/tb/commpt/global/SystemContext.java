package com.tb.commpt.global;


import com.tb.commpt.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SystemContext {
    private static final Logger logger = LoggerFactory.getLogger(SystemContext.class);

    private static boolean initialized = false;

    private static Map contextPool = null;

    private static SystemContext systemContext = null;

    private static final String BOOTSTRAP = "config/config.properties,config/datasource.properties";


    private SystemContext() {

    }

    public static void reload() {
        systemContext = new SystemContext();
    }


    public void init() throws SQLException {
        if (initialized) {
            return;
        }
        contextPool = new ConcurrentHashMap();
        loadProperties();
        setInitialized(true);
        logger.info("完成缓存中所有的参数数据初始化.");
    }

    private void loadProperties() {
        List<Properties> list = getProperties();
        for (int i = 0; i < list.size(); i++) {
            Properties props = list.get(i);
            Iterator iter = props.keySet().iterator();
            while ((iter != null) && (iter.hasNext())) {
                String key = (String) iter.next();
                String value = props.getProperty(key);
                synchronized (contextPool) {
                    contextPool.put(key, value);
                    logger.info("初始化系统属性: " + key + " = " + value);
                }
            }
        }

    }

    private List<Properties> getProperties() {
        List<Properties> list = new ArrayList<>();
        Properties props = new Properties();
        InputStream is = null;
        try {
            for (int i = 0; i < BOOTSTRAP.split(",").length; i++) {
                URL url = FileUtil.getFileURL(BOOTSTRAP.split(",")[i]);
                is = url.openStream();
                props.load(is);
            }
            list.add(props);
        } catch (Exception e) {
            logger.error("读取系统属性配置文件时发生错误:", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception localException1) {
            }
        }
        return list;
    }

    public static SystemContext singleton() {
        if (systemContext == null) {
            systemContext = new SystemContext();
        }
        return systemContext;
    }

    private boolean containsKey(Object key) {
        return contextPool.containsKey(key);
    }

    private boolean containsValue(Object value) {
        return contextPool.containsValue(value);
    }

    public void clear() {
        contextPool.clear();
        initialized = false;
        logger.info("清除参数缓存中所有的参数数据");
    }


    public Object getValue(String key) {
        return contextPool.get(key);
    }

    public String getValueAsString(String key) {
        return (String) getValue(key);
    }

    public Map getValWith(String key) {
        Map vals = new HashMap();
        Set keys = contextPool.keySet();
        Iterator itKeys = keys.iterator();
        while (itKeys.hasNext()) {
            Object objKey = itKeys.next();
            if ((objKey instanceof String)) {
                String strKey = (String) objKey;
                if (strKey.indexOf(key) != -1) {
                    vals.put(strKey, contextPool.get(strKey));
                }
            }
        }
        return vals;
    }


    private void setInitialized(boolean flag) {
        initialized = flag;
    }


    public void reLoadConfig() throws SQLException, IOException {
        clear();
        init();
    }

    public String toString() {
        return

                "{  initialized = " + initialized + ", contextPool = [" + contextPool + "] }";
    }

    public Object getValue(String pkID, String skID) {
        Object obj = contextPool.get(pkID);
        if (null == obj) {
            logger.error(
                    "找不到配置项：" + pkID + "　，请检查配置文件：" + BOOTSTRAP);
            return null;
        }
        if (!(obj instanceof IConfigContext)) {
            logger.error(
                    "配置项：" + pkID + "　没有加载或加载错误，请检查配置文件：" + BOOTSTRAP);
            return null;
        }
        IConfigContext configContext = (IConfigContext) obj;
        return configContext.getValue(skID);
    }

    public String getValueAsString(String pkID, String skID) {
        return (String) getValue(pkID, skID);
    }


}
