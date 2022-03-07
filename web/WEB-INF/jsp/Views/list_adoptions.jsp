<%-- 
    Document   : list_adoptions
    Created on : 3/03/2022, 10:53:49 AM
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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
             
<div class="container-contact100 p-2">
    <div class="m-t-80 wrap-list">
        <span class="contact100-form-title mb-0 mt-4">
            List of adoptions
        </span>
        <div class="container-contact100-form-btn w-50 mx-auto mb-3">
            <div class="wrap-contact100-form-btn pt-2 pb-0">
                <div class="contact100-form-bgbtn"></div>
                <a class="contact100-form-btn btn mt-1 mb-0" href="form_adoptpet.htm">
                    <span class="fs-18">Add Adoption</span>
                </a>
            </div>
        </div>
        <table id="userTable" class="table table-hover">
            <thead class="table-primary bg-opacity-50">
                <tr>
                    <th>ID</th>
                    <th>User</th>
                    <th>Pet name</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${adoptions}" var="adopt">
                    <tr>
                        <td>${adopt.id}</td>
                        <td>${adopt.user_name}</td>
                        <td>${adopt.pet_name}</td>
                        <td>${adopt.date}</td>
                        <td>
                            <div class="row p-0">
                                <script src="https://code.iconify.design/2/2.1.2/iconify.min.js"></script>
                                <a class="btn btn-success col mx-1" href="form_adoptpet.htm?id=${adopt.id}"><span class="iconify fs-20" data-icon="clarity:pencil-solid"></span></a>
                                <a class="btn btn-danger col mx-1" href="deleteadoption.htm?id=${adopt.id}"><span class="iconify fs-20" data-icon="bi:trash-fill"></span></a>
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