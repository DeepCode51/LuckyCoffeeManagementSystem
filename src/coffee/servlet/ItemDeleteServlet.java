package coffee.servlet;

import coffee.dao.ItemDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 删除商品
 * @date 2021/7/1 23:23
 */
public class ItemDeleteServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        ItemDao itemDao = new ItemDao();
        int result = itemDao.delete(id);
        if (result > 0) {
            out.println(JsonUtil.toSuccessJsonResult("删除成功"));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "删除失败"));
        }
        out.flush();
        out.close();
    }
}
