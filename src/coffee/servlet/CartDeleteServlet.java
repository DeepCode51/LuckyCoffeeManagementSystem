package coffee.servlet;

import coffee.dao.CartDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 购物车商品删除
 * @date 2021/7/4 0:00
 */
public class CartDeleteServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        CartDao cartDao = new CartDao();
        int result = cartDao.delete(id);
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
