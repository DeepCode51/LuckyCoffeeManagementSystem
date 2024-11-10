package coffee.servlet;

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
 * @Description:商品上架下架
 * @date 2022/3/27 17:46
 */
public class ItemPutServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        ItemDao itemDao = new ItemDao();
        int result = itemDao.itemPut(id,state);
        if (result > 0) {
            out.println(JsonUtil.toSuccessJsonResult("保存成功"));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "保存失败"));
        }
        out.flush();
        out.close();
    }
}
