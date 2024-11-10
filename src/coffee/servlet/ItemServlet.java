package coffee.servlet;

import coffee.bean.ItemDO;
import coffee.dao.ItemDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 获取商品详情
 * @date 2021/6/19 19:55
 */
public class ItemServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        ItemDao itemDao = new ItemDao();
        ItemDO itemDO = itemDao.getInfoById(id);
        if (itemDO != null) {
            out.println(JsonUtil.toSuccessJsonResult(itemDO));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "加载失败"));
        }
        out.flush();
        out.close();
    }
}
