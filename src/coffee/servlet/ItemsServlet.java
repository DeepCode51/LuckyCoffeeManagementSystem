package coffee.servlet;

import coffee.bean.ItemDO;
import coffee.dao.ItemDao;
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
 * @Description: 商品管理列表，查询所有商品信息
 * @date 2021/6/19 19:55
 */
public class ItemsServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String brand = request.getParameter("brand") == null ? "" : request.getParameter("brand");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String place = request.getParameter("place") == null ? "" : request.getParameter("place");
        String packet = request.getParameter("packet") == null ? "" : request.getParameter("packet");
        String keyWords = request.getParameter("keyWords") == null ? "" : request.getParameter("keyWords");
        String state = request.getParameter("state") == null ? "" : request.getParameter("state");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");
        List<ItemDO> list = new ArrayList<ItemDO>();
        ItemDao itemDao = new ItemDao();
        list = itemDao.listItemInfo(brand, name, place, packet, state, keyWords, "", "");
        int total = list.size();
        if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
            list = itemDao.listItemInfo(brand, name, place, packet, state, keyWords, pageIndex, pageSize);
        }
        out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        out.flush();
        out.close();
    }
}
