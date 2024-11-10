package coffee.servlet;

import coffee.bean.OrderDO;
import coffee.bean.UserDO;
import coffee.dao.OrderDao;
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
 * @Description: 订单列表
 * @date 2022-3-5 14:59
 */
public class OrdersServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String orderNo = request.getParameter("orderNo") == null ? "" : request.getParameter("orderNo");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        String keyWords = request.getParameter("keyWords") == null ? "" : request.getParameter("keyWords");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        //获取订单列表
        List<OrderDO> list = new ArrayList<OrderDO>();
        OrderDao orderDao = new OrderDao();
        if (userDO != null) {
            String userId2 = "";
            if (!"admin".equals(userDO.getLoginName())) {
                userId2 = userDO.getId();
            }
            list = orderDao.listOrder(userId2, orderNo, state, keyWords, "", "");
            int total = list.size();
            if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
                list = orderDao.listOrder(userId2, orderNo, state, keyWords, pageIndex, pageSize);
            }
            out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "请先登录"));
        }
        out.flush();
        out.close();
    }
}
