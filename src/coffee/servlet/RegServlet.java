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
 * @Description: 注册
 * @date 2021/6/19 19:55
 */
public class RegServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String loginName = request.getParameter("loginName") == null ? "" : request.getParameter("loginName");
        String loginPwd = request.getParameter("loginPwd") == null ? "" : request.getParameter("loginPwd");
        String name = request.getParameter("nickname") == null ? "" : request.getParameter("nickname");

        if (StringUtil.checkNull(loginName) && StringUtil.checkNull(loginPwd) && StringUtil.checkNull(name)) {
            UserDao userDao = new UserDao();
            UserDO userDO = userDao.getUserByName(loginName);
            if (userDO != null) {
                //用户已存在
                out.println(JsonUtil.toFailJsonResult(-1, "用户名已存在"));
            } else {
                userDO = new UserDO();
                userDO.setLoginName(loginName);
                userDO.setLoginPwd(loginPwd);
                userDO.setName(name);
                int result = userDao.addUser(userDO);
                out.println(JsonUtil.toSuccessJsonResult(result));
            }
        } else {
            out.println("信息不完整");
        }
        out.flush();
        out.close();
    }
}
