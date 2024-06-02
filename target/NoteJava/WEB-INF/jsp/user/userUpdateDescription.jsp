<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update Description</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        .error-message {
            color: #D8000C;
            background-color: #FFD2D2;
            padding: 10px;
            border-radius: 5px;
            margin: 20px auto;
            width: 50%;
            font-weight: bold;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 50%;
            margin: 20px auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"], input[type="email"], input[type="tel"], input[type="date"], input[type="submit"] {
            padding: 8px;
            width: 95%;
            margin-bottom: 20px;
        }

        button {
            padding: 10px 20px;
            background-color: #5C7AEA;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 30%;
        }

        button:hover {
            background-color: #3f5bcc;
        }

        a {
            text-decoration: none;
        }
    </style>
</head>
<body>

<c:if test="${not empty requestScope.errors}">
    <div class="error-message">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.typeError}: </span><span>${error.typeError.toString()}</span><br/>
        </c:forEach>
    </div>
</c:if>

<form method="post" action="<c:url value='/user/update/description'/>">
    <label for="login">Login:</label>
    <input type="text" id="login" name="login" value="${sessionScope.user.login()}" required>

    <label for="username">User Name:</label>
    <input type="text" id="username" name="userName" value="${sessionScope.user.userName()}" required>

    <label for="userSurname">User Surname:</label>
    <input type="text" id="userSurname" name="userSurname" value="${sessionScope.user.userSurname()}" required>

    <label for="birthdayDate">Birthday Date:</label>
    <input type="date" id="birthdayDate" name="birthdayDate" value="${sessionScope.user.birthdayDate()}" required>

    <input type="submit" value="Change">

    <a href="<c:url value='/user/default'/>">
        <button type="button">On default page</button>
    </a>
</form>

</body>
</html>