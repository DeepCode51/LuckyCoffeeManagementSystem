package coffee.servlet;

import coffee.dao.AreaDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 行政单位获取
 * @date 2022/4/17 11:47
 */
public class AreaServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type") == null ? "" : request.getParameter("type");
        String parent = request.getParameter("parent") == null ? "" : request.getParameter("parent");
        AreaDao areaDao = new AreaDao();
        if("1".equals(type)){
            //获取省份
            out.println(JsonUtil.toSuccessJsonResult(areaDao.listProvince()));
        }else {
            //根据上级单位，获取下级单位信息
            out.println(JsonUtil.toSuccessJsonResult(areaDao.listAreaByParent(parent)));
        }
        out.flush();
        out.close();
    }
}
