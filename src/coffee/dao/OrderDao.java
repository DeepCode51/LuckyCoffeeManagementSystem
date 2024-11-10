package coffee.dao;

import coffee.bean.OrderDO;
import coffee.util.BaseDAO;
import coffee.util.CommonUtil;
import coffee.util.GUID;
import coffee.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @Description: 订单
 * @date 2021/7/3 23:51
 */
public class OrderDao {

    /**
     * 查询订单
     *
     * @param userId
     * @return
     */
    public List<OrderDO> list(String userId, String orderNo) {
        List<OrderDO> list = new ArrayList<OrderDO>();
        String sql = "SELECT * FROM EAST_ORDER WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(userId)) {
            //普通用户查询自己的订单
            sql += " AND USER_ID = '" + userId + "'";
        }
        if (!StringUtil.isNullOrEmpty(orderNo)) {
            //根据订单编号查询同一订单编号中的商品
            sql += " AND ORDER_NO = '" + orderNo + "'";
        }
        sql += " ORDER BY CREATE_TIME DESC";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(getInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 查询订单
     *
     * @param userId
     * @param orderNo   订单编号
     * @param state     订单状态：待支付、待发货、运输中、已完成（确认收货）
     * @param keyWords  搜索关键字
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<OrderDO> listOrder(String userId, String orderNo, String state, String keyWords, String pageIndex, String pageSize) {
        List<OrderDO> list = new ArrayList<OrderDO>();
        String sql = "SELECT * FROM EAST_ORDER WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(userId)) {
            //普通用户查询自己的订单
            sql += " AND USER_ID = '" + userId + "'";
        }
        if (!StringUtil.isNullOrEmpty(orderNo)) {
            //根据订单编号查询同一订单编号中的商品
            sql += " AND ORDER_NO = '" + orderNo + "'";
        }
        if (!StringUtil.isNullOrEmpty(state)) {
            sql += " AND STATE = '" + state + "'";
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            sql += " AND ORDER_NO LIKE '%" + keyWords + "%'";
        }
        sql += " ORDER BY CREATE_TIME DESC";
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getLayUIMysqlPageSql(sql, pageIndex, pageSize);
        }
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(getInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 查询订单表中的所有订单编号
     *
     * @param userId
     * @return
     */
    public List<String> listOrderNo(String userId) {
        List<String> list = new ArrayList<String>();
        String sql = "SELECT DISTINCT ORDER_NO FROM EAST_ORDER ";
        if (!StringUtil.isNullOrEmpty(userId)) {
            //普通用户查询自己的订单
            sql += " WHERE USER_ID = '" + userId + "'";
        }
        sql += " ORDER BY CREATE_TIME DESC";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(rs.getString("ORDER_NO") == null ? "" : rs.getString("ORDER_NO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 生成订单号
     * @return
     */
    public String getOrderNo(){
        String orderNo = "";
        String rqSql = "select DATE_FORMAT(NOW(),'%Y%m%d')RQ";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(rqSql);
        try {
            if (rs.next()) {
                orderNo = rs.getString("RQ") == null ? CommonUtil.getCurrentDay() : rs.getString("RQ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        int total = getOrderTotalByToday() + 1;
        if(total >0 && total < 10){
            orderNo += "-0000" + total;
        }else if(total >=10 && total < 100){
            orderNo += "-000" + total;
        }else if(total >=100 && total < 1000){
            orderNo += "-00" + total;
        }else if(total >=1000 && total < 10000){
            orderNo += "-0" + total;
        }
        return orderNo;
    }

    /**
     * 查询订单表中今日订单数量
     *
     * @return
     */
    public int getOrderTotalByToday() {
        int total = 0;
        List<String> list = new ArrayList<String>();
        String sql = "select count(id_)NUM from EAST_ORDER where to_days(create_time) = to_days(now())";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                total = rs.getString("NUM") == null ? 0 : rs.getInt("NUM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return total;
    }

    /**
     * 组装图书信息
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private OrderDO getInfo(ResultSet rs) throws SQLException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        orderDO.setOrderNo(rs.getString("ORDER_NO") == null ? "" : rs.getString("ORDER_NO"));
        orderDO.setTotalPrice(rs.getString("TOTAL_PRICE") == null ? "" : rs.getString("TOTAL_PRICE"));
        orderDO.setUserId(rs.getString("USER_ID") == null ? "" : rs.getString("USER_ID"));
        orderDO.setUserName(rs.getString("USER_NAME") == null ? "" : rs.getString("USER_NAME"));
        orderDO.setState(rs.getString("STATE") == null ? "" : rs.getString("STATE"));
        orderDO.setPhone(rs.getString("PHONE") == null ? "" : rs.getString("PHONE"));
        orderDO.setAddress(rs.getString("ADDRESS") == null ? "" : rs.getString("ADDRESS"));
        orderDO.setCreateTime(rs.getString("CREATE_TIME") == null ? "" : rs.getString("CREATE_TIME"));
        orderDO.setPayTime(rs.getString("PAY_TIME") == null ? "" : rs.getString("PAY_TIME"));
        orderDO.setSendTime(rs.getString("SEND_TIME") == null ? "" : rs.getString("SEND_TIME"));
        orderDO.setFinishTime(rs.getString("FINISH_TIME") == null ? "" : rs.getString("FINISH_TIME"));
        return orderDO;
    }

    /**
     * 添加
     *
     * @param orderDO
     * @return
     */
    public String add(OrderDO orderDO) {
        String id = "";
        String sql = "INSERT INTO EAST_ORDER (ID_,ORDER_NO,TOTAL_PRICE,USER_ID,USER_NAME,STATE,CREATE_TIME,PHONE,ADDRESS) VALUES (?,?,?,?,?,?,?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            id = GUID.newGUID();
            prep.setString(1, id);
            prep.setString(2, orderDO.getOrderNo());
            prep.setString(3, orderDO.getTotalPrice());
            prep.setString(4, orderDO.getUserId());
            prep.setString(5, orderDO.getUserName());
            prep.setString(6, orderDO.getState());
            prep.setString(7, CommonUtil.getCurrentTime());
            prep.setString(8, orderDO.getPhone());
            prep.setString(9, orderDO.getAddress());
            int result = prep.executeUpdate();
            if (result > 0) {
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return id;
    }

    /**
     * 订单状态变更，可用于支付、发货、确认收货 ;订单状态：待支付、待发货、运输中、已完成（确认收货）
     *
     * @param id
     * @return
     */
    public int save(String id,String state) {
        int result = 0;
        String sql = "";
        if("0".equals(state)){
        	sql = "STATE = '待发货',PAY_TIME = ?";
        }else if("1".equals(state)){
        	 sql = "STATE = '运输中',SEND_TIME = ?";
        }else if("2".equals(state)){
        	sql = "STATE = '已完成',FINISH_TIME = ?";
        }
 
        if(!StringUtil.isNullOrEmpty(sql)){
            sql = "UPDATE EAST_ORDER SET " + sql + " WHERE ID_ = ?";
        }
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, CommonUtil.getCurrentTime());
            prep.setString(2, id);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 完成订单
     *
     * @param id
     * @return
     */
    public int finish(String id) {
        int result = 0;
        String sql = "UPDATE EAST_ORDER SET STATE = '已完成',FINISH_TIME = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, CommonUtil.getCurrentTime());
            prep.setString(2, id);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        int result = 0;
        String sql = "DELETE FROM EAST_ORDER WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, id);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }
}
