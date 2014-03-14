<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Performance Test</title>
</head>

<body>
Hi ${name }!
<c:if test="${list.size() > 0}">
<ul>
    <c:forEach var="item" items="list">
    <li>${item}</li>
    </c:forEach>
</ul>
</c:if>
</body>
</html>
