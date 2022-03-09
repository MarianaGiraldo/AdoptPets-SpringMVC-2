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
        
        <!--Font Awesome===============================================================================================-->
        <link rel="stylesheet" type="text/css" href='<c:url value="public/fonts/font-awesome-4.7.0/css/font-awesome.min.css" />'>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        
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
                <a class="navbar-brand" href="index.htm">Home</a>
            </div>
            <button class="navbar-toggler" style="color: #fff;" type="button" data-bs-toggle="collapse" data-bs-target="#mainListDiv" aria-controls="mainListDiv" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div id="mainListDiv" class="main_list collapse navbar-collapse mx-0 p-0">
                <ul class="navlinks navbar-nav me-auto mb-2 mb-lg-0 ">
                    <li class="nav-item">
                        <a class="nav-link" href="listusers.htm">Users</a>
                    </li>
                    <li class="nav-item">
<<<<<<< HEAD
                        <a class="nav-link" href="listpets.htm">Pets</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listadoptions.htm">List Adoptions</a>
                    </li>
                </ul>
                <ul class="navlinks navbar-nav float-right p-0 mr-0">
                    
                    <li class="nav-item  float-right">
=======
                        <a class="nav-link" href="listusers.htm">Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listpets.htm">Pets</a>
                    </li>
                    <li class="nav-item">
>>>>>>> 214b4f8724da09cb4f990380c37b7522946c8eed
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                    <li class="nav-item float-right">
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
