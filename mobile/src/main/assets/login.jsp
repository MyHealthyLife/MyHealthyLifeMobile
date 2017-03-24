<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>Login</title>

	<link rel="stylesheet" href="static/css/bootstrap.min.css" />
    
    <script src="static/js/jquery-3.1.1.min.js"></script>
    <script src="static/js/angular.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    

</head>
	<body>
	<!-- <div class="container-fluid">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3>Login</h3>
					</div>
					<div class="panel-body">
						<center>
							<form method="POST" action="LoginValidator">
								<label>Username:</label><br/>
								<input type="text" name="username"  /><br/>
								<label>Password:</label><br/>
								<input type="password" name="password"/><br/><br/>
								<input type="submit" class="btn btn-primary"/> <br/>
							</form>
						</center><br>
						<a href="register.jsp">Register now!</a>
					</div>
				</div>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>-->
	
	
	
	
	
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	        <form name="logModule" action="Registration" method="POST" class="navbar-form navbar-right">
	            <div class="form-group">
	
	                Haven't got an account yet?<button type="button" class="btn btn-default btn-md" data-toggle="modal" data-target="#signUpModal">Register</button>
	
	            </div>
	        </form>
	    </div>
        
        
        <div class="container">
			<div class="row">
				<div class="col-md-3">
					<br>
                </div>
								
				<div class="col-md-6">
		            <div class="form-group">
		                <h2>Login Form</h2>
		            </div>
		            
		            <br>
		
		            <form name="module" role="form" action="LoginValidator" method="POST" onsubmit="return control();">
		                <div class="form-group">
		                    <label for="username">
		                        <span class="glyphicon glyphicon-user"></span> Username
		                    </label>
		                    <input type="text" class="form-control" name="username" placeholder="Enter email" value="">
		                </div>
		                
		                <div class="form-group">
		                    <label for="password">
		                        <span class="glyphicon glyphicon-lock"></span> Password
		                    </label>
		                    <input type="password" class="form-control" name="password" placeholder="Enter password" value="">
		                </div>
		                
		                <button type="submit" class="btn btn-primary btn-block"><span class="glyphicon glyphicon-off"></span> Login</button>
		            </form>
		            
		            <a data-dismiss="modal" href="" data-toggle="modal" data-target="#passwordRecoveryModal">Forgot your password?</a>
		            
				</div>
                        
	            <div class="col-md-3">
					<br>            
	            </div>
			</div>
        </div>
        
        
        
        <!-- Include modals -->
    	<%@ include file="signUpModal.jsp" %>
    	
    	<% if(request.getAttribute("errorMessage")!=null) { %>
    
        <%@ include file="private/errorMessageModal.jsp" %>
        	<script language="JavaScript" type="text/javascript" src="static/js/showGenericErrorMessage.js"></script>
   		<% } %>
    
    	<% if(request.getAttribute("genericMessage")!=null) { %>
    
        <%@ include file="private/messageModal.jsp" %>
        	<script language="JavaScript" type="text/javascript" src="static/js/showGenericMessage.js"></script>
    	<% } %>
    	
	</body>
</html>