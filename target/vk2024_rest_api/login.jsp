<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="images/fav_logo.ico">
    <title>Вход</title>
</head>
<body>
<h2>Вход</h2>
<form action="login-servlet" method="post">
    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Войти">
</form>
</body>
</html>

