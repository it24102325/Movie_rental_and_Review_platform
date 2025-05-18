<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Account - MovieRental</title>
    <link rel="stylesheet" href="assets/styles/edit-account.css">
</head>
<body>
<form action="update" method="post">
    <h2>Edit Account</h2>

    <div class="input-field">
        <input type="text" name="name" value="${sessionScope.user.name}" required>
    </div>

    <div class="input-field">
        <input type="text" name="uname" value="${sessionScope.user.username}" required>
    </div>

    <div class="input-field">
        <input type="password" name="pwd" placeholder="New Password" required>
    </div>

    <button type="submit">Update Account</button>
</form>
</body>
</html>
