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
<form method="post" action="${pageContext.request.contextPath}/import-result" enctype="multipart/form-data">
    <table>

        <tr>
            <td>GSOD file</td>
            <td><input name="gsodFile" type="file"/></td>
        </tr>


    </table>
</form>
</body>
</html>
