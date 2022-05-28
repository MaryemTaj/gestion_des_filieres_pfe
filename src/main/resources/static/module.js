$(document).ready(function(){
	
	getSemestres();
	
});
function getSemestres(){
	url="http://localhost:8080/newModule/1/semestres";
	$.ajax({
		method:"GET",
		url}).done(function(responseJson){
			semestre=$('#exampleFormControlSelect1');
			$.each(responseJson,function(exampleFormControlSelect1){
			alert(exampleFormControlSelect1.get("Nom"));
			
			});
		}).fail(function(){
			alert("fail to connect to server");
		})
		.always(function(){
			alert("always executed")
		});
}