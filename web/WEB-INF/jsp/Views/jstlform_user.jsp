<%-- 
    Document   : jsltform_user
    Created on : 17/02/2022, 08:13:56 AM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>

<div class="container-contact100">
    <div class="wrap-contact100 m-t-80">
        <form:form commandName="user" cssClass="contact100-form validate-form" method="POST" enctype="multipart/form-data">
            <span class="contact100-form-title m-b-0">
                Welcome!
            </span>
            
            <form:errors path="*" element="div" cssClass="alert alert-danger mt-0" />
            <form:input path="name" type="hidden" value="${update}"/>
            <br/>
            <div class="wrap-input100 validate-input" data-validate="Name is required">
                <form:label path="name" cssClass="label-input100">Your Name</form:label>
                <form:input path="name" cssClass="input100" type="text" placeholder="Enter your name" />
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
                <form:label path="email" cssClass="label-input100">Email</form:label>
                <form:input path="email" cssClass="input100" type="email" placeholder="Enter your email addess" />
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Phone number is required">
                <form:label path="phoneNumber" cssClass="label-input100">Phone Number</form:label>
                <form:input path="phoneNumber" cssClass="input100" type="tel" placeholder="Enter your phone number" value="" pattern="[0-9]{10}" title="Phone Number invalid: Only numbers allowed"/>
                <span class="focus-input100"></span>
            </div>
            <div class="wrap-input100 validate-input" data-validate = "Document number is required">
                <form:label path="document" cssClass="label-input100">Identification Number</form:label>
                <form:input path="document" cssClass="input100" type="text" placeholder="Enter your ID"  value="" pattern="[0-9]{10}" />
                <span class="focus-input100"></span>
            </div>
                
            <div class="wrap-input100 validate-input" data-validate = "Photo is required">
                <form:label path="photo" cssClass="label-input100">Photo</form:label>
                <c:if test="${update}">
                    <br><img class="rounded-lg offset-md-3" src="<c:url value="${user.old_photo}"></c:url>" style="width: 50%; height: 100%; margin-bottom: 15px" />
                </c:if>
                <form:input path="photo" cssClass="form-control" type="file" value="null" />
                <span class="focus-input100"></span>
            </div>

            <div class="container-contact100-form-btn">
                <div class="wrap-contact100-form-btn">
                    <div class="contact100-form-bgbtn"></div>
                    <form:button name="Submit" class="contact100-form-btn">
                        <span>
                            Submit
                            <i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i>
                        </span>
                    </form:button>
                </div>
            </div>
            <div class="container-contact100-form-btn">
                <div class="wrap-contact100-form-btn pt-2 pb-0">
                    <div class="contact100-form-bgbtn"></div>
                    <a class="contact100-form-btn btn mt-1 mb-0" href="listusers.htm">
                        <span class="fs-18">List Users</span>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
