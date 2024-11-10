package coffee.dao;

import coffee.bean.ItemDO;
import coffee.util.BaseDAO;
import coffee.util.GUID;
import coffee.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @DESCription: 商品
 * @date 2021/7/1 22:47
 */
public class ItemDao {
    /**
     * 根据条件查询商品列表
     *
     * @return
     */
    public List<ItemDO> listItem() {
        List<ItemDO> list = new ArrayList<ItemDO>();
        String sql = "SELECT * FROM EAST_ITEM";
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
     * 根据条件查询商品列表
     *
     * @param brand
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<ItemDO> listItem(String brand, String name, String pageIndex, String pageSize) {
        List<ItemDO> list = new ArrayList<ItemDO>();
        String sql = "SELECT * FROM EAST_ITEM WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(brand)) {
            sql += " AND BRAND = '" + brand + "'";
        }
        if (!StringUtil.isNullOrEmpty(name)) {
            sql += " AND NAME LIKE '%" + name + "%'";
        }
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getPageSql(sql, pageIndex, pageSize);
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
     * 查询信息，关联已购买记录，获取可买库存
     *
     * @param brand
     * @param name
     * @param place
     * @param packet
     * @param state     商品上架/下架状态
     * @param keyWords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<ItemDO> listItemInfo(String brand, String name, String place, String packet, String state, String keyWords, String pageIndex, String pageSize) {
        List<ItemDO> list = new ArrayList<ItemDO>();
        String sql = "SELECT item.*,item.TOTAL - SL AS STOCK,SL AS SALES FROM(" +
                "SELECT item.*,IFNULL(his.SL,0)SL FROM EAST_ITEM AS item LEFT JOIN " +
                "(" +
                "SELECT ITEM_ID ,COUNT(1)SL FROM EAST_CART WHERE STATE = '已提交' GROUP BY ITEM_ID " +
                ") AS his ON item.ID_ = his.ITEM_ID" +
                ") AS item WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(brand)) {
            sql += " AND item.BRAND = '" + brand + "'";
        }
        if (!StringUtil.isNullOrEmpty(name)) {
            sql += " and item.NAME LIKE '%" + name + "%'";
        }
        if (!StringUtil.isNullOrEmpty(place)) {
            sql += " and item.PLACE LIKE '%" + place + "%'";
        }
        if (!StringUtil.isNullOrEmpty(packet)) {
            sql += " and item.PACKET LIKE '%" + packet + "%'";
        }
        if (!StringUtil.isNullOrEmpty(state)) {
            sql += " and item.STATE = '" + state + "'";
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            sql += " and (item.NAME LIKE '%" + keyWords + "%' OR item.ITEM_DESC LIKE '%" + keyWords + "%')";
        }
        sql += " ORDER BY item.NAME ";
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getLayUIMysqlPageSql(sql, pageIndex, pageSize);
        }
//        System.out.println(sql);
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                ItemDO itemDO = getInfo(rs);
                itemDO.setStock(rs.getString("STOCK") == null ? "" : rs.getString("STOCK"));
                itemDO.setSales(rs.getString("SALES") == null ? "" : rs.getString("SALES"));
                list.add(itemDO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return list;
    }

    /**
     * 根据ID 获取信息
     *
     * @param id
     * @return
     */
    public ItemDO getInfoById(String id) {
        ItemDO itemDO = null;
        String sql = "SELECT * FROM EAST_ITEM WHERE ID_ = '" + id + "'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                itemDO = getInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return itemDO;
    }

    /**
     * 获取查询结果，组装对象
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private ItemDO getInfo(ResultSet rs) throws SQLException {
        ItemDO itemDO = new ItemDO();
        itemDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        itemDO.setName(rs.getString("NAME") == null ? "" : rs.getString("NAME"));
        itemDO.setDesc(rs.getString("ITEM_DESC") == null ? "" : rs.getString("ITEM_DESC"));
        itemDO.setPrice(rs.getString("PRICE") == null ? "" : rs.getString("PRICE"));
        itemDO.setTotal(rs.getString("TOTAL") == null ? "" : rs.getString("TOTAL"));
        itemDO.setPlace(rs.getString("PLACE") == null ? "" : rs.getString("PLACE"));
        itemDO.setPic(rs.getString("PIC") == null ? "" : rs.getString("PIC"));
        itemDO.setBrand(rs.getString("BRAND") == null ? "" : rs.getString("BRAND"));
        itemDO.setWeight(rs.getString("WEIGHT") == null ? "" : rs.getString("WEIGHT"));
        itemDO.setPacket(rs.getString("PACKET") == null ? "" : rs.getString("PACKET"));
        itemDO.setState(rs.getString("STATE") == null ? "" : rs.getString("STATE"));
        return itemDO;
    }

    /**
     * 添加
     *
     * @param itemDO
     * @return
     */
    public int add(ItemDO itemDO) {
        int result = 0;
        String sql = "INSERT INTO EAST_ITEM VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, GUID.newGUID());
            prep.setString(2, itemDO.getName());
            prep.setString(3, itemDO.getDesc());
            prep.setString(4, itemDO.getPrice());
            prep.setString(5, itemDO.getTotal());
            prep.setString(6, itemDO.getPlace());
            prep.setString(7, itemDO.getPic());
            prep.setString(8, itemDO.getBrand());
            prep.setString(9, itemDO.getWeight());
            prep.setString(10, itemDO.getPacket());
            prep.setString(11, itemDO.getState());
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
     * @param itemDO
     * @return
     */
    public int save(ItemDO itemDO) {
        int result = 0;
        String sql = "UPDATE EAST_ITEM SET NAME = ?,ITEM_DESC=?,PRICE=?,TOTAL=?,PLACE=?,PIC=?,BRAND=?,WEIGHT=?,PACKET=? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, itemDO.getName());
            prep.setString(2, itemDO.getDesc());
            prep.setString(3, itemDO.getPrice());
            prep.setString(4, itemDO.getTotal());
            prep.setString(5, itemDO.getPlace());
            prep.setString(6, itemDO.getPic());
            prep.setString(7, itemDO.getBrand());
            prep.setString(8, itemDO.getWeight());
            prep.setString(9, itemDO.getPacket());
            prep.setString(10, itemDO.getId());
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
        String sql = "DELETE FROM EAST_ITEM WHERE ID_ = ?";
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

    /**
     * 商品上架下架
     *
     * @param id
     * @param state
     * @return
     */
    public int itemPut(String id, String state) {
        int result = 0;
        String sql = "UPDATE EAST_ITEM SET STATE = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, state);
            prep.setString(2, id);
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }
}
