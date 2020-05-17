<%--
  Created by IntelliJ IDEA.
  User: Sadaqa
  Date: 5/17/2020
  Time: 2:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

</head>
<body>
<form action="${pageContext.request.contextPath}/add-user" method="post">
    <div class="container">
        <h1>Add User</h1>
        <p>Please fill in this form to create an User.</p>
        <hr>
        <label><b>Username</b></label>
        <input type="text" placeholder="username" name="username" required>

        <label><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" required>

        <label><b>Name</b></label>
        <input type="text" placeholder="Name" name="name" required>

        <select name="status">
            <option value="ACTIVE">ACTIVE></option>
            <option value="INACTIVE">INACTIVE></option>
        </select>
        <hr>

        <button type="submit" class="registerbtn">Add User</button>
    </div>

</form>
</body>
</html>

<style>
    * {
        box-sizing: border-box
    }

    /* Add padding to containers */
    .container {
        padding: 16px;
    }

    /* Full-width input fields */
    input[type=text], input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }

    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }

    /* Overwrite default styles of hr */
    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }

    /* Set a style for the submit/register button */
    .registerbtn {
        background-color: #D3D3D3;
        color: white;
        padding: 16px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        width: 100%;
        opacity: 0.9;
    }

    .registerbtn:hover {
        opacity: 1;
    }

    /* Add a blue text color to links */
    a {
        color: dodgerblue;
    }

    /* Set a grey background color and center the text of the "sign in" section */
    .signin {
        background-color: #f1f1f1;
        text-align: center;
    }
</style>