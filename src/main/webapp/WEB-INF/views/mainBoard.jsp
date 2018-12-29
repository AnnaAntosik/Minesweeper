<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table</title>
    <style>
        table {
            border-collapse: separate;
            border-radius: 25px 25px 25px 25px;
            table-layout: fixed;
            background: lightgray;
        }

        table, tr, td {
            border: 2px solid black;
            padding: 5px;
        }

        td {
            text-align: center;
            width: 5vw;
        }

        tr {
            height: 5vw;
        }
    </style>
</head>
<body>
<table>
   <c:forEach items="${board}" var="row">
       <tr>
           <c:forEach items="${row}" var="cell">
               <td>${cell}</td>
           </c:forEach>
       </tr>
   </c:forEach>
</table>
</body>
</html>