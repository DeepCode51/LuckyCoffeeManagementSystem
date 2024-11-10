package coffee.servlet;

import coffee.bean.AddressDO;
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
 * @Description: 获取购物车信息
 * @date 2022-2-27 20:12
 */
public class CartServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type") == null ? "" : request.getParameter("type");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        CartDao cartDao = new CartDao();
        if (userDO != null) {
            if("1".equals(type)){
                List<CartDO> list = cartDao.list(userDO.getId(),"","");
                int total = list.size();
                if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
                    list = cartDao.list(userDO.getId(),pageIndex,pageSize);
                }
                out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
            }else {
//                List<CartDO> list = cartDao.list(userDO.getId());
                List<CartDO> list = cartDao.list(userDO.getId(),"","");
                out.println(JsonUtil.toSuccessJsonResult(list, list.size()));
            }
        } else {
            out.println(JsonUtil.toFailJsonResult(-1,"加载失败"));
        }
        out.flush();
        out.close();
    }
}
