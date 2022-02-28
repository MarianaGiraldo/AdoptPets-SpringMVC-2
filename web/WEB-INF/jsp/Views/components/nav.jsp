<%-- 
    Document   : nav
    Created on : 13/02/2022, 07:48:59 PM
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<html>
    <head>
        <%@include file="bootstrap.jsp" %>
        <link rel="stylesheet" href='<c:url value="public/css/main.css" />' >
        <link rel="stylesheet" href='<c:url value="public/css/nav.css" />' >
        <link rel="stylesheet" href='<c:url value="public/css/util.css" />' >

        <link rel="icon" type="image/png" href="public/images/icons/favicon.ico"/>
        
        <!--Bootstrap===============================================================================================-->
        <link rel="stylesheet" type="text/css" href='<c:url value="public/vendor/bootstrap/css/bootstrap.min.css" />'>
        <script src='<c:url value="public/vendor/bootstrap/js/popper.js"/>'></script>
        <script src='<c:url value="public/vendor/bootstrap/js/bootstrap.min.js"/>'></script>
        
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href='<c:url value="public/fonts/font-awesome-4.7.0/css/font-awesome.min.css" />'>
        
        <!-- Jquery===============================================================================================-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <!--===============================================================================================-->
        <script src='<c:url value="public/js/main.js"/>'></script>
        <script src='<c:url value="public/js/nav.js"/>'></script>   

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adopt Pets</title>
    </head>
    <body class="">

    <nav class="navbar fixed-top navbar-expand-lg nav bg-dark">

        <div class="container-fluid container">
            <div class="logo">
                <a class="navbar-brand" href="index.htm">Your Logo</a>
            </div>
            <button class="navbar-toggler" style="color: #fff;" type="button" data-bs-toggle="collapse" data-bs-target="#mainListDiv" aria-controls="mainListDiv" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div id="mainListDiv" class="main_list collapse navbar-collapse">
                <ul class="navlinks navbar-nav me-auto mb-2 mb-lg-0 ">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="index.htm">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="form_user.htm">User</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="form_pet.htm">Pet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listpets.htm">Adopt Pet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="logout.jsp">Logout</a>
                    </li>

                </ul>
            </div>
        </div>

    </nav>
    <!-- Jquery needed -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    
    <!-- Function used to shrink nav bar removing paddings and adding black background
    <script>
        $(window).scroll(function() {
            if ($(document).scrollTop() > 50) {
                $('.nav').addClass('bg-dark');
                $('.nav').addClass('p-0');
                console.log("OK");
            } else {
                $('.nav').removeClass('bg-dark');
                $('.nav').removeClass('p-0');
            }
        });
    </script> -->