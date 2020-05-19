<%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: 5/18/2020
  Time: 4:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<% Cookie[] cookies = request.getCookies();%>

<body>
<% for (int i = 0; i < cookies.length; i++)
%>
<label>New Records : <%cookies[0].getValue();%></label>
<label>Total Records in Database <%cookies[3].getValue();%></label>
<label>Total Records Parsed: <%cookies[1].getValue();%></label>
<label>Updated Records :<%cookies[2].getValue();%></label>
<%
    Cookie cookie0 = new Cookie("updatedRecords", "");
    Cookie cookie1 = new Cookie("totalInDatabase", "");
    Cookie cookie2 = new Cookie("newRecord", "");
    Cookie cookie3 = new Cookie("totalParsed", "");
    cookie0.setMaxAge(0);
    cookie1.setMaxAge(0);
    cookie2.setMaxAge(2);
    cookie3.setMaxAge(3);
    response.addCookie(cookie0);
    response.addCookie(cookie1);
    response.addCookie(cookie2);
    response.addCookie(cookie3);


%>
</body>
</html>
