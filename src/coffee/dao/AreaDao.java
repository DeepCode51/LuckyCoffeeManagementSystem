package coffee.dao;

import coffee.bean.AreaDO;
import coffee.util.BaseDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @Description: 行政单位维护
 * @date 2022/4/17 10:17
 */
public class AreaDao {

    /**
     * 获取所有省份信息，按行政编码排序
     *
     * @return
     */
    public List<AreaDO> listProvince() {
        List<AreaDO> list = new ArrayList<AreaDO>();
        String sql = "SELECT * FROM EAST_AREA WHERE AREA_LEVEL = 1 ORDER BY AREA_CODE";
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
     * 根据上级单位，获取下级单位信息
     *
     * @param parent
     * @return
     */
    public List<AreaDO> listAreaByParent(String parent) {
        List<AreaDO> list = new ArrayList<AreaDO>();
        String sql = "SELECT * FROM EAST_AREA WHERE PARENT = '" + parent + "' ORDER BY AREA_CODE";
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
     * 获取数据查询返回信息
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private AreaDO getInfo(ResultSet rs) throws SQLException {
        AreaDO areaDO = new AreaDO();
        areaDO.setAreaCode(rs.getString("AREA_CODE") == null ? "" : rs.getString("AREA_CODE"));
        areaDO.setArea(rs.getString("AREA") == null ? "" : rs.getString("AREA"));
        areaDO.setSort(rs.getString("SORT") == null ? "" : rs.getString("SORT"));
        areaDO.setAreaLevel(rs.getString("AREA_LEVEL") == null ? "" : rs.getString("AREA_LEVEL"));
        areaDO.setAreaParent(rs.getString("PARENT") == null ? "" : rs.getString("PARENT"));
        return areaDO;
    }
}
