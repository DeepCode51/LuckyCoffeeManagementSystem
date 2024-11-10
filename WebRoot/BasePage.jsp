<%@ page import="coffee.bean.UserDO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    //从session中获取当前登录的用户信息 userDO；session中的用户信息是在登录时保存的；注销时会清除用户信息
    UserDO userDO = (UserDO)request.getSession().getAttribute("user");
//    System.out.println("用户信息" + userDO);
    String role = "";
    if(userDO == null){
//        response.sendRedirect(basePath + "index.jsp");
    }else {
        role = "普通用户";
        //获取当前登录人的用户名
        String userName = userDO.getLoginName();
        //根据用户名判断当前登录人是否是管理员
        if("admin".equals(userName)){
            role = "管理员";
        }
    }

%>

