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
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @Description: 用户管理列表，查询所有用户信息
 * @date 2022-3-5 12:17
 */
public class UsersServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String sex = request.getParameter("sex") == null ? "" : request.getParameter("sex");
        String startAge = request.getParameter("startAge") == null ? "" : request.getParameter("startAge");
        String endAge = request.getParameter("endAge") == null ? "" : request.getParameter("endAge");
        String keyWords = request.getParameter("keyWords") == null ? "" : request.getParameter("keyWords");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");
        List<UserDO> list = new ArrayList<UserDO>();
        UserDao userDao = new UserDao();
        list = userDao.listUserBuyInfo(sex, name, startAge, endAge, keyWords, "", "");
        int total = list.size();
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            list = userDao.listUserBuyInfo(sex, name, startAge, endAge, keyWords, pageIndex, pageSize);
        }
        out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        out.flush();
        out.close();
    }
}
