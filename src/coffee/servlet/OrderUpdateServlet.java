package coffee.servlet;

import coffee.bean.UserDO;
import coffee.dao.OrderDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 订单信息修改
 * @date 2021/7/4 13:02
 */
public class OrderUpdateServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        if (userDO != null) {
            OrderDao orderDao = new OrderDao();
            //完成订单
//            int result = orderDao.finish(id);
            int result = orderDao.save(id,state);
            if (result > 0) {
                //成功
                out.println(JsonUtil.toSuccessJsonResult("完成订单"));
            } else {
                out.println(JsonUtil.toFailJsonResult(-2, "操作失败请重试"));
            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "请先登录"));
        }
        out.flush();
        out.close();
    }
}
