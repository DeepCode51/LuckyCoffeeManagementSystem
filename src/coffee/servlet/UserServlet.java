package coffee.servlet;

import coffee.bean.UserDO;
import coffee.bean.UserPO;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yu
 * @Description: 获取当前用户信息
 * @date 2022-2-27 17:57
 */
public class UserServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        UserPO userPO = new UserPO();
        PrintWriter out = response.getWriter();
        if (userDO != null) {
            String role = "普通用户";
            //获取当前登录人的用户名
            String userName = userDO.getLoginName();
            //根据用户名判断当前登录人是否是管理员
            if ("admin".equals(userName)) {
                role = "管理员";
            }
            userPO.setId(userDO.getId());
            userPO.setLoginName(userDO.getLoginName());
            userPO.setName(userDO.getName());
            userPO.setAge(userDO.getAge());
            userPO.setSex(userDO.getSex());
            userPO.setMyDesc(userDO.getDesc());
            userPO.setRole(role);
            out.println(JsonUtil.toSuccessJsonResult(userPO));
        } else {
            out.println(JsonUtil.toSuccessJsonResult(userPO));
        }
        out.flush();
        out.close();
    }
}
