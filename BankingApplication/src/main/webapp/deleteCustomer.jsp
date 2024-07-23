<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Customer</title>
</head>
<body>
    <h2>Delete Customer</h2>
    <form action="deleteCustomer" method="post">
        <label for="customerId">Customer ID:</label>
        <input type="text" id="customerId" name="customerId" required>
        <input type="submit" value="Delete Customer">
    </form>

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color:green;">${successMessage}</p>
    </c:if>
</body>
</html>
