<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Score</title>

    <link href="resources/styles.css" rel="stylesheet">

    <script language="JavaScript">

        function checkValidation(){

            var name=document.getElementById("name").value;
            var isbn=document.getElementById("isbn").value;
            var author=document.getElementById("author").value;
            var price=document.getElementById("price").value;



             if(name.length==0 || isbn.length==0 || author.length==0 ||  isNaN(price)){

                alert("All field must be correct filled");

                return false;
            }


            return true;
        }


    </script>
</head>
<body>

<form action="<c:url value="/addbook" />" method="post" onsubmit="return checkValidation();">
    <table>
        <tr>
            <td>Book Name:</td>
            <td><input type="text" name="name" id="name" > </td>
        </tr>
        <tr>
            <td>Author</td>
            <td><input type="text" name="author" id="author" ></td>
        </tr>

        <tr>
        <td>ISBN</td>
        <td><input type="text" name="isbn" id="isbn"></td>
        </tr>

        <tr>
            <td>Price</td>
            <td><input type="number" step="any" name="price" id="price" ></td>
        </tr>
    </table>

    <input type="hidden" name="token" value="${token}">
    <input type="submit" value="Submit">
</form>
<br>
<a href="<c:url value="/listbook" />" class="likeabutton">List Books</a>




</body>
</html>
