$(function() {
	$('.data').mask('00-00-0000 00:00');
	
	$('#btnNovoEvento').click(function() {
		console.log(haErrosNosForms());
		if(!haErrosNosForms()) {
			var evento = {};
			evento.nome = $("#nome").val();
			evento.descricao = $("#descricao").val();
			evento.dataInicio = $("#dataInicio").val();
			evento.dataFim = $("#dataFim").val();			
			evento.local = $("#local").val();
			evento.url = $("#input_site").val();	
			EventoResource.criar({
				$entity: evento,
				$callback: function(httpCode, xmlHttpRequest) {	
					alert(httpCode)
				}
			});
			
		}
	});
});
