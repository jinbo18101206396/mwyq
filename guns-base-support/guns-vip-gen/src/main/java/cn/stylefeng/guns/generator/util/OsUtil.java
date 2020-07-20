package cn.stylefeng.guns.generator.util;

/**
 * 判断操作系统的工具
 *
 * @author jinbo
 * @date 2019-05-30-12:31
 */
public class OsUtil {

    /**
     * 判断是不是windows
     *
     * @author jinbo
     * @Date 2019-05-30 12:32
     */
    public static Boolean windowsFlag() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

}
