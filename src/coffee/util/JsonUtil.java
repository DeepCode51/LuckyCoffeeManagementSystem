package coffee.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author yu
 * @Description:
 * @date 2022/2/24 14:36
 */
public class JsonUtil {
    /**
     * 根据入参，转换成JSONObject对象
     *
     * @param status
     * @param code
     * @param data
     * @param total
     * @return
     */
    public static JSONObject toJsonObj(boolean status, int code, Object data, int total) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("code", code);
        jsonObject.put("data", data);
        jsonObject.put("total", total);
        return jsonObject;
    }

    /**
     * 成功返回的json格式数据
     *
     * @param data
     * @return
     */
    public static JSONObject toSuccessJsonResult(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("code", 1);
        jsonObject.put("data", data);
        return jsonObject;
    }

    /**
     * 成功返回的json格式数据,带总数
     *
     * @param data
     * @param total
     * @return
     */
    public static JSONObject toSuccessJsonResult(Object data, int total) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("code", 1);
        jsonObject.put("data", data);
        jsonObject.put("total", total);
        return jsonObject;
    }

    /**
     * 成功返回的json格式数据,带总数
     *
     * @param data
     * @param total
     * @return
     */
    public static JSONObject toSuccessLayUiJsonResult(Object data, int total) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("code", 0);
        jsonObject.put("data", data);
        jsonObject.put("count", total);
        return jsonObject;
    }

    /**
     * 失败返回的json格式数据
     *
     * @param code
     * @param message
     * @return
     */
    public static JSONObject toFailJsonResult(int code, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", false);
        jsonObject.put("code", code);
        jsonObject.put("data", message);
        return jsonObject;
    }
}
