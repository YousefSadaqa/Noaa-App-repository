<%@ page import="jo.edu.htu.noaa.Station" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: ٥/١٦/٢٠٢٠
  Time: ٠٤:٤١ ص
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    List<Station> stations = (List<Station>) request.getAttribute("gsod");
%>
<head>
    <title>Stations</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/list-stations">
    <div>
        <input type="text" value="usaf" name="usaf">
        <input type="text" value="wban" name="wban">
        <input type="number" value="lat" name="lat">
        <input type="number" value="lon" name="lon">
    </div>
    <table>
        <tr>
        <th>USAF-WBAN</th>
        <th>Name</th>
        <th>Country-State</th>
        <th>Location</th>
        </tr>
        <%
            for (Station st : stations) {
        %>
        <tr>
            <td><%st.getUsaf();%> - <%= st.getWban()%>
            </td>
            <td><%st.getName();%></td>
            <td><%
                st.getCtry();
                st.getState();
            %></td>
            <td><%
                st.getLatitude();
                st.getLongitude();
            %></td>

        </tr>
        <% }%>
    </table>
</form>
</body>
</html>
