<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Nunito|Open+Sans" rel="stylesheet">
    <meta charset="utf-8">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="<c:url value='/scripts/css/index.css' />" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.10.2.js' />"></script>
    <script type="text/javascript" src="<c:url value='/scripts/lib/jquery.cookie.js' />"></script>
    <script type="text/javascript" src="<c:url value='/scripts/js/Survey.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/scripts/js/field_validator.js' />" ></script>
</head>
<body>

<div class="width-restrictor centered">

<nav class="navbar">

    <ul class="nav">
        <li><a href="<c:url value='/' />" class="header-link">Home</a></li>
        <security:authorize ifAnyGranted="ROLE_ADMIN">
            <li><a href="<c:url value="/survey/report" />" class="header-link">NPS Report</a></li>
        </security:authorize>
        <security:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a href="<c:url value='/admin' />" class="header-link">Admin</a></li>
        </security:authorize>
        <li>
            <a href="<c:url value='/cart' />" class="header-link"><img id="cart-header-image" src="http://image.flaticon.com/icons/png/512/2/2772.png" />Cart</a>
        </li>
        <% if ((Boolean)session.getAttribute("isLoggedIn")) { %>
        <li><a href="<c:url value='/userProfile' />" class="header-link">Profile</a></li>
        <% } %>

        <% if (!(Boolean)session.getAttribute("isLoggedIn")) { %>
        <li><a href="<c:url value='/login' />" class="header-link">Login</a></li>
        <% } %>
        <% if (!(Boolean)session.getAttribute("isLoggedIn")) { %>
        <li><a href="<c:url value='/account/create' />" class="header-link">Create Account</a></li>
        <% } %>

        <security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
            <li><a href="<c:url value="j_spring_security_logout" />" class="header-link">Logout</a></li>
        </security:authorize>
        <security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">

            <li id="welcome" class="navbar-text">Welcome <%= session.getAttribute("UserName")%>!</li>

        </security:authorize>
    </ul>

</nav>

    <a class="brand" href="<c:url value='/' />">
        <img id="brand-name" width="250px;" src="<c:url value='/images/freewheelers-name.png' />">
        <img id="brand-logo" width="40px;" src="<c:url value='/images/freewheelers-logo.png' />">
    </a>

<!--[if lt IE 9]>
<div class="alert">
    You are using a Legacy Browser - it is not supported. Please upgrade to <a
        href="http://windows.microsoft.com/en-US/internet-explorer/downloads/ie-9/worldwide-languages">IE9</a>, Firefox,
    Safari, Chrome or Opera.
</div>
<![endif]-->

</div><!-- end width-restrictor -->

<article class="main">
    <div class="width-restrictor centered">
