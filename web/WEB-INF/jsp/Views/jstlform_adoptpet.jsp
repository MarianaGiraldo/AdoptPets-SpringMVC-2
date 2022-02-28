<%-- 
    Document   : form_adoptpet
    Created on : 21/02/2022, 10:31:26 AM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>

<div class="container-contact100">
    <div class="wrap-contact100 m-t-80">
        <form:form commandName="adopt" cssClass="contact100-form validate-form" method="POST">
            <span class="contact100-form-title m-b-0">
                Adopt pet ${pet_id}
            </span>
            <form:errors path="*" element="div" cssClass="alert alert-danger mt-0" />
            <br/>
            <div class="wrap-input100 validate-input" data-validate = "Phone number is required">
                <form:label path="user_id" cssClass="label-input100">Enter your Identification Number</form:label>
                <form:input path="user_id" cssClass="input100" type="text" placeholder="Enter your ID"  value="" pattern="[0-9]{10}" />
                <span class="focus-input100"></span>
            </div>
            <div class="wrap-input100 input100-select">
                <form:label path="pet_id" cssClass="label-input100">Pet id</form:label>
                <form:input path="pet_id" cssClass="input100 " type="text" value="${pet_id}" disabled="true"/>

            </div>
            <div>
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
        </form:form>
    </div>
</div>

</body>
</html>
