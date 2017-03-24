<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<title>MyHealthyLife</title>

	 <script src="static/js/jquery-3.1.1.min.js"></script>
    <script src="static/js/angular.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    
	<link rel="stylesheet" type="text/css" href="static/css/bootstrap.min.css" />
    
    <script src="static/js/progress-bar.js"></script>
	<link rel="stylesheet" href="static/css/progress-bar.css" />
	<link rel="stylesheet" href="static/css/help.css" />
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-default navbar-fixed-top">
  	<div class="container-fluid">
	    
	    <div class="navbar-header">
	    	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
	      	</button>
	        <a class="navbar-brand" href="#"><img src="logo.jpg" class="logo" alt="logo" style="max-height:30px; max-width:100px;"></a>
	    </div>
	
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	        <ul class="nav navbar-nav">
	            <li><a href="index.jsp">My Profile <span class="sr-only"></span></a></li>
	            <li><a href="progress.jsp">Progress</a></li>
	            <li><a href="social.jsp">Social</a></li>
	            <li class="active"><a href="help.jsp">Help</a></li>
	        </ul>
		 
	 		<!-- Search bar-->
	       	<form action="#" method="post" class="navbar-form navbar-left" role="search">
		        <div class="form-group">
		
		            <input name="searchText" type="text" class="form-control" placeholder="Search contents">
		            <button type="submit" class="btn btn-default btn-sm">
	     				<span class="glyphicon glyphicon-search"></span>
			        </button>
					
		        </div>
      		</form>
		   	  
		  	<!-- Logout bar -->
			<div class="navbar-form navbar-right">
	                    
	            <form id="form1" action="Logout" method="POST" class="navbar-form navbar-left" role="logout"></form>
	            
				<ul class="nav pull-right">
	               	<button type="submit" form="form1" class="btn btn-default btn-md">Logout</button>
	               </ul>
			</div>
			
		</div>
    </div>
	</nav>
	
	
	
	
	<section id="contact" class="content-section text-center">
        <div class="contact-section">
            <div class="container">
            
			<br><br><br>
              <h2>Contact Us</h2>
              <div class="row">
                <div class="col-md-8 col-md-offset-2">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label for="exampleInputName2">Name</label>
                      <input type="text" class="form-control" id="exampleInputName2" placeholder="Insert your name and surname">
                    </div>
                    <div class="form-group">
                      <label for="exampleInputEmail2">Email</label>
                      <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Insert your email address">
                    </div>
                    <div class="form-group ">
                      <label for="exampleInputText">Your Message</label>
                     <textarea  class="form-control" placeholder="Description"></textarea> 
                    </div>
                    <button type="submit" class="btn btn-default">Send Message</button>
                  </form>

                  <hr>
                    <h3>Our Social Sites</h3>
                  <ul class="list-inline banner-social-buttons">
                    <li><a href="#" class="btn btn-default btn-lg"><i class="fa fa-twitter"> <span class="network-name">Twitter</span></i></a></li>
                    <li><a href="#" class="btn btn-default btn-lg"><i class="fa fa-facebook"> <span class="network-name">Facebook</span></i></a></li>
                    <li><a href="#" class="btn btn-default btn-lg"><i class="fa fa-youtube-play"> <span class="network-name">Youtube</span></i></a></li>
                  </ul>
                </div>
              </div>
            </div>
        </div>
      </section>

</body>
</html>