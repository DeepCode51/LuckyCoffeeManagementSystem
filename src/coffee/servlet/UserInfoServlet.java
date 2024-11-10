package coffee.servlet;

import coffee.bean.UserDO;
import coffee.bean.UserPO;
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
 * @Description: 根据用户id获取用户信息
 * @date 2022-2-27 17:57
 */
public class UserInfoServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        UserPO userPO = new UserPO();
        PrintWriter out = response.getWriter();
        UserDao userDao = new UserDao();
        UserDO userDO = userDao.getUserById(id);
        if (userDO != null) {
            userPO.setId(userDO.getId());
            userPO.setLoginName(userDO.getLoginName());
            userPO.setName(userDO.getName());
            userPO.setAge(userDO.getAge());
            userPO.setSex(userDO.getSex());
            userPO.setMyDesc(userDO.getDesc());
            out.println(JsonUtil.toSuccessJsonResult(userPO));
        } else {
            out.println(JsonUtil.toSuccessJsonResult(userPO));
        }
        out.flush();
        out.close();
    }
}
