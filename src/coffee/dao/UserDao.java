package coffee.dao;

import coffee.bean.UserDO;
import coffee.util.BaseDAO;
import coffee.util.GUID;
import coffee.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author win10
 * @Description: 用户信息相关数据库操作
 * @date 2021/6/19 19:59
 */
public class UserDao {

    /**
     * 登录验证
     *
     * @param userName
     * @param pwd
     * @return
     */
    public UserDO checkLogin(String userName, String pwd) {
        UserDO userDO = null;
        String sql = "SELECT * FROM EAST_USER WHERE LOGIN_NAME ='" + userName + "'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                String loginPwd = rs.getString("LOGIN_PWD") == null ? "" : rs.getString("LOGIN_PWD");
                if (loginPwd.equals(pwd)) {
                    userDO = getUserInfo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDO;
    }

    /**
     * 根据条件查询用户列表
     *
     * @return
     */
    public List<UserDO> listUser() {
        List<UserDO> userDOList = new ArrayList<UserDO>();
        String sql = "SELECT * FROM EAST_USER";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                userDOList.add(getUserInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDOList;
    }


    /**
     * 根据条件查询用户信息
     *
     * @param sex
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<UserDO> listUser(String sex, String name, String pageIndex, String pageSize) {
        List<UserDO> userDOList = new ArrayList<UserDO>();
        String sql = "SELECT * FROM EAST_USER WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(sex)) {
            sql += " AND SEX = '" + sex + "'";
        }
        if (!StringUtil.isNullOrEmpty(name)) {
            sql += " and NAME LIKE '%" + name + "%'";
        }
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getLayUIMysqlPageSql(sql, pageIndex, pageSize);
        }
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                userDOList.add(getUserInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDOList;
    }

    /**
     * 用户列表信息，及关联的订单记录
     *
     * @param sex
     * @param name
     * @param startAge
     * @param endAge
     * @param keyWords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<UserDO> listUserBuyInfo(String sex, String name, String startAge, String endAge, String keyWords, String pageIndex, String pageSize) {
        List<UserDO> userDOList = new ArrayList<UserDO>();
        String sql = "SELECT user.*,IFNULL(list.SL,0)SL from EAST_USER AS user LEFT JOIN " +
                "(" +
                "SELECT USER_ID ,COUNT(1)SL FROM EAST_ORDER GROUP BY USER_ID" +
                ") AS list " +
                "ON user.ID_ = list.USER_ID WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(sex)) {
            sql += " AND user.SEX = '" + sex + "'";
        }
        if (!StringUtil.isNullOrEmpty(name)) {
            sql += " AND user.NAME = '" + name + "'";
        }
        if (!StringUtil.isNullOrEmpty(startAge)) {
            sql += " AND user.AGE >=" + startAge;
        }
        if (!StringUtil.isNullOrEmpty(endAge)) {
            sql += " AND user.AGE <=" + endAge;
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            sql += " AND (user.NAME LIKE '%" + keyWords + "%')";
        }
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            sql = StringUtil.getLayUIMysqlPageSql(sql, pageIndex, pageSize);
        }
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            while (rs.next()) {
                UserDO userDO = getUserInfo(rs);
                userDO.setHisTotal(rs.getString("SL") == null ? "" : rs.getString("SL"));
                userDOList.add(userDO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDOList;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    public UserDO getUserByName(String userName) {
        UserDO userDO = null;
        String sql = "SELECT * FROM EAST_USER WHERE LOGIN_NAME ='" + userName + "'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                userDO = getUserInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDO;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param userId
     * @return
     */
    public UserDO getUserById(String userId) {
        UserDO userDO = null;
        String sql = "SELECT * FROM EAST_USER WHERE ID_ ='" + userId + "'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                userDO = getUserInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return userDO;
    }

    /**
     * 获取查询结果，组装对象
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private UserDO getUserInfo(ResultSet rs) throws SQLException {
        UserDO userDO = new UserDO();
        userDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        userDO.setLoginName(rs.getString("LOGIN_NAME") == null ? "" : rs.getString("LOGIN_NAME"));
        userDO.setName(rs.getString("NAME") == null ? "" : rs.getString("NAME"));
        userDO.setSex(rs.getString("SEX") == null ? "" : rs.getString("SEX"));
        userDO.setAge(rs.getString("AGE") == null ? "" : rs.getString("AGE"));
        userDO.setDesc(rs.getString("MY_DESC") == null ? "" : rs.getString("MY_DESC"));
        return userDO;
    }

    /**
     * 注册
     *
     * @param userDO
     * @return
     */
    public int addUser(UserDO userDO) {
        int result = 0;
        String sql = "INSERT INTO EAST_USER (ID_,LOGIN_NAME,LOGIN_PWD,NAME) values (?,?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, GUID.newGUID());
            prep.setString(2, userDO.getLoginName());
            prep.setString(3, userDO.getLoginPwd());
            prep.setString(4, userDO.getName());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 保存个人信息
     *
     * @param userDO
     * @return
     */
    public int save(UserDO userDO) {
        int result = 0;
        String sql = "UPDATE EAST_USER SET NAME = ?,AGE=?,SEX=?,MY_DESC=? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, userDO.getName());
            prep.setString(2, userDO.getAge());
            prep.setString(3, userDO.getSex());
            prep.setString(4, userDO.getDesc());
            prep.setString(5, userDO.getId());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 修改密码
     *
     * @param userDO
     * @return
     */
    public int updatePwd(UserDO userDO) {
        int result = 0;
        String sql = "UPDATE EAST_USER SET LOGIN_PWD = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, userDO.getLoginPwd());
            prep.setString(2, userDO.getId());
            result = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        int result = 0;
        String sql = "DELETE FROM EAST_USER WHERE ID_ = ?";
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
