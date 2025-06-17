<%-- 
    Document   : create
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/include/header.jsp" %>
  <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Artist list</h1>
                <a class=" btn btn-success float-end">create</a>
                <form class="form" method="post" action="artist">
                    <p>
                        <label for="name">Artist name</label>
                        <input  type="text" id="name" name="name" required=>
                        
                    </p>
                    <p>
                        <button class="btn btn-success" type="submit"  name="action" value="create">Add</button>
                        <button type="clear" class="btn btn-secondary">Clear</button>
                    
                    </p>
                    
                    
                    
                </form>



<%@include file="/WEB-INF/include/footer.jsp" %>