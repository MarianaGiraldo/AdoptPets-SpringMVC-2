<%-- 
    Document   : list_pets
    Created on : 21/02/2022, 11:23:56 AM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/nav.jsp" %>

<div class="container-contact100 p-2">
    <div class="m-t-80 wrap-list">
        <span class="contact100-form-title mb-0 mt-4">
            List of pets
        </span>
        <div class="container-contact100-form-btn w-50 mx-auto mb-3">
            <div class="wrap-contact100-form-btn pt-2 pb-0">
                <div class="contact100-form-bgbtn"></div>
                <a class="contact100-form-btn btn mt-1 mb-0" href="form_pet.htm">
                    <span class="fs-18">Add Pet</span>
                </a>
            </div>
        </div>
        <div class="row p-3 mx-2"  id="listpets">
            <c:forEach items="${pets}" var="pet">
                <div class="card col-sm-2 col-md-3 m-3" style="width: 18rem;">
                    <div class="card-body card-content">
                        <h3 class="card-title"><b>${pet.name}</b> </h3>
                        <h4 class="card-subtitle mb-2 text-muted">${pet.pet_type}</h4>
                        <p class="card-text"><b>Born year: </b>${pet.born_year}</p>
                        <p class="card-text"><b>Color: </b>${pet.color}</p>
                        <p class="card-text"><b>Breed: </b>${pet.breed}</p>
                        <p class="card-text"><b>Is adopted?: </b>
                            <c:if test="${pet.is_adopted}">
                                Yes </p>
                            </c:if>
                            <c:if test="${!pet.is_adopted}">
                            No </p>
                            <form class="card-action mt-4" action="form_adoptpet.htm" method="GET">
                                <input name="pet_id" type="hidden" value="${pet.id}" />
                                <button class="btn btn-primary fs-20" type="submit">Adopt this pet</button>
                            </form>
                        </c:if>
                        <div class="row">
                            <a class="btn btn-success fs-15 mt-3 col mx-1" href="form_pet.htm?id=${pet.id}">Edit pet</a>
                            <a class="btn btn-danger fs-15 mt-3 col mx-1" href="deletepet.htm?id=${pet.id}">Delete pet</a>
                        </div>
                    </div>
                </div>  
            </c:forEach>
        </div>

    </div>
</div>
</body>
</html>
