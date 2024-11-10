package coffee.servlet;

import coffee.bean.CartDO;
import coffee.dao.CartDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * @author yu
 * @Description: 购物车信息修改
 * @date 2022/4/18 11:27
 */
public class CartUpdateServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String num = request.getParameter("num") == null ? "" : request.getParameter("num");
        String price = request.getParameter("price") == null ? "" : request.getParameter("price");
        BigDecimal price2 = new BigDecimal(price);
        CartDao cartDao = new CartDao();
        CartDO cartDO = new CartDO();
        cartDO.setId(id);
        cartDO.setNum(num);
        BigDecimal num2 = new BigDecimal(num);
        cartDO.setTotalPrice(price2.multiply(num2).toString());
        int result = cartDao.save(cartDO);
        if(result > 0){
            out.println(JsonUtil.toSuccessJsonResult(result));
        }else {
            out.println(JsonUtil.toFailJsonResult(-1,"添加失败"));
        }
        out.flush();
        out.close();
    }
}
