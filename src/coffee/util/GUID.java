package coffee.util;

import java.util.UUID;

/**
 * @author yu
 * @Description:
 * @date 2021/6/19 21:10
 */
public class GUID {
    /**
     * 生成主键
     *
     * @return
     */
    public static String newGUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
