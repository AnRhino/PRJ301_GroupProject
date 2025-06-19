<%-- 
    Document   : header
    Created on : Jun 16, 2025, 1:13:17 PM
    Author     : Le Thien Tri - CE191249
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

 <header>
            <!-- Fixed navbar -->
            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div class="container-fluid">
                    <a href="userProduct"><img class="navbar-brand" src="assets/images/logo.png" alt="logo store" style="width: 35px; height: 35px;" /> </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarCollapse">
                        <div>
                            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="userProduct">Home</a>
                                </li>
                                
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="userProduct">Category</a>
                                </li>
                            </ul>
                        </div>
                        <div>
                            <input class="search border-2" style="border-radius: 5px;" type="search" placeholder="Search" aria-label="Search">
                            <button class="btn btn-outline-success m-2"  type="submit">Search</button>
                        </div>
                        <div class="flex align-content-end ">
                            <form class="d-flex">   
                                <button class="btn btn-outline-primary m-2" type="submit">Login</button>
                                <button class="btn btn-outline-light m-2" type="submit"><i class="bi bi-cart2"> Cart</i></button>
                            </form>
                        </div>
                    </div>
                </div>
            </nav>
        </header>
