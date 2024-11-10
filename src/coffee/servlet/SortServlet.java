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
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @Description: 查询分类
 * @date 2022-3-2 21:20
 */
public class SortServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
        String keyWords = request.getParameter("keyWords") == null ? "" : request.getParameter("keyWords");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");

        SortDao sortDao = new SortDao();
        List<SortDO> list = sortDao.list(sort, keyWords, "", "");
        int total = list.size();
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            list = sortDao.list(sort, keyWords, pageIndex, pageSize);
        }
        out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        out.flush();
        out.close();
    }
}
