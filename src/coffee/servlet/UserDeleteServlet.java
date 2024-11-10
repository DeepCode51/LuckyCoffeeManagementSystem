package coffee.servlet;

import coffee.dao.UserDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 删除用户
 * @date 2021/6/19 20:01
 */
public class UserDeleteServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        PrintWriter out = response.getWriter();
        UserDao userDao = new UserDao();
        int result = userDao.delete(id);
        if (result > 0) {
            out.println(JsonUtil.toSuccessJsonResult("删除成功"));
        } else {
            out.println(JsonUtil.toSuccessJsonResult("删除失败"));
        }
        out.flush();
        out.close();
    }
}
