<%-- 
    Document   : jsltview_user
    Created on : 17/02/2022, 08:19:36 AM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>

<div class="container-contact100">
    <div class="wrap-contact100 m-t-80">
        <h1>User</h1>
        <div class="py-3">
            <b>Name: </b> <c:out value="${ub.getName()}" /><br/>
            <b>Email: </b> <c:out value="${ub.getEmail()}" /><br/>
            <b>Phone: </b> <c:out value="${ub.getPhoneNumber()}" /><br/>
            <b>Document: </b> <c:out value="${ub.getDocument()}" /><br/>
            
            <div class="container-contact100-form-btn mb-0 w-75 mx-auto">
                <div class="wrap-contact100-form-btn pt-2 pb-0">
                    <div class="contact100-form-bgbtn"></div>
                    <a class="contact100-form-btn btn mt-1 mb-0" href="listusers.htm">
                        <span class="fs-18">Go to users list</span>
                    </a>
                </div>
            </div>

        </div>
        <br/>
    </div>
</div>
</body>
</html>
