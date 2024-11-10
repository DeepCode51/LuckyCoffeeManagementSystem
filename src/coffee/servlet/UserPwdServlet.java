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
 * @Description: 用户密码修改
 * @date 2022/4/10 17:01
 */
public class UserPwdServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String type = request.getParameter("type") == null ? "" : request.getParameter("type");
        PrintWriter out = response.getWriter();
        UserDao userDao = new UserDao();
        if("update".equals(type)){
            //修改密码
            String oldPwd = request.getParameter("oldPwd") == null ? "" : request.getParameter("oldPwd");
            String newPwd = request.getParameter("newPwd") == null ? "" : request.getParameter("newPwd");
            UserDO userDO = (UserDO) request.getSession().getAttribute("user");
            UserDO userDO1 = userDao.checkLogin(userDO.getLoginName(),oldPwd);
            if(userDO1 == null){
                out.println(JsonUtil.toFailJsonResult(-1,"原密码错误！"));
            }else {
                userDO.setLoginPwd(newPwd);
                int result = userDao.updatePwd(userDO);
                out.println(JsonUtil.toSuccessJsonResult(result));
            }
        }else if("reset".equals(type)){
            //重置密码
            String userId = request.getParameter("id") == null ? "" : request.getParameter("id");
            UserDO userDO = new UserDO();
            userDO.setId(userId);
            userDO.setLoginPwd("1");
            int result = userDao.updatePwd(userDO);
            out.println(JsonUtil.toSuccessJsonResult(result));
        }
    }
}
