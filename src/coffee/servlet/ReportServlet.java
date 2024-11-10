package coffee.servlet;

import coffee.dao.ReportDao;
import coffee.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author yu
 * @Description:
 * @date 2022/3/6 12:02
 */
public class ReportServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String startTime = request.getParameter("startTime") == null ? "" : request.getParameter("startTime");
        String endTime = request.getParameter("endTime") == null ? "" : request.getParameter("endTime");
        String type = request.getParameter("type") == null ? "" : request.getParameter("type");
        ReportDao reportDao = new ReportDao();
        if("area".equals(type)){
            //按销售商品产地统计
            Map<String,String> map = reportDao.getByArea(startTime,endTime);
            out.println(JsonUtil.toSuccessJsonResult(map));
        }else if("packet".equals(type)){
            //按销售商品包装类型统计
            Map<String,String> map = reportDao.getByPacket(startTime,endTime);
            out.println(JsonUtil.toSuccessJsonResult(map));
        }else if("age".equals(type)){
            //按购买用户年龄统计
            Map<String,String> map = reportDao.getByAge(startTime,endTime);
            out.println(JsonUtil.toSuccessJsonResult(map));
        }else if("month".equals(type)){
            //按月销量统计
            Map<String,String> map = reportDao.getByMonth(startTime,endTime);
            out.println(JsonUtil.toSuccessJsonResult(map));
        }else if("itemSales".equals(type)){
            //按月销量统计
            List<Map<String,Object>> list = reportDao.getSalesByItems();
            out.println(JsonUtil.toSuccessLayUiJsonResult(list,list.size()));
        }else if("sales".equals(type)){
            //按月销量统计
            List<Map<String,Object>> list = reportDao.getSales();
            out.println(JsonUtil.toSuccessLayUiJsonResult(list,list.size()));
        }else if("yearSales".equals(type)){
            //按年销量统计
            List<Map<String,Object>> list = reportDao.getSalesByYear();
            out.println(JsonUtil.toSuccessLayUiJsonResult(list,list.size()));
        }
        out.flush();
        out.close();
    }
}
