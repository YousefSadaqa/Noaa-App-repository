<%@ page import="jo.edu.htu.noaa.User" %>
<%@ page import="java.util.List" %>
<%@ page import="jo.edu.htu.noaa.DisableUser" %>
<%@ page import="jo.edu.htu.noaa.web.servlets.DisableUserServlets" %>
<%@ page import="jo.edu.htu.noaa.users.DisableUserHandler" %>
<%@ page import="jo.edu.htu.DBUsersRepository" %>
<%@ page import="jo.edu.htu.UsersRepository" %><%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: 5/17/2020
  Time: 2:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
</style>
</head>
<body>

<h2>Users Management</h2>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>
<table>
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>E-mail</th>
        <th>Status</th>
        <th></th>
    </tr>
    <%
        for (User user : users) {
    %>
    <tr>
        <td name="username"><%user.getUsername();%>
        </td>
        <td><%user.getName();%></td>
        <td><%
            user.getEmail();
        %></td>
        <td>
            <select name="status">
                <option><%user.getStatus();%></option>
            </select>
        </td>
    </tr>
    <% }%>
</table>

</body>
</html>
