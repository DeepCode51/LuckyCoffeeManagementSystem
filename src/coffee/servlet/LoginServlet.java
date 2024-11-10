package coffee.servlet;

import coffee.bean.UserDO;
import coffee.dao.UserDao;
import coffee.util.JsonUtil;
import coffee.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 登录
 * @date 2021/6/19 19:55
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String loginName = request.getParameter("loginName") == null ? "" : request.getParameter("loginName");
        String loginPwd = request.getParameter("loginPwd") == null ? "" : request.getParameter("loginPwd");
        if (StringUtil.checkNull(loginName) && StringUtil.checkNull(loginPwd)) {
            UserDao userDao = new UserDao();
            UserDO userDO = userDao.checkLogin(loginName, loginPwd);
            if (userDO != null) {
                //密码校验成功，允许登录，进入首页
                request.getSession().setAttribute("user", userDO);
                out.println(JsonUtil.toSuccessJsonResult("登录成功！"));
            } else {
                //虽然有可能是未注册，但出于安全考虑，用户名不对或密码不对都应是同样提示
                out.println(JsonUtil.toFailJsonResult(-1, "用户名或密码错误，请重新输入！"));
            }
        }
        out.flush();
        out.close();
    }
}
