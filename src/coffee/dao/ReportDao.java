package coffee.dao;

import coffee.util.BaseDAO;
import coffee.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yu
 * @Description:
 * @date 2022/3/6 12:05
 */
public class ReportDao {
    /**
     * 按销售商品产地统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, String> getByArea(String startTime, String endTime) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "SELECT a.PLACE,SUM(NUM)SL FROM (SELECT item.PLACE,cart.NUM FROM east_cart AS cart, east_order AS orders, east_item AS item "
                + "WHERE cart.order_id = orders.id_ AND item.ID_ = cart.ITEM_ID  AND orders.state in ('待发货','运输中','已完成') ";
        if (!StringUtil.isNullOrEmpty(startTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= '" + startTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= concat(year(now()),'-01-01')";
        }
        if (!StringUtil.isNullOrEmpty(endTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= '" + endTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= concat(year(now()),'-12-31')";
        }
        sql += ") AS a GROUP BY a.PLACE";

        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                map.put(rs.getString("PLACE") == null ? "" : rs.getString("PLACE"), rs.getString("SL") == null ? "" : rs.getString("SL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return map;
    }

    /**
     * 按销售商品包装类型统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, String> getByPacket(String startTime, String endTime) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "SELECT a.PACKET ,count(*) SL FROM (SELECT item.PACKET  FROM east_cart AS cart, east_order AS orders, east_item AS item "
                + "WHERE cart.order_id = orders.id_ AND item.ID_ = cart.ITEM_ID  AND orders.state in ('待发货','运输中','已完成') ";
        if (!StringUtil.isNullOrEmpty(startTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= '" + startTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= concat(year(now()),'-01-01')";
        }
        if (!StringUtil.isNullOrEmpty(endTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= '" + endTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= concat(year(now()),'-12-31')";
        }
        sql += ") AS a GROUP BY a.PACKET";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                map.put(rs.getString("PACKET") == null ? "" : rs.getString("PACKET"), rs.getString("SL") == null ? "" : rs.getString("SL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return map;
    }

    /**
     * 按购买用户年龄统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, String> getByAge(String startTime, String endTime) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "SELECT A.AGE_TYPE,B.SL FROM(" +
                "SELECT ID_ USER_ID," +
                "CASE WHEN AGE >= 18 AND AGE <= 22 THEN '18~22' WHEN AGE >= 23 AND AGE <= 35 THEN '23~35' WHEN AGE >= 36 AND AGE <= 50 THEN '36~50' " +
                "WHEN AGE >= 50 AND AGE <= 60 THEN '50~60' WHEN AGE > 60 THEN '60以上' end AGE_TYPE,AGE,SEX FROM east_user) AS A , " +
                "(SELECT A.USER_ID,COUNT(*)SL FROM(SELECT cart.USER_ID FROM east_cart AS cart, east_order AS orders,east_item AS item " +
                "WHERE cart.ORDER_ID = orders.ID_ and item.ID_ = cart.ITEM_ID " +
                "AND orders.STATE in ('待发货','运输中','已完成') ";
        if (!StringUtil.isNullOrEmpty(startTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= '" + startTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= concat(year(now()),'-01-01')";
        }
        if (!StringUtil.isNullOrEmpty(endTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= '" + endTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= concat(year(now()),'-12-31')";
        }
        sql += ") AS A GROUP BY USER_ID) AS B WHERE A.USER_ID = B.USER_ID";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                map.put(rs.getString("AGE_TYPE") == null ? "" : rs.getString("AGE_TYPE"), rs.getString("SL") == null ? "" : rs.getString("SL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return map;
    }

    /**
     * 按月销量统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, String> getByMonth(String startTime, String endTime) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "SELECT a.YF ,sum(num)SL,sum(TOTAL_PRICE)TOTAL_PRICE FROM (SELECT month(orders.CREATE_TIME) YF,CAST(orders.TOTAL_PRICE AS DECIMAL(9,2))TOTAL_PRICE,cart.num FROM east_cart AS cart, east_order AS orders, east_item AS item "
                + "WHERE cart.order_id = orders.id_ AND item.ID_ = cart.ITEM_ID  AND orders.state in ('待发货','运输中','已完成') ";
        if (!StringUtil.isNullOrEmpty(startTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= '" + startTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  >= concat(year(now()),'-01-01')";
        }
        if (!StringUtil.isNullOrEmpty(endTime)) {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= '" + endTime + "'";
        }else {
            sql += " AND DATE_FORMAT(orders.CREATE_TIME,'%Y-%m-%d')  <= concat(year(now()),'-12-31')";
        }

        sql += ") AS a GROUP BY a.YF";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                map.put(rs.getString("YF") == null ? "" : rs.getString("YF"), rs.getString("SL") == null ? "" : rs.getString("SL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return map;
    }

    /**
     * 商品销售统计TOP5
     * @return
     */
    public List<Map<String,Object>> getSalesByItems(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sql = "select a.name,a.BRAND,IFNULL(CAST(b.daySales AS DECIMAL(9,2)),0)daySales,IFNULL(b.daySalesNum,0)daySalesNum," +
                "IFNULL(CAST(c.monthSales AS DECIMAL(9,2)),0)monthSales,IFNULL(c.monthSalesNum,0)monthSalesNum," +
                "IFNULL(CAST(d.yearSales AS DECIMAL(9,2)),0)yearSales,IFNULL(d.yearSalesNum,0)yearSalesNum " +
                "from east_item a left join " +
                "(" +
                "SELECT a.ITEM_ID,sum(a.TOTAL_PRICE) daySales ,sum(a.num) daySalesNum FROM east_cart a " +
                "LEFT JOIN east_order b ON a.order_id = b.id_ " +
                "WHERE b.state IN ( '待发货', '运输中', '已完成' ) AND DATE_FORMAT( b.CREATE_TIME, '%Y-%m-%d' ) = DATE_FORMAT(sysdate(), '%Y-%m-%d') " +
                "GROUP BY ITEM_ID) b on a.id_ = b.ITEM_ID " +
                "LEFT JOIN " +
                "(" +
                "SELECT a.ITEM_ID, sum(a.TOTAL_PRICE) monthSales ,sum(a.num) monthSalesNum FROM east_cart a LEFT JOIN east_order b ON a.order_id = b.id_ " +
                "WHERE b.state IN ( '待发货', '运输中', '已完成' ) AND DATE_FORMAT( b.CREATE_TIME, '%Y-%m' ) = DATE_FORMAT(sysdate(), '%Y-%m') " +
                "GROUP BY ITEM_ID) c on a.id_ = c.ITEM_ID " +
                "LEFT JOIN " +
                "(" +
                "SELECT a.ITEM_ID, sum(a.TOTAL_PRICE) yearSales ,sum(a.num) yearSalesNum FROM east_cart a LEFT JOIN east_order b ON a.order_id = b.id_ " +
                "WHERE b.state IN ( '待发货', '运输中', '已完成' ) AND DATE_FORMAT( b.CREATE_TIME, '%Y' ) = DATE_FORMAT(sysdate(), '%Y') " +
                "GROUP BY ITEM_ID) d on a.id_ = d.ITEM_ID " +
                "order by yearSales desc,yearSalesNum desc , monthSales desc,monthSalesNum desc,daySales desc,daySalesNum desc limit 5";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            String daySales = "";
            String monthSales = "";
            String yearSales = "";
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", rs.getString("name") == null ? "" : rs.getString("name"));
                map.put("brand", rs.getString("brand") == null ? "" : rs.getString("brand"));
                map.put("daySalesNum", rs.getString("daySalesNum") == null ? "" : rs.getString("daySalesNum"));
                map.put("monthSalesNum", rs.getString("monthSalesNum") == null ? "" : rs.getString("monthSalesNum"));
                map.put("yearSalesNum", rs.getString("yearSalesNum") == null ? "" : rs.getString("yearSalesNum"));
                daySales =  rs.getString("daySales") == null ? "0" : rs.getString("daySales");
                daySales = "0.00".equals(daySales) ? "0" : daySales;
                monthSales =  rs.getString("monthSales") == null ? "0" : rs.getString("monthSales");
                monthSales = "0.00".equals(monthSales) ? "0" : monthSales;
                yearSales =  rs.getString("yearSales") == null ? "0" : rs.getString("yearSales");
                yearSales = "0.00".equals(yearSales) ? "0" : yearSales;
                map.put("daySales",daySales );
                map.put("monthSales",monthSales );
                map.put("yearSales",yearSales );
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 获取年销量top5
     * @return
     */
    public List<Map<String,Object>> getSalesByYear(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sql = "select a.name,a.BRAND,a.PIC,IFNULL(CAST(d.yearSales AS DECIMAL(9,2)),0)yearSales,IFNULL(d.yearSalesNum,0)yearSalesNum "
                    +"from east_item a left join ("
                    +"SELECT a.ITEM_ID, sum(a.TOTAL_PRICE) yearSales ,sum(a.num) yearSalesNum FROM east_cart a LEFT JOIN east_order b ON a.order_id = b.id_ "
                    +"WHERE b.state IN ( '待发货', '运输中', '已完成' ) AND DATE_FORMAT( b.CREATE_TIME, '%Y' ) = DATE_FORMAT(sysdate(), '%Y') "
                    +"GROUP BY ITEM_ID) d on a.id_ = d.ITEM_ID "
                    +"order by yearSales desc,yearSalesNum desc limit 5";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            String yearSales = "";
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", rs.getString("name") == null ? "" : rs.getString("name"));
                map.put("brand", rs.getString("brand") == null ? "" : rs.getString("brand"));
                map.put("pic", rs.getString("PIC") == null ? "" : rs.getString("PIC"));
                map.put("yearSalesNum", rs.getString("yearSalesNum") == null ? "" : rs.getString("yearSalesNum"));
                yearSales =  rs.getString("yearSales") == null ? "0" : rs.getString("yearSales");
                yearSales = "0.00".equals(yearSales) ? "0" : yearSales;
                map.put("yearSales",yearSales );
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     *
     * @return
     */
    public List<Map<String, Object>> getSales() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String sql = "select '日销量' dayType,IFNULL(sum(IFNULL(a.num,0)),0)salesNum,IFNULL(sum(CAST(a.TOTAL_PRICE AS DECIMAL(9,2))),0)sales from (" +
                "select b.id_ ,b.TOTAL_PRICE,b.CREATE_TIME,a.num,a.price from east_cart  a left join east_order b on a.order_id = b.id_ where " +
                "b.state in ('待发货','运输中','已完成') and DATE_FORMAT(b.CREATE_TIME,'%Y-%m-%d') = DATE_FORMAT(sysdate(),'%Y-%m-%d') " +
                ") as a " +
                " UNION " +
                "select '月销量' dayType,IFNULL(sum(IFNULL(a.num,0)),0)salesNum,IFNULL(sum(CAST(a.TOTAL_PRICE AS DECIMAL(9,2))),0)sales from (" +
                "select b.id_ ,b.TOTAL_PRICE,b.CREATE_TIME,a.num,a.price from east_cart  a left join east_order b on a.order_id = b.id_ where " +
                "b.state in ('待发货','运输中','已完成') and DATE_FORMAT(b.CREATE_TIME,'%Y-%m') = DATE_FORMAT(sysdate(),'%Y-%m') " +
                ") as a " +
                " UNION " +
                "select '年销量' dayType,IFNULL(sum(IFNULL(a.num,0)),0)salesNum,IFNULL(sum(CAST(a.TOTAL_PRICE AS DECIMAL(9,2))),0)sales from (" +
                "select b.id_ ,b.TOTAL_PRICE,b.CREATE_TIME,a.num,a.price from east_cart  a left join east_order b on a.order_id = b.id_ where " +
                "b.state in ('待发货','运输中','已完成') and DATE_FORMAT(b.CREATE_TIME,'%Y') = DATE_FORMAT(sysdate(),'%Y') " +
                ") as a";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            String sales = "";
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("dayType", rs.getString("dayType") == null ? "" : rs.getString("dayType"));
                map.put("salesNum", rs.getString("salesNum") == null ? "0" : rs.getString("salesNum"));
                sales =  rs.getString("sales") == null ? "0" : rs.getString("sales");
                sales = "0.00".equals(sales) ? "0" : sales;
                map.put("sales", sales);
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }
}
