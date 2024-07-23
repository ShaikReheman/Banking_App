<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Close Account</title>
</head>
<body>
    <h2>Close Account</h2>
    <form action="closeAccount" method="post">
        <label for="accountNo">Account Number:</label>
        <input type="text" id="accountNo" name="accountNo" required>
        <input type="submit" value="Close Account">
    </form>

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color:green;">${successMessage}</p>
    </c:if>
</body>
</html>
