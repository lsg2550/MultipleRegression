$(document).ready(function () { 

$('a[href^=http], area[href^=http]').click(function(event){
event.preventDefault();
var link = $(this).attr("href");



if ((!(link.match(/^http\:\/\/([^ ]*huduser\.gov)/) || link.match(/^https\:\/\/([^ ]*huduser\.gov)/)))&&(!link.match(/^(http|https)\:\/\/([^ ]*hud\.gov)/) ))
{	
    $("<div class='modal fade' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'> <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button><h4 class='modal-title'>You are about to leave HUDUser.gov</h4></div><div class='modal-body'><p>You have requested a document that is external to the HUDUser.gov website. Would you like to continue?</p></div><div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'>Cancel</button> <button type='button' id='myBtn' class='btn btn-primary' data-dismiss='modal' onclick=\"window.open('"+link+"')\">&nbsp;&nbsp; Yes &nbsp;&nbsp;</button></div></div></div></div>").appendTo( "body" );    
	$('#myModal').modal('show');

/*myModal had to be removed because the link was not getting updated  */
$('#myModal').on('hidden.bs.modal', function (e) { 
 $( "#myModal" ).remove();
})

//document.getElementById("myBtn").addEventListener("click", function(){
	
   // alert(link);
//});
}
else
{
window.open(link);
}	
	
}); 
	
	
$('a[href$=".pdf"]').click(function(){

 $(this).attr('target', '_blank');
	
});	


	
});			

	var myFunction = function (link) {
        alert(link);
    };
