<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="icon" type="image/x-icon" href="images/fav_logo.ico">

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="images/fav_logo.ico">
    <title>Регистрация</title>
</head>
<body>
<h2>Регистрация</h2>
<form action="registration-servlet" method="POST">

    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Зарегистрироваться">
</form>
</body>
</html>

