<%-- 
    Document   : listusers
    Created on : 24/02/2022, 07:32:35 AM
    Author     : Mariana
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css" rel="stylesheet" >
<link href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap5.min.css" rel="stylesheet" >
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap5.min.js"></script>
<script>
    $(document).ready(function() {
        $('#userTable').DataTable();
    });
</script>

<div class="container-contact100">
    <div class="m-t-80 wrap-list">
        <span class="contact100-form-title m-b-0">
            Users
        </span>
        <table id="userTable" class="table table-hover">
            <thead class="table-primary bg-opacity-50">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.email}</td>
                        <td>
                            <div class="row">
                                <a class="btn btn-success mt-3 col mx-1" href="edituser.htm">Edit pet</a>
                                <a class="btn btn-danger mt-3 col mx-1" href="deleteuser.htm">Delete pet</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                    <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.email}</td>
                        <td>
                            <div class="row">
                                <a class="btn btn-success mt-3 col mx-1" href="edituser.htm">Edit pet</a>
                                <a class="btn btn-danger mt-3 col mx-1" href="deleteuser.htm">Delete pet</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                    <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.email}</td>
                        <td>
                            <div class="row">
                                <a class="btn btn-success mt-3 col mx-1" href="edituser.htm">Edit user</a>
                                <a class="btn btn-danger mt-3 col mx-1" href="deleteuser.htm">Delete user</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>
