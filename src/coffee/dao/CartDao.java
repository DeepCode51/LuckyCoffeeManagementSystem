package coffee.dao;

import coffee.bean.CartDO;
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
 * @Description: 购物车
 * @date 2021/7/3 23:50
 */
public class CartDao {

    /**
     * 查询
     *
     * @param userId
     * @return
     */
    public List<CartDO> list(String userId) {
        List<CartDO> list = new ArrayList<CartDO>();
        String sql = "SELECT * FROM EAST_CART WHERE USER_ID = '" + userId + "' AND STATE = '待提交' ORDER BY CREATE_TIME DESC";
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
     * 购物车分页查询
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CartDO> list(String userId, String pageIndex, String pageSize) {
        List<CartDO> list = new ArrayList<CartDO>();
//        String sql = "SELECT * FROM EAST_CART WHERE USER_ID = '" + userId + "' AND STATE = '待提交' ORDER BY CREATE_TIME DESC";
        String sql = "select a.id_,b.ID_ ITEM_ID,b.NAME ITEM_NAME,b.PIC ITEM_PIC,b.PRICE,a.NUM,a.NUM*b.PRICE TOTAL_PRICE,a.CREATE_TIME,a.USER_ID,a.STATE,a.ORDER_ID,b.STATE ITEM_STATE from east_cart a,east_item b " +
                "where a.ITEM_ID = b.id_ AND a.STATE = '待提交' AND A.USER_ID = '"+ userId +"' ORDER BY CREATE_TIME DESC";
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getLayUIMysqlPageSql(sql, pageIndex, pageSize);
        }
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(getInfos(rs));
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
     * @param userId
     * @return
     */
    public List<CartDO> listOrderItem(String userId) {
        List<CartDO> list = new ArrayList<CartDO>();
//        String sql = "SELECT * FROM EAST_CART WHERE USER_ID = '" + userId + "' AND STATE = '待提交' ORDER BY CREATE_TIME DESC";
        String sql = "select a.id_,b.ID_ ITEM_ID,b.NAME ITEM_NAME,b.PIC ITEM_PIC,b.PRICE,a.NUM,a.NUM*b.PRICE TOTAL_PRICE,a.CREATE_TIME,a.USER_ID,a.STATE,a.ORDER_ID,b.STATE ITEM_STATE from east_cart a,east_item b " +
                "where a.ITEM_ID = b.id_ AND a.STATE = '待提交' AND A.USER_ID = '"+ userId +"' AND b.STATE = '上架' ORDER BY CREATE_TIME DESC";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(getInfos(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 查询订单关联的购物车商品记录
     *
     * @param orderId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CartDO> listByOrderId(String orderId, String pageIndex, String pageSize) {
        List<CartDO> list = new ArrayList<CartDO>();
        String sql = "SELECT * FROM EAST_CART WHERE ORDER_ID = '" + orderId + "' ORDER BY CREATE_TIME DESC";
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
     * 根据商品ID，查询购物车中是否已有该商品
     * 用途：当购物车车已有该商品时，购物车中不在新增一条商品，而是在原有商品数量上 +1，同时计算 “小计”
     *
     * @param userId
     * @param itemId
     * @return
     */
    public List<CartDO> listByItemId(String userId, String itemId) {
        List<CartDO> list = new ArrayList<CartDO>();
        String sql = "SELECT * FROM EAST_CART WHERE USER_ID = '" + userId + "' AND ITEM_ID = '" + itemId + "' AND STATE = '待提交' ORDER BY CREATE_TIME DESC";
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
     * 生成订单时，以商品表信息 更新订单所关联的购物车表商品信息
     *
     * @param orderId
     * @return
     */
    public int updateCart(String orderId) {
        int result = 0;
        String sql = "update east_cart a,east_item b set a.ITEM_NAME = b.NAME,a.ITEM_PIC = b.PIC,a.PRICE = b.PRICE,a.TOTAL_PRICE = a.NUM * b.PRICE "
                + "WHERE a.ITEM_ID = b.id_ and a.ORDER_ID = ?";
//        System.out.println(sql);
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, orderId);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 获取购物车商品总价
     *
     * @param userId
     * @return
     */
    public String getTotalPrice(String userId) {
        String result = "";
        String sql = "SELECT ROUND(SUM(TOTAL_PRICE),2)TOTAL_PRICE FROM east_cart a, east_item b WHERE a.ITEM_ID = b.id_ AND a.STATE = '待提交'  AND A.USER_ID = '"+ userId +"' AND b.STATE = '上架'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                result = rs.getString("TOTAL_PRICE") == null ? "" : rs.getString("TOTAL_PRICE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 获取查询结果，组装对象
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private CartDO getInfo(ResultSet rs) throws SQLException {
        CartDO cartDO = new CartDO();
        cartDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        cartDO.setItemId(rs.getString("ITEM_ID") == null ? "" : rs.getString("ITEM_ID"));
        cartDO.setItemName(rs.getString("ITEM_NAME") == null ? "" : rs.getString("ITEM_NAME"));
        cartDO.setItemPic(rs.getString("ITEM_PIC") == null ? "" : rs.getString("ITEM_PIC"));
        cartDO.setPrice(rs.getString("PRICE") == null ? "" : rs.getString("PRICE"));
        cartDO.setNum(rs.getString("NUM") == null ? "" : rs.getString("NUM"));
        cartDO.setTotalPrice(rs.getString("TOTAL_PRICE") == null ? "" : rs.getString("TOTAL_PRICE"));
        cartDO.setCreateTime(rs.getString("CREATE_TIME") == null ? "" : rs.getString("CREATE_TIME"));
        cartDO.setUserId(rs.getString("USER_ID") == null ? "" : rs.getString("USER_ID"));
        cartDO.setState(rs.getString("STATE") == null ? "" : rs.getString("STATE"));
        cartDO.setOrderId(rs.getString("ORDER_ID") == null ? "" : rs.getString("ORDER_ID"));
        return cartDO;
    }

    private CartDO getInfos(ResultSet rs) throws SQLException {
        CartDO cartDO = new CartDO();
        cartDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        cartDO.setItemId(rs.getString("ITEM_ID") == null ? "" : rs.getString("ITEM_ID"));
        cartDO.setItemName(rs.getString("ITEM_NAME") == null ? "" : rs.getString("ITEM_NAME"));
        cartDO.setItemPic(rs.getString("ITEM_PIC") == null ? "" : rs.getString("ITEM_PIC"));
        cartDO.setPrice(rs.getString("PRICE") == null ? "" : rs.getString("PRICE"));
        cartDO.setNum(rs.getString("NUM") == null ? "" : rs.getString("NUM"));
        cartDO.setTotalPrice(rs.getString("TOTAL_PRICE") == null ? "" : rs.getString("TOTAL_PRICE"));
        cartDO.setCreateTime(rs.getString("CREATE_TIME") == null ? "" : rs.getString("CREATE_TIME"));
        cartDO.setUserId(rs.getString("USER_ID") == null ? "" : rs.getString("USER_ID"));
        cartDO.setState(rs.getString("STATE") == null ? "" : rs.getString("STATE"));
        cartDO.setOrderId(rs.getString("ORDER_ID") == null ? "" : rs.getString("ORDER_ID"));
        cartDO.setItemState(rs.getString("ITEM_STATE") == null ? "" : rs.getString("ITEM_STATE"));
        return cartDO;
    }

    /**
     * 添加
     *
     * @param cartDO
     * @return
     */
    public int add(CartDO cartDO) {
        int result = 0;
        String sql = "INSERT INTO EAST_CART VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, GUID.newGUID());
            prep.setString(2, cartDO.getItemId());
            prep.setString(3, cartDO.getItemName());
            prep.setString(4, cartDO.getItemPic());
            prep.setString(5, cartDO.getPrice());
            prep.setString(6, cartDO.getNum());
            prep.setString(7, cartDO.getTotalPrice());
            prep.setString(8, CommonUtil.getCurrentTime());
            prep.setString(9, cartDO.getUserId());
            prep.setString(10, "待提交");
            prep.setString(11, "");
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 保存
     *
     * @param cartDO
     * @return
     */
    public int save(CartDO cartDO) {
        int result = 0;
        String sql = "UPDATE EAST_CART SET NUM = ?,TOTAL_PRICE = ?,CREATE_TIME = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, cartDO.getNum());
            prep.setString(2, cartDO.getTotalPrice());
            prep.setString(3, CommonUtil.getCurrentTime());
            prep.setString(4, cartDO.getId());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 购物车已有商品增加数量
     *
     * @param price
     * @return
     */
    public int save(String price, String id) {
        String time = CommonUtil.getCurrentTime();
        String sql = "UPDATE EAST_CART SET PRICE = '" + price + "', NUM = NUM + 1,TOTAL_PRICE = ROUND(NUM* " + price + ",2),CREATE_TIME = '" + time + "' WHERE ID_ = '" + id + "'";
        BaseDAO dao = new BaseDAO();
        return dao.executeUpdate(sql);
    }

    /**
     * 购物车 提交订单
     *
     * @param list
     * @param orderId
     * @return
     */
    public int commit(List<CartDO> list, String orderId) {
        String ids = "";
        for (int i = 0; i < list.size(); i++) {
            if (!StringUtil.isNullOrEmpty(ids)) {
                ids += ",";
            }
            ids += "'" + list.get(i).getId() + "'";
        }
        String time = CommonUtil.getCurrentTime();
        String sql = "UPDATE EAST_CART SET STATE = '已提交', ORDER_ID = '" + orderId + "',CREATE_TIME = '" + time + "' WHERE ID_ IN (" + ids + ")";
        BaseDAO dao = new BaseDAO();
        return dao.executeUpdate(sql);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        int result = 0;
        String sql = "DELETE FROM EAST_CART WHERE ID_ = ?";
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
