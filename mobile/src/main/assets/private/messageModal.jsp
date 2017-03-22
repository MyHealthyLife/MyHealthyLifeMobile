
<div class="container">
    <div class="modal fade" id="messageModal" role="dialog" aria-hidden="true" style="display: none;">
       <div class="modal-dialog">
         <!-- Modal content-->
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal">×</button>
                 <h2 class="modal-title">Message</h2>
               </div>
               <div class="modal-body">
                   
                    <div class="row">
                        <div class="col-md-12">
                            <center>
                                 <h3>
                                    <% if(request.getAttribute("genericMessage")!=null) { %>
                                       <%=request.getAttribute("genericMessage")%>
                                    <% } else { %>
                                        <%=session.getAttribute("genericMessage")%>
                                        <%session.removeAttribute("genericMessage");%>
                                    <% } %>
                                 </h3>
                            </center>
                        </div>
                    </div>
                    <br>

               </div>
               <div class="modal-footer">
                    <center>
                        <button data-dismiss="modal" name="genericButtonDismiss" type="button" class="btn btn-primary">Return</button>
                    </center>
               </div>
         </div>

       </div>
 </div>
</div>