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

            #carousel-img{
                height: 300px;
            }

            #Search-bar{
                border-radius: 5px;
                width: 400px;
            }

            #img{
                width: 300px;
            }

            #navbarCollapse{
                max-height: 20px;
            }

            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                width: 300px;
                background-color: honeydew;
            }
        }



        @media (max-width: 1434px){
            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                width: 240px;
                background-color: honeydew;
            }

            #img{
                width: 240px;
            }

        }

        @media (max-width: 1126px){

            #cate-img{
                width: 180px;
                height: 110px;
            }

            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                background-color: honeydew;
                width: 200px;
            }

            #img{
                width: 200px;
            }

        }

        @media (max-width: 995px) {
            .bd-placeholder-img-lg {
                font-size: 2.5rem;
            }

            #carousel-img{
                height: 300px;
            }

            #cate-img{
                width: 125px;
                height: 90px;
            }

            #img{
                width: 200px;
            }

            #Search-bar{
                width: 190px;
            }
        }

        
          @media (max-width: 886px){
            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                width: 160px;
                background-color: honeydew;
            }

            #img{
                width: 160px;
            }

        }
        
         @media (max-width: 726px){
            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                width: 130px;
                background-color: honeydew;
            }

            #img{
                width: 130px;
            }
         }
             @media (max-width: 614px){
            #productInfo{
                display: flex;
                border: 1px solid black;
                flex-wrap: inherit;
                justify-content: center;
                align-content: center;
                width:85px;
                background-color: honeydew;
                 height: 110px;
            }

            #img{
                width: 85px;             
            }

            #cate-img{
                width: 80px;
                height: 65px;
            }
        }
  
        .navbar-collapse{
            transition: all .5s;
        }
        
        body {
            padding-top: 60px;
            background-color: #DDDDDD;
        }

    </style>


    <!-- Custom styles for this template -->
    <link href="assets/css/sticky-footer-navbar.css" rel="stylesheet"/>
</head>