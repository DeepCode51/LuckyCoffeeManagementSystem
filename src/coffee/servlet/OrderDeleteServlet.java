package coffee.servlet;

import coffee.dao.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yu
 * @Description: 订单删除
 * @date 2021/7/4 13:06
 */
public class OrderDeleteServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        OrderDao orderDao = new OrderDao();
        //删除订单
        int result = orderDao.delete(id);
        if (result > 0) {
            //成功
            request.setAttribute("result", "删除成功");
        } else {
            request.setAttribute("result", "删除失败");
        }
        request.setAttribute("page", "views/orders.jsp");
    }
}
