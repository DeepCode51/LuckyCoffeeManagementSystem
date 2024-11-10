package coffee.servlet;

import coffee.bean.OrderDO;
import coffee.bean.UserDO;
import coffee.dao.OrderDao;
import coffee.util.JsonUtil;

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
 * @Description: 订单查询
 * @date 2021/6/19 19:55
 */
public class OrderServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String orderNo = request.getParameter("orderNo") == null ? "" : request.getParameter("orderNo");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String pageIndex = request.getParameter("pageIndex") == null ? "" : request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize") == null ? "" : request.getParameter("pageSize");
        UserDO userDO = (UserDO)request.getSession().getAttribute("user");
        //获取订单列表
        List<OrderDO> list = new ArrayList<OrderDO>();
        OrderDao orderDao = new OrderDao();
        if (userDO != null) {
            String userId2 = "";
            if(!"admin".equals(userDO.getLoginName())){
                userId2 = userDO.getId();
            }
            list = orderDao.list(userId2,"");
            out.println(JsonUtil.toSuccessLayUiJsonResult(list,list.size()));
        }else {
            out.println(JsonUtil.toFailJsonResult(-1,"请先登录"));
        }
        out.flush();
        out.close();
    }
}
