<%-- 
    Document   : head
    Created on : Jun 18, 2025, 8:10:02 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Start Page</title>
    <link rel="icon" type="image/png" href="assets/images/logo.png">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .navbar-collapse{
            transition: all .5s;
        }
        body {
            padding-top: 70px;
        }
        #all {
            position: relative;
            display: inline-block;
            cursor: pointer;
        }

        
        #all-box {
            display: none;
            position: absolute;
            top: 100%;
            background-color: white;
            border: 1px solid black;
            width: 300px;
            padding: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);  
        }

        #all:hover #all-box {
            display: block;
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="assets/css/sticky-footer-navbar.css" rel="stylesheet"/>
</head>