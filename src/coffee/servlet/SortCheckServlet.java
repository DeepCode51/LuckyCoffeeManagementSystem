package coffee.servlet;

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
 * @Description: 分类唯一性校验
 * @date 2022-3-2 21:20
 */
public class SortCheckServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
        String sortValue = request.getParameter("sortValue") == null ? "" : request.getParameter("sortValue");
        SortDao sortDao = new SortDao();
        int count = sortDao.getBySortValue(sort,sortValue);
        out.println(JsonUtil.toSuccessJsonResult(count));
        out.flush();
        out.close();
    }
}
