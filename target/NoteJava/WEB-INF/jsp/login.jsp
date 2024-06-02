<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        .error {
            color: #d80000;
            background-color: #bcdbf8;
            padding: 10px;
            border-radius: 5px;
            margin: 20px auto;
            width: 50%;
            font-weight: bold;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        form {
            background-color: #ffffff;
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

        input[type="text"], input[type="password"], input[type="submit"] {
            padding: 8px;
            width: 95%;
            margin-bottom: 20px;
        }

        button {
            padding: 10px 20px;
            background-color: #aaea5c;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            display: block;
            margin: 10px auto;
            width: 80%;
        }

        button:hover {
            background-color: #296c1e;
        }

        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<c:if test="${not empty requestScope.error}">
    <div class="error">
        <span>${requestScope.error.toString()}</span>
    </div>
</c:if>
<form method="post" action="<c:url value='/login'/>">
    <label for="login">Логин</label>
    <input type="text" id="login" name="login" required>

    <label for="userPassword">Пароль</label>
    <input type="password" id="userPassword" name="userPassword" required>

    <input type="submit" value="Войти">
    <a href="<c:url value='/registration'/>">
        <button type="button">Регистрация</button>
    </a>
</form>
</body>
</html>