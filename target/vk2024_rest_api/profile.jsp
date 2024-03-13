<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*" %>
<link rel="icon" type="image/x-icon" href="images/fav_logo.ico">

<%
    HttpSession currentSession = request.getSession(false);

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="images/fav_logo.ico">
    <title>Профиль пользователя</title>
</head>
<body>
<h2>Профиль пользователя</h2>

<h3>Изменение имени пользователя</h3>
<form id="changeUsernameForm">
    <label for="newUsername">Новое имя пользователя:</label>
    <input type="text" id="newUsername" name="newUsername" required><br><br>

    <input type="hidden" id="oldUsername" name="oldUsername" value="<%= currentSession.getAttribute("username") %>">

    <button type="button" onclick="changeUsername()">Изменить имя</button>
</form>

<h3>Изменение пароля пользователя</h3>
<form id="changePasswordForm">
    <label for="oldPassword">Текущий пароль:</label>
    <input type="password" id="oldPassword" name="oldPassword" required><br><br>

    <label for="newPassword">Новый пароль:</label>
    <input type="password" id="newPassword" name="newPassword" required><br><br>

    <input type="hidden" id="username" name="username" value="<%= currentSession.getAttribute("username") %>">

    <button type="button" onclick="changePassword()">Изменить пароль</button>
</form>

<h3>Удаление пользователя</h3>
<form id="deleteUserForm">
    <input type="hidden" id="username" name="username" value="<%= currentSession.getAttribute("username") %>">

    <button type="button" onclick="deleteUser()">Удалить пользователя</button>
</form>

<script>
    function changeUsername() {
        var formData = new FormData(document.getElementById("changeUsernameForm"));
        fetch('rename-user', {
            method: 'PUT',
            body: formData
        }).then(response => {
            if (response.ok) {
                alert("Имя пользователя успешно изменено");
                location.reload();
            } else {
                alert("Не удалось изменить имя пользователя");
            }
        });
    }

    function changePassword() {
        var formData = new FormData(document.getElementById("changePasswordForm"));
        fetch('change-password', {
            method: 'PUT',
            body: formData
        }).then(response => {
            if (response.ok) {
                alert("Пароль пользователя успешно изменен");
                location.reload();
            } else {
                alert("Не удалось изменить пароль пользователя");
            }
        });
    }

    function deleteUser() {
        var formData = new FormData(document.getElementById("deleteUserForm"));
        fetch('delete-user', {
            method: 'DELETE',
            body: formData
        }).then(response => {
            if (response.ok) {
                alert("Пользователь успешно удален");
                location.href = 'index.jsp';
            } else {
                alert("Не удалось удалить пользователя");
            }
        });
    }
</script>
</body>
</html>
