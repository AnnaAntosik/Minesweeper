<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
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

        #button {
            width: 5vw;
            height: 5vw;
        }

    </style>
</head>
<body>
<table>
    <c:forEach items="${board}" var="rows" varStatus="yLoop">
        <tr>
            <c:forEach items="${rows}" var="cell" varStatus="xLoop">
                <c:if test="${clickedFieldsBoard[xLoop.index][yLoop.index]}">
                    <td>
                        ${board[xLoop.index][yLoop.index]}
                    </td>
                </c:if>
                <c:if test="${!clickedFieldsBoard[xLoop.index][yLoop.index]}">
                    <td>
                        <form:form method="post" action="/coveredBoard">
                            <input name="x" value="${xLoop.index}" hidden="hidden" />
                            <input name="y" value="${yLoop.index}" hidden="hidden" />
                            <button type="submit" id="button"></button>
                        </form:form>
                    </td>
                </c:if>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>