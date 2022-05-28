$( document ).ready(function() {

$('#divcne').hide();
  
  $('#Role').on('change', function (e) {
   if($('#Role').val()==3){
  	$( '#cne' ).prop( "disabled", false );
   $('#divcne').show();
   
   
   }
   else {
   		$( '#cne' ).prop( "disabled", true );
  		$('#divcne').hide();
   
   
   }
});
  
  
  
});