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

    .button {
        background-color: #D3D3D3;
        width: 210px;
        height: 100px;
        border: none;
        color: black;
        border: solid 1px black;
        border-radius: 10px;
        padding: 30px 60px;
        text-align: center;
        text-decoration: none;
        display: inline;
        font-size: 16px;
        margin: 31px 22px;
        cursor: pointer;
    }
</style>
</head>
<body>
<form>
    <h2>Users Management</h2>
        <%
    List<User> users = (List<User>) request.getAttribute("users");
%>
    <a class="button" href="add-user.jsp">Add User</a>
    <form>
        <table>
            <tr>
                <th>Username</th>
                <th>Name</th>
                <th>E-mail</th>
                <th>Status</th>
                <th></th>
                <%
                    for (User user : users) {
                %>
            </tr>

            <tr>
                <td name=<%user.getUsername();%>
                        <%user.getUsername();%>
                            </td>
                <td><%user.getName();%></td>
                <td><%
                    user.getEmail();
                %></td>
                <td>
                    <select name="status">
                        <option value="<%user.getUsername();%>"><input name=<%user.getStatus();%></option>
                    </select>
                </td>
            </tr>
            <% }%>
        </table>
    </form>
</body>
</html>
