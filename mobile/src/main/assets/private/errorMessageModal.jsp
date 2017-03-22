
<div class="container">
    <div class="modal fade" id="errorMessageModal" role="dialog" aria-hidden="true" style="display: none;">
       <div class="modal-dialog">
         
	       <!-- Modal content-->
	       <div class="modal-content">
	           <div class="modal-header">
	               <button type="button" class="close" data-dismiss="modal">×</button>
	               <h2 class="modal-title">An error occured!</h2>
	             </div>
	
	             <div class="modal-body">
	                  <div class="row">
	                      <div class="col-md-12">
	                          <center>
	                               <h3><%=request.getAttribute("errorMessage")%></h3>
	                          </center>
	                      </div>
	                  </div>
	                  <br>
	             </div>
	             
	             <div class="modal-footer">
	                  <center>
	                      <button data-dismiss="modal" name="errorButtonDismiss" type="button" class="btn btn-primary">Return</button>
	                  </center>
	             </div>
	       </div>




       </div>
 </div>
</div>
