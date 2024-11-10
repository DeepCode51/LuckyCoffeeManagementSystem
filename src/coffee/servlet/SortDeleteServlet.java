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
 * @Description: 分类删除
 * @date 2022-3-2 21:47
 */
public class SortDeleteServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        SortDao sortDao = new SortDao();
        int result = sortDao.delete(id);
        if (result > 0) {
            request.setAttribute("result", "删除成功");
            out.println(JsonUtil.toSuccessJsonResult(result));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "删除失败"));
        }
        out.flush();
        out.close();
    }
}
