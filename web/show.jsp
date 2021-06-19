<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.FULL_NAME}</h1>
        <form action="SearchControllerDOM" method="POST">
            Address : <input name="txtAddress" type="text"/>
            <br/>

            <input type="submit" value="Search"/>
        </form>
    <c:if test="${requestScope.STUDENT_LIST != null}">
        <c:if test="${not empty requestScope.STUDENT_LIST}" var="checkList">
            <table border="1">
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>Class</th>
                        <th>Full Name</th>
                        <th>Status</th>
                        <th>Address</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.STUDENT_LIST}" var="studentDTO">
                    <tr>
                        <td>${studentDTO.id}</td>
                        <td>${studentDTO.aClass}</td>
                        <td>${studentDTO.firstName} ${studentDTO.middleName} ${studentDTO.lastName}</td>
                        <td>${studentDTO.status}</td>
                        <td>${studentDTO.address}</td>
                        <td>
                    <c:url var="deleteLink" value="DeleteControllerDOM">
                        <c:param name="id" value="${studentDTO.id}"/>
                        <c:param name="txtAddress" value="${param.txtAddress}"/>
                    </c:url>

                    <a href="${deleteLink}">
                        Delete
                    </a>
                    </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:if>
        <c:if test="${!checkList}">
            No record found !!!!
        </c:if>
    </c:if>
</body>
</html>
