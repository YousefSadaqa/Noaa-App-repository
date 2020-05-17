<%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: ٥/١٦/٢٠٢٠
  Time: ٠٤:٤٥ ص
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import GSOD file</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/import-gosd" enctype="multipart/form-data">
    <table>
        <tr>
            <td>BIS file</td>
            <td><input name="gsodFile" type="file"/></td>
        </tr>
        <tr>
            <td>Date</td>
            <td><input name="fileDate" type="date"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button>Import</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
