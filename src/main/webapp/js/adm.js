$(function() {
	$('.data').mask('00-00-0000 00:00');
	// com angular td seria mais fácil ..
	var eventosHtml = "";
	var eventos = EventoResource.listarTodos();
	eventos.sort(function(a, b) {
		var d1 = converteParaData(a.dataFim);
		var d2 = converteParaData(b.dataFim);
		return d1 < d2;
	});
	var agora = new Date();
	$.each(eventos, function(key, evt) {
		
		eventosHtml += "<li>";
		if(converteParaData(evt.dataFim) > agora) {
			eventosHtml += "<span style='color: blue'>"
		} else {
			eventosHtml += "<span style='color: red'>"
		}
		eventosHtml += evt.nome;
		eventosHtml += " ( <a href='./rest/evento/"+ evt.id + "/inscritos'>ver inscritos</a> )";
		eventosHtml += "</span>"
		eventosHtml +=	"</li>";
		
	});
	$("#todosEventos").append(eventosHtml);
	
	$('#btnNovoEvento').click(function() {
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
					if(httpCode == 201) 
						alert("Evento criado com sucesso!")
						limpaCamposForm();
				}
			});
			
		}
	});
});
