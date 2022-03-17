<%-- 
    Document   : jsltform_pet
    Created on : 17/02/2022, 08:13:13 AM
    Author     : Mariana
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<div class="container-contact100">
    <div class="wrap-contact100 m-t-80">
        <form:form commandName="pet" cssClass="contact100-form validate-form" method="POST" enctype="multipart/form-data">
            <span class="contact100-form-title m-b-0">
                Pet
            </span>
            <form:errors path="*" element="div" cssClass="alert alert-danger" />
            <form:input path="name" type="hidden" value="${update}"/>
            <div class="wrap-input100 validate-input" data-validate="Name is required">
                <form:label path="name" cssClass="label-input100">Name</form:label>
                <form:input path="name" cssClass="input100" type="text" placeholder="Enter the pet's name"/>
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Born year is required: ex@abc.xyz">
                <form:label path="born_year" cssClass="label-input100">Born year</form:label>
                <form:input path="born_year" cssClass="input100" type="number" placeholder="Enter the pet's born year" min="1960" max="2022"/>
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate = "Color is required">
                <form:label path="color" cssClass="label-input100">Color</form:label>
                <form:input path="color" cssClass="input100" type="text" placeholder="Enter the pet's color" />
                <span class="focus-input100"></span>
            </div>
            <div class="wrap-input100 validate-input" data-validate = "Breed is required">
                <form:label path="breed" cssClass="label-input100">Breed</form:label>
                <form:input path="breed" cssClass="input100" type="text" placeholder="Enter the pet's breed" />
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 input100-select">
                <form:label path="pet_type" cssClass="label-input100">Pet type</form:label>
                    <div class="select2">
                    <form:select path="pet_type" cssClass="form-select selection-2-pettype fs-18 w-100">
                        <option disabled>Select a type of pet</option>
                        <form:option selected="true" value="Cat" cssClass="w-100">Cat</form:option>
                        <form:option value="Dog" cssClass="w-100">Dog</form:option>
                        <form:option value="Hamster" cssClass="w-100">Hamster</form:option>
                        <form:option value="Bird" cssClass="w-100">Bird</form:option>
                        <form:option value="Reptil" cssClass="w-100">Reptil</form:option>
                        <form:option value="Other" cssClass="w-100">Other</form:option>
                    </form:select>
                </div>
                <span class="focus-input100"></span>
            </div>

            <div class="wrap-input100 input100-select">
                <form:label path="is_adopted" cssClass="label-input100">Is adopted?</form:label>
                    <div>
                    <form:select path="is_adopted" cssClass="selection-2-isadopted form-select fs-18 w-75">
                        <option disabled>Select if is adopted</option>
                        <form:option value="true" cssClass="w-100">Yes</form:option>
                        <form:option selected="true" value="false" cssClass="w-100">No</form:option>
                    </form:select>
                </div>
                <span class="focus-input100"></span>
            </div>
                    
            <div class="wrap-input100 validate-input" data-validate = "Photo is required">
                <form:label path="photo" cssClass="label-input100">Photo</form:label> <br/>
                <c:if test="${update}">
                    <img src="<c:url value="${pet.old_photo}"></c:url>" style="width: 250px; height: 100%; margin-bottom: 15px" />
                </c:if>
                <form:input path="photo" cssClass="form-control" type="file" />
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
                    <a class="contact100-form-btn btn mt-1 mb-0" href="listpets.htm">
                        <span class="fs-18">List Pets</span>
                    </a>
                </div>
            </div>    
        </form:form>

    </div>
</div>
<div id="dropDownSelect1"></div>
<div id="dropDownSelect2"></div>
<script>
    $(".selection-2-pettype").select2({
        minimumResultsForSearch: 20,
        dropdownParent: $('#dropDownSelect1')
    });
    $(".selection-2-isadopted").select2({
        minimumResultsForSearch: 20,
        dropdownParent: $('#dropDownSelect2')
    });
</script>
</body>
</html>

