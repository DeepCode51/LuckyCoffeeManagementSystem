package coffee.servlet;


import coffee.bean.SortDO;
import coffee.bean.UserDO;
import coffee.dao.SortDao;
import coffee.util.JsonUtil;
import coffee.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 分类添加
 * @date 2022-3-2 21:43
 */
public class SortAddServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        PrintWriter out = response.getWriter();
        if (userDO != null) {
            String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
            String sortValue = request.getParameter("sortValue") == null ? "" : request.getParameter("sortValue");
            SortDao sortDao = new SortDao();

            int count = sortDao.getBySortValue(sort,sortValue);
            if(count == 0){
                SortDO sortDO = new SortDO();
                sortDO.setSort(sort);
                sortDO.setSortValue(sortValue);
                String result = sortDao.add(sortDO);
                if (!StringUtil.isNullOrEmpty(result)) {
                    out.println(JsonUtil.toSuccessJsonResult(result));
                } else {
                    out.println(JsonUtil.toFailJsonResult(-1, "创建失败"));
                }
            }else {
                out.println(JsonUtil.toFailJsonResult(-1, "已存在" + sortValue));
            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-2, "请先登录"));
        }
    }
}
