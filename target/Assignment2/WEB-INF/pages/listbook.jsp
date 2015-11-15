<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Book List</title>
    <link href="resources/styles.css" rel="stylesheet">
</head>
<body>

<c:if test="${not empty books}">

<table>
    <tr>
        <td>Book Name</td>
        <td>Author</td>
        <td>ISBN</td>
        <td>Price</td>
    </tr>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.name}</td>
            <td>${book.author}</td>
            <td>${book.isbn}</td>
            <td>${book.price}</td>
        </tr>
    </c:forEach>
</table>
</c:if>

<br>
  <a href="<c:url value="/addbook" />" class="likeabutton">Add Book</a>


</body>
</html>
