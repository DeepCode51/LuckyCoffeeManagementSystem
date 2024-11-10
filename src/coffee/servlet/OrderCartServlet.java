package coffee.servlet;

import coffee.bean.CartDO;
import coffee.bean.UserDO;
import coffee.dao.CartDao;
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
 * @Description: 订单及关联的商品
 * @date 2022-3-5 14:59
 */
public class OrderCartServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String orderId = request.getParameter("orderId") == null ? "" : request.getParameter("orderId");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        //获取订单列表
        List<CartDO> list = new ArrayList<CartDO>();
        CartDao cartDao = new CartDao();
        if (userDO != null) {
            list = cartDao.listByOrderId(orderId, "", "");
            int total = list.size();
            if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
                list = cartDao.listByOrderId(orderId, pageIndex, pageSize);
            }
            out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "请先登录"));
        }
        out.flush();
        out.close();
    }
}
