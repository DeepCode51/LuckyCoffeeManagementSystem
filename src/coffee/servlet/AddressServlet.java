package coffee.servlet;

import coffee.bean.AddressDO;
import coffee.bean.UserDO;
import coffee.dao.AddressDao;
import coffee.util.JsonUtil;
import coffee.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yu
 * @Description: 个人地址管理
 * @date 2022/4/17 12:50
 */
public class AddressServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        String type = request.getParameter("type") == null ? "type" : request.getParameter("type");
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String phone = request.getParameter("phone") == null ? "" : request.getParameter("phone");
        String province = request.getParameter("province") == null ? "" : request.getParameter("province");
        String city = request.getParameter("city") == null ? "" : request.getParameter("city");
        String district = request.getParameter("district") == null ? "" : request.getParameter("district");
        String address = request.getParameter("address") == null ? "" : request.getParameter("address");
        String defaultAddr = request.getParameter("defaultAddr") == null ? "" : request.getParameter("defaultAddr");
        String pageIndex = request.getParameter("page") == null ? "" : request.getParameter("page");
        String pageSize = request.getParameter("limit") == null ? "" : request.getParameter("limit");

        AddressDO addressDO = new AddressDO();
        addressDO.setId(id);
        addressDO.setName(name);
        addressDO.setPhone(phone);
        addressDO.setProvince(province);
        addressDO.setCity(city);
        addressDO.setDistrict(district);
        addressDO.setAddress(address);
        addressDO.setUserId(userDO.getId());
        addressDO.setDefaultAddr(defaultAddr);

        AddressDao addressDao = new AddressDao();
        if("list".equals(type)){
            List<AddressDO> list = addressDao.list(userDO.getId(),"","");
            int total = list.size();
            if (!StringUtil.isNullOrEmpty(pageIndex) && !StringUtil.isNullOrEmpty(pageSize)) {
                list = addressDao.list(userDO.getId(),pageIndex,pageSize);
            }
            out.println(JsonUtil.toSuccessLayUiJsonResult(list, total));
        }else if("add".equals(type)){
            int result = addressDao.add(addressDO);
            if(result > 0){
                out.println(JsonUtil.toSuccessJsonResult(result));
            }else {
                out.println(JsonUtil.toFailJsonResult(-1,"添加失败"));
            }
        }else if("save".equals(type)){
            int result = addressDao.save(addressDO);
            if(result > 0){
                out.println(JsonUtil.toSuccessJsonResult(result));
            }else {
                out.println(JsonUtil.toFailJsonResult(-1,"保存失败"));
            }
        }else if("get".equals(type)){
            out.println(JsonUtil.toSuccessJsonResult(addressDao.getById(id)));
        }else if("del".equals(type)){
            int result = addressDao.delete(id);
            if(result > 0){
                out.println(JsonUtil.toSuccessJsonResult(result));
            }else {
                out.println(JsonUtil.toFailJsonResult(-1,"删除失败"));
            }
        }else if("setAddr".equals(type)){
            if("是".equals(defaultAddr)){
                int result = addressDao.cancelAddr();
                if(result > 0){
                    int result2 = addressDao.setAddr(id,defaultAddr);
                    if(result2 > 0){
                        out.println(JsonUtil.toSuccessJsonResult("设置成功"));
                    }else {
                        out.println(JsonUtil.toFailJsonResult(-1,"设置失败"));
                    }
                }else {
                    out.println(JsonUtil.toFailJsonResult(-1,"设置失败"));
                }
            }else if("否".equals(defaultAddr)){
                int result = addressDao.setAddr(id,defaultAddr);
                if(result > 0){
                    out.println(JsonUtil.toSuccessJsonResult(result));
                }else {
                    out.println(JsonUtil.toFailJsonResult(-1,"设置失败"));
                }
            }
        }else if("defaultAddr".equals(type)){
            AddressDO addressDO1 = addressDao.getDefaultAddr(userDO.getId());
            if(addressDO1 != null){
                out.println(JsonUtil.toSuccessJsonResult(addressDO1));
            }else {
                out.println(JsonUtil.toFailJsonResult(-1,"未查到相关记录"));
            }

        }
        out.flush();
        out.close();
    }
}
