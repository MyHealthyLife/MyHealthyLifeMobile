
<div class="container">

  <!-- Modal -->
  <div class="modal fade" id="signUpModal" role="dialog">
    <div class="modal-dialog">
    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header" style="padding:20px 50px;">
	          <button type="button" class="close" data-dismiss="modal">×</button>
	          <h4><span class="glyphicon glyphicon-lock"></span> Sign Up</h4>
	        </div>
	        <div class="modal-body" style="padding:40px 50px;">
	            <form role="form" name="regModule" onsubmit="return control();" action="Registration" method="POST">
	                
	                <div class="row">
		                <div class="col-xs-12">
			                <div class="form-group">
			                  <label for="password">Username</label>
			                  <input type="text" class="form-control" name="usernameNew" placeholder="Insert Username">
		                	</div>
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-xs-6">
			                <div class="form-group">
			                  <label for="firstname">Firstname</label>
			                  <input type="text" class="form-control" name="firstname" placeholder="Firstname">
			                </div>
	                	</div>
	                	
		                <div class="col-xs-6">	                
			                <div class="form-group">
			                  <label for="lastname">Lastname</label>
			                  <input type="text" class="form-control" name="lastname" placeholder="Lastname">
			                </div>
	                	</div>
                	</div>
	                
	                <div class="row">
		                <div class="col-xs-6">
			                <div class="form-group">
			                  <label for="password">Password</label>
			                  <input type="password" class="form-control" name="passwordNew" placeholder="Insert Password">
			                </div>
	                	</div>
		                <div class="col-xs-6">
			                <div class="form-group">
			                  <label for="password">Confirm password</label>
			                  <input type="password" class="form-control" name="passwordConfirmNew" placeholder="Confirm Password">
			                </div>
	                	</div>
                	</div>
                	
                	
	                <div class="row">
		                <div class="col-xs-6">
	                		<div class="form-group">
			                  <label for="birthdate">Birthdate</label>
			                  <input type="date" class="form-control" name="birthdate" placeholder="Insert birthdate">
			                </div>
		                </div>
		                <div class="col-xs-6">
	                		<div class="form-group">
		                   		<label for="sex">Sex</label>
				                <!-- <input type="text" class="form-control" name="sex" placeholder="Insert sex">-->
				                <select class="form-control ingredient" name="sex">
			        				<option value="" disabled selected>Male or Female</option>
			        				<option value="M">Male</option>
			        				<option value="F">Female</option>
		          				</select><br>
					        </div>
		                </div>
	                </div>
	                
	                <br><br>
	                  <button type="submit" class="btn btn-primary btn-block"><span class="glyphicon glyphicon-off"></span> Sign Up</button>
	                  
	            </form>
	        </div>
	        
	      </div>
      
    </div>
  </div> 
</div>