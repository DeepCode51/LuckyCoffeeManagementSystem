package coffee.util;

/**
 * @author yu
 * @Description: 常用处理工具类
 * @date 2021/6/19 22:17
 */
public class StringUtil {

    /**
     * 校验空值，非空返回真
     *
     * @param value
     * @return
     */
    public static boolean checkNull(String value) {
        return value != null && !"".equals(value);
    }

    /**
     * 判断字符串为空 （含空格）
     *
     * @param s 需要判断的字符串
     * @return 字符串为空或者null返回true
     */
    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (isBlank(s));
    }

    /**
     * 判断字符串为空 （含空格）
     *
     * @param str 需要判断的字符串
     * @return 字符串为空或是空格返回true
     */
    public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return true;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 分页sql
     *
     * @param sql
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static String getPageSql(String sql, String pageIndex, String pageSize) {
        return "select * from( select rownum num, * from ( " + sql + " )) where num >=(" + pageIndex + "-1) * " + pageSize + " + 1 and num <= " + pageIndex + " * " + pageSize;
    }

    /**
     * mysql分页
     *
     * @param sql
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static String getMysqlPageSql(String sql, String pageIndex, String pageSize) {
        int start = Integer.parseInt(pageIndex) * Integer.parseInt(pageSize);
        int end = (Integer.parseInt(pageIndex) + 1) * Integer.parseInt(pageSize);
        return "SELECT * FROM ( " + sql + " ) AS A LIMIT " + start + ", " + pageSize;
    }

    /**
     * layui风格，mysql分页
     *
     * @param sql
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static String getLayUIMysqlPageSql(String sql, String pageIndex, String pageSize) {
        int start = (Integer.parseInt(pageIndex) - 1) * Integer.parseInt(pageSize);
        int end = (Integer.parseInt(pageIndex)) * Integer.parseInt(pageSize);
        return "SELECT * FROM ( " + sql + " ) AS A LIMIT " + start + ", " + pageSize;
    }

    public static String charConvert(String strResource) {
        if (strResource != null) {
            try {
                String str = strResource;
                byte[] tempString = str.getBytes("iso-8859-1");
                str = new String(tempString, "utf-8");
                return str;
            } catch (Exception e) {
                System.out.println(e);
                return "";
            }
        } else {
            return "";
        }
    }
}
