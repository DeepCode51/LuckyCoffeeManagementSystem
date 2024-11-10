package coffee.servlet;

import coffee.bean.CartDO;
import coffee.bean.UserDO;
import coffee.dao.CartDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yu
 * @Description: 购物车商品添加
 * @date 2021/7/4 0:00
 */
public class CartAddServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        PrintWriter out = response.getWriter();
        if (userDO != null) {
            //获取用户ID
            String userId = userDO.getId();
            String itemId = request.getParameter("itemId") == null ? "" : request.getParameter("itemId");
            String name = request.getParameter("name") == null ? "" : request.getParameter("name");
            String pic = request.getParameter("pic") == null ? "" : request.getParameter("pic");
            String price = request.getParameter("price") == null ? "" : request.getParameter("price");
//            name = StringUtil.charConvert(name);
            int result = 0;
            CartDao cartDao = new CartDao();
            //先查询本次加入购物车的商品，在购物车中是否已存在，若已存在则只增加该商品数量和小计，若不存在则新增该商品
            List<CartDO> list = cartDao.listByItemId(userId, itemId);
            if (list.size() > 0) {
                //购物车中已存在，修改数量和小计
                String id = list.get(0).getId();
                result = cartDao.save(price, id);
            } else {
                //购物车中不存在，则添加
                BigDecimal price2 = new BigDecimal(price);
                CartDO cartDO = new CartDO();
                cartDO.setItemId(itemId);
                cartDO.setItemName(name);
                cartDO.setItemPic(pic);
                cartDO.setPrice(price);
                String num = "1";
                cartDO.setNum(num);
                BigDecimal num2 = new BigDecimal(num);
                cartDO.setTotalPrice(price2.multiply(num2).toString());
                cartDO.setUserId(userId);
                //添加
                result = cartDao.add(cartDO);
            }
            if (result > 0) {
                //成功
                out.println(JsonUtil.toSuccessJsonResult(result));
            } else {
                out.println(JsonUtil.toFailJsonResult(-1, "添加失败"));
            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-2, "请先登录"));
        }
    }
}
