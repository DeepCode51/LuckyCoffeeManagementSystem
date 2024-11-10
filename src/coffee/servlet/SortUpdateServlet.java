package coffee.servlet;

import coffee.bean.UserDO;
import coffee.dao.SortDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 分类修改
 * @date 2022-3-2 21:49
 */
public class SortUpdateServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
        String sortValue = request.getParameter("sortValue") == null ? "" : request.getParameter("sortValue");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        if (userDO != null) {
            SortDao sortDao = new SortDao();
            int count = sortDao.getBySortValue(sort,sortValue);
            if(count == 0) {
                //保存分类
                int result = sortDao.save(id, sort, sortValue);
                if (result > 0) {
                    //成功
                    out.println(JsonUtil.toSuccessJsonResult("保存成功"));
                } else {
                    out.println(JsonUtil.toFailJsonResult(-1, "操作失败请重试"));
                }
            }else {
                out.println(JsonUtil.toFailJsonResult(-1, "已存在" + sortValue));
            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-2, "请先登录"));
        }
        out.flush();
        out.close();
    }
}
