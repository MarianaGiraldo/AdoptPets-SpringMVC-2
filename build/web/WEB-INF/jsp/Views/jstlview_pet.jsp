<%-- 
    Document   : jsltview_pet
    Created on : 17/02/2022, 08:24:09 AM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>

<div class="container-contact100">
    <div class="wrap-contact100 m-t-80">
        <h1>Pet</h1>
        <jsp:useBean id="pet" scope="request" class="Models.PetBean" />
        <p class="py-3">
            
            <b>Name: </b>${pet.getName()}<br/>
            <b>Pet type: </b> <jsp:getProperty name="pet" property="pet_type" /><br/>
            <b>Born Year: </b> <jsp:getProperty name="pet" property="born_year" /><br/>
            <b>Color: </b> <jsp:getProperty name="pet" property="color" /><br/>
            <b>Breed: </b> <jsp:getProperty name="pet" property="breed" /><br/>
            <b>Is adopted?: </b> 
            <c:if test="${pet.getIs_adopted()}">Yes</c:if>
            <c:if test="${!pet.getIs_adopted()}">No</c:if><br/>
            
            <div class="container-contact100-form-btn mb-0 w-75 mx-auto">
                <div class="wrap-contact100-form-btn pt-2 pb-0">
                    <div class="contact100-form-bgbtn"></div>
                    <a class="contact100-form-btn btn mt-1 mb-0" href="listpets.htm">
                        <span class="fs-18">Go to pets list</span>
                    </a>
                </div>
            </div>
        </p>
        <br/>
    </div>
</div>
</body>
</html>

