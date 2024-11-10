package coffee.dao;

import coffee.bean.AddressDO;
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
 * @Description: 地址管理
 * @date 2022/4/17 12:52
 */
public class AddressDao {

    /**
     * 获取个人地址管理列表
     *
     * @param userId
     * @return
     */
    public List<AddressDO> list(String userId, String pageIndex, String pageSize) {
        List<AddressDO> list = new ArrayList<AddressDO>();
        String sql = "SELECT *,CONCAT(PROVINCE,CITY,DISTRICT,ADDRESS) FULL_ADDR FROM EAST_ADDRESS WHERE USER_ID = '" + userId + "' ORDER BY DEFAULT_ADDR DESC,CTIME DESC";
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
     * 获取用户默认地址
     * @param userId
     * @return
     */
    public AddressDO getDefaultAddr(String userId) {
        AddressDO addressDO = null;
        String sql = "SELECT *,CONCAT(PROVINCE,CITY,DISTRICT,ADDRESS) FULL_ADDR FROM EAST_ADDRESS WHERE USER_ID = '" + userId + "' ORDER BY DEFAULT_ADDR DESC,CTIME DESC limit 1";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                addressDO = getInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return addressDO;
    }

    /**
     * 获取收货地址信息
     *
     * @param id
     * @return
     */
    public AddressDO getById(String id) {
        AddressDO addressDO = new AddressDO();
        String sql = "SELECT *,CONCAT(PROVINCE,CITY,DISTRICT,ADDRESS) FULL_ADDR FROM EAST_ADDRESS WHERE ID_ = '" + id + "'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                addressDO = getInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return addressDO;
    }

    /**
     * 获取数据查询返回信息
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private AddressDO getInfo(ResultSet rs) throws SQLException {
        AddressDO addressDO = new AddressDO();
        addressDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        addressDO.setName(rs.getString("NAME") == null ? "" : rs.getString("NAME"));
        addressDO.setPhone(rs.getString("PHONE") == null ? "" : rs.getString("PHONE"));
        addressDO.setProvince(rs.getString("PROVINCE") == null ? "" : rs.getString("PROVINCE"));
        addressDO.setCity(rs.getString("CITY") == null ? "" : rs.getString("CITY"));
        addressDO.setDistrict(rs.getString("DISTRICT") == null ? "" : rs.getString("DISTRICT"));
        addressDO.setAddress(rs.getString("ADDRESS") == null ? "" : rs.getString("ADDRESS"));
        addressDO.setFullAddr(rs.getString("FULL_ADDR") == null ? "" : rs.getString("FULL_ADDR"));
        addressDO.setUserId(rs.getString("USER_ID") == null ? "" : rs.getString("USER_ID"));
        addressDO.setDefaultAddr(rs.getString("DEFAULT_ADDR") == null ? "" : rs.getString("DEFAULT_ADDR"));
        addressDO.setCtime(rs.getString("CTIME") == null ? "" : rs.getString("CTIME"));
        return addressDO;
    }

    /**
     * 添加
     *
     * @param addressDO
     * @return
     */
    public int add(AddressDO addressDO) {
        int result = 0;
        String sql = "INSERT INTO EAST_ADDRESS VALUES (?,?,?,?,?,?,?,?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, GUID.newGUID());
            prep.setString(2, addressDO.getName());
            prep.setString(3, addressDO.getPhone());
            prep.setString(4, addressDO.getProvince());
            prep.setString(5, addressDO.getCity());
            prep.setString(6, addressDO.getDistrict());
            prep.setString(7, addressDO.getAddress());
            prep.setString(8, addressDO.getUserId());
            prep.setString(9, CommonUtil.getCurrentTime());
            prep.setString(10, "否");
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 修改保存
     *
     * @param addressDO
     * @return
     */
    public int save(AddressDO addressDO) {
        int result = 0;
        String sql = "UPDATE EAST_ADDRESS SET NAME = ?,PHONE = ?,PROVINCE = ?,CITY = ?,DISTRICT = ?,ADDRESS = ?,CTIME = ?,DEFAULT_ADDR = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, addressDO.getName());
            prep.setString(2, addressDO.getPhone());
            prep.setString(3, addressDO.getProvince());
            prep.setString(4, addressDO.getCity());
            prep.setString(5, addressDO.getDistrict());
            prep.setString(6, addressDO.getAddress());
            prep.setString(7, CommonUtil.getCurrentTime());
            prep.setString(8, addressDO.getDefaultAddr());
            prep.setString(9, addressDO.getId());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 设置全部非默认
     *
     * @return
     */
    public int cancelAddr() {
        int result = 0;
        String sql = "UPDATE EAST_ADDRESS SET DEFAULT_ADDR = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, "否");
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 设置默认地址
     *
     * @param id
     * @return
     */
    public int setAddr(String id,String defaultAddr) {
        int result = 0;
        String sql = "UPDATE EAST_ADDRESS SET DEFAULT_ADDR = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, defaultAddr);
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
        String sql = "DELETE FROM EAST_ADDRESS WHERE ID_ = ?";
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
