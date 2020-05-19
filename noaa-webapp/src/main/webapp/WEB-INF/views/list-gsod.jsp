<%@ page import="jo.edu.htu.noaa.GlobalSummaryOfDay" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: ٥/١٦/٢٠٢٠
  Time: ٠٤:٤٠ ص
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    List<GlobalSummaryOfDay> gsods = (List<GlobalSummaryOfDay>) request.getAttribute("gsod");
%>
<head>
    <title>GSOD</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/list-gsod">
    <div>
        <input type="text" value="usaf" name="usaf">
        <input type="text" value="wban" name="wban">
        <input type="date" value="begin" name="begin">
        <input type="date" value="end" name="end">
    </div>
    <table>
        <tr>
            <th>USAF-WBAN</th>
            <th>Temp</th>
            <th>Temp count</th>
            <th>WDSP</th>
            <th>WDSP Count</th>
            <th>Max Temp</th>
            <th>Min Temp</th>
        </tr>
        <%
            for (GlobalSummaryOfDay gs : gsods) {
        %>
        <tr>
            <td><%gs.getStn();%> -<%gs.getWban();%>
            </td>
            <td><%gs.getTemperature();%></td>
            <td><%gs.getTempCount();%></td>
            <td><%gs.getWindSpeed();%></td>
            <td><%gs.getWindSpedCount();%></td>
            <td><%gs.getMaxTemp();%></td>
            <td><%gs.getMinTemp();%></td>
        </tr>
        <% }%>
    </table>
</form>
</body>
</html>
