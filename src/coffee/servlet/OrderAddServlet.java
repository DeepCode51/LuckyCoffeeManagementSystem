package coffee.servlet;

import coffee.bean.CartDO;
import coffee.bean.OrderDO;
import coffee.bean.UserDO;
import coffee.dao.CartDao;
import coffee.dao.OrderDao;
import coffee.util.JsonUtil;
import coffee.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yu
 * @Description: 订单生成
 * @date 2021/7/4 12:40
 */
public class OrderAddServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String fullAddr = request.getParameter("fullAddr") == null ? "" : request.getParameter("fullAddr");

        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        PrintWriter out = response.getWriter();
        if (userDO != null) {
            //获取用户ID
            String userId = userDO.getId();
            CartDao cartDao = new CartDao();
            //获取当前用户的购物车所有记录
            List<CartDO> list = cartDao.listOrderItem(userId);
            //购物车有记录，则生成订单，并将购物车记录与之关联
            if (list.size() > 0) {
                //提交订单，先生成订单记录，再更新购物车对应记录
                OrderDao orderDao = new OrderDao();
                //生成订单记录,查询当前订单总数，+1变为订单号
                String orderNo = orderDao.getOrderNo();
                OrderDO orderDO = new OrderDO();
                orderDO.setOrderNo(orderNo);
                //购物车商品总价
                orderDO.setTotalPrice(cartDao.getTotalPrice(userId));
                orderDO.setUserId(userId);
                orderDO.setUserName(name);
                orderDO.setState("待支付");
                orderDO.setPhone(phone);
                orderDO.setAddress(fullAddr);
                String id = orderDao.add(orderDO);
                if (!StringUtil.isNullOrEmpty(id)) {
                    //生成订单成功，则修改提交的购物车记录，变更为已提交，并关联订单编号
                    int result = cartDao.commit(list, id);
                    if (result > 0) {
                        //更新订单关联的购物车表中的商品信息，防止加入购物车后，商品信息有变更过
                        cartDao.updateCart(id);
                        //成功
                        out.println(JsonUtil.toSuccessJsonResult(result));
                    } else {
                        out.println(JsonUtil.toFailJsonResult(-1, "提交失败"));
                    }
                } else {
                    out.println(JsonUtil.toFailJsonResult(-3, "订单生成失败"));
                }
            } else {
                out.println(JsonUtil.toFailJsonResult(-4, "购物车无记录"));

            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-2, "请先登录"));
        }
    }

}
