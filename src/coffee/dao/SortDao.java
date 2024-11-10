package coffee.dao;

import coffee.bean.SortDO;
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
 * @Description: 商品分类
 * @date 2021/7/3 23:51
 */
public class SortDao {

    /**
     * 查询分类表数据
     *
     * @param sort      地区 or 包装
     * @param keyWords  具体搜索的关键字
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @return
     */
    public List<SortDO> list(String sort, String keyWords, String pageIndex, String pageSize) {
        List<SortDO> list = new ArrayList<SortDO>();
        String sql = "SELECT * FROM EAST_SORT WHERE 1 = 1 ";
        if (!StringUtil.isNullOrEmpty(sort)) {
            sql += " AND SORT = '" + sort + "'";
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            sql += " AND SORT_VALUE LIKE '%" + keyWords + "%'";
        }
        sql += " ORDER BY SORT_VALUE";
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
     * 组装图书信息
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private SortDO getInfo(ResultSet rs) throws SQLException {
        SortDO sortDO = new SortDO();
        sortDO.setId(rs.getString("ID_") == null ? "" : rs.getString("ID_"));
        sortDO.setSort(rs.getString("SORT") == null ? "" : rs.getString("SORT"));
        sortDO.setSortValue(rs.getString("SORT_VALUE") == null ? "" : rs.getString("SORT_VALUE"));
        return sortDO;
    }

    /**
     * 根据分类及分类值，查询数量。可用于判断是否已存在某一分类
     * @param sort
     * @param sortValue
     * @return
     */
    public int getBySortValue(String sort,String sortValue){
        int result = 0;
        String sql = "SELECT COUNT(1)SL FROM EAST_SORT WHERE SORT = '" + sort + "' AND SORT_VALUE = '"+ sortValue +"'";
        BaseDAO dao = new BaseDAO();
        ResultSet rs = dao.executeQuery(sql);
        try {
            if (rs.next()) {
                result = rs.getString("SL") == null ? 0 : rs.getInt("SL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close();
        }
        return result;
    }

    /**
     * 添加
     *
     * @param sortDO
     * @return
     */
    public String add(SortDO sortDO) {
        String id = "";
        String sql = "INSERT INTO EAST_SORT (ID_,SORT,SORT_VALUE) VALUES (?,?,?)";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            id = GUID.newGUID();
            prep.setString(1, id);
            prep.setString(2, sortDO.getSort());
            prep.setString(3, sortDO.getSortValue());
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
     * 保存分类
     *
     * @param id
     * @return
     */
    public int save(String id, String sort, String sortValue) {
        int result = 0;
        String sql = "UPDATE EAST_SORT SET SORT = ?,SORT_VALUE = ? WHERE ID_ = ?";
        BaseDAO dao = new BaseDAO();
        PreparedStatement prep = dao.createPrep(sql);
        try {
            prep.setString(1, sort);
            prep.setString(2, sortValue);
            prep.setString(3, id);
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
        String sql = "DELETE FROM EAST_SORT WHERE ID_ = ?";
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
