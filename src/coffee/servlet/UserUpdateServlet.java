package coffee.servlet;

import coffee.bean.UserDO;
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
 * @Description: 个人信息修改
 * @date 2021/6/19 19:59
 */
public class UserUpdateServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String age = request.getParameter("age") == null ? "" : request.getParameter("age");
        String sex = request.getParameter("sex") == null ? "" : request.getParameter("sex");
        String desc = request.getParameter("myDesc") == null ? "" : request.getParameter("myDesc");
        PrintWriter out = response.getWriter();
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setName(name);
        userDO.setAge(age);
        userDO.setSex(sex);
        userDO.setDesc(desc);
        UserDao userDao = new UserDao();
        int result = userDao.save(userDO);
        if (result > 0) {
            out.println(JsonUtil.toSuccessJsonResult("保存成功"));
            ///更新session中的个人信息
            UserDO userDO1 = (UserDO) request.getSession().getAttribute("user");
            if (userDO1.getId().equals(id)) {
                request.getSession().setAttribute("user", userDao.getUserById(id));
            }
        } else {
            request.setAttribute("result", "修改失败");
            out.println(JsonUtil.toSuccessJsonResult("保存失败"));
        }
        out.flush();
        out.close();
    }
}
