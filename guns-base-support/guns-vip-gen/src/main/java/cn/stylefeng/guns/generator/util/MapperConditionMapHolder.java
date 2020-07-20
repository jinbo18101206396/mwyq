package cn.stylefeng.guns.generator.util;

import java.util.Map;

/**
 * 临时变量存放
 *
 * @author jinbo
 * @date 2019-05-06-18:33
 */
public class MapperConditionMapHolder {

    private static ThreadLocal<Map<String, String[]>> threadLocal = new ThreadLocal<>();

    public static void put(Map<String, String[]> value) {
        threadLocal.set(value);
    }

    public static Map<String, String[]> get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
