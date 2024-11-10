package coffee.servlet;

import coffee.bean.ItemDO;
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
 * @Description: 修改商品
 * @date 2021/7/1 23:23
 */
public class ItemUpdateServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String brand = request.getParameter("brand") == null ? "" : request.getParameter("brand");
        String price = request.getParameter("price") == null ? "" : request.getParameter("price");
        String total = request.getParameter("total") == null ? "" : request.getParameter("total");
        String desc = request.getParameter("desc") == null ? "" : request.getParameter("desc");
        String place = request.getParameter("place") == null ? "" : request.getParameter("place");
        String weight = request.getParameter("weight") == null ? "" : request.getParameter("weight");
        String pic = request.getParameter("pic") == null ? "" : request.getParameter("pic");
        String packet = request.getParameter("packet") == null ? "" : request.getParameter("packet");

        ItemDO itemDO = new ItemDO();
        itemDO.setId(id);
        itemDO.setName(name);
        itemDO.setBrand(brand);
        itemDO.setPrice(price);
        itemDO.setTotal(total);
        itemDO.setDesc(desc);
        itemDO.setPlace(place);
        itemDO.setWeight(weight);
        itemDO.setPic(pic);
        itemDO.setPacket(packet);
        //修改商品
        ItemDao itemDao = new ItemDao();
        int result = itemDao.save(itemDO);
        if (result > 0) {
            out.println(JsonUtil.toSuccessJsonResult("保存成功"));
        } else {
            out.println(JsonUtil.toFailJsonResult(-1, "保存失败"));
        }
        out.flush();
        out.close();
    }
}
