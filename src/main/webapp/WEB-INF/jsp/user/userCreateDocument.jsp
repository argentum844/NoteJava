<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создание Текстового Документа</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 50%;
            margin: 20px auto;
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
    </style>
</head>
<body>

<h2>Введите текст для вашего документа:</h2>

<form action="<c:url value='/user/create/document'/>" method="post">
    <textarea name="documentText" rows="100" cols="100"></textarea><br>
    <label for="title">Название документа:</label>
    <input type="text" id="title" name="title" value="" required>
    <input type="submit" value="Сохранить"><br>
    <a href="<c:url value='/user/default'/>">
        <button type="button">On default page</button>
    </a>
</form>
</body>
</html>
