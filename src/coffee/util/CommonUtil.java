package coffee.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yu
 * @Description:
 * @date 2021/6/20 10:27
 */
public class CommonUtil {
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        //当前时间
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(day);
    }

    public static String getCurrentDay() {
        //当前时间
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(day);
    }
}
