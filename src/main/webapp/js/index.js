$(function() {
	
	$( document ).ready(function() {
		carregaDadosIniciais();
	});
	
	function carregaDadosIniciais() {
		var eventos = EventoResource.listarTodos();
		var size = "";
		
		$.each(eventos, function(key, value) {

			size += "<div class='evento_box' id='evento_box'>" +
			"<div class='row'>" +
					"<div class='col-xs-6 col-md-3'>" +
					"<div class='panel panel-default'>";
			size += "<div class='panel-heading'>";
			size += "<a href='./evento.html?id=" + value.id + "'>" + value.nome + "</a></div>";
			size += "<div class='panel-body'>";
			size += "<p><small> <b>Inicio: </b>" + value.dataInicio + "</small></p>";
			size += "<p><small> <b>Fim: </b>" + value.dataFim + "</small></p>";
			size += "<p><small> <b>Local:</b> " + value.local + "</small></p>";
			size += "</div>";
			size += "</div></div></div></div>";

		});

		$('#eventos_box').append(size);
		
	}
	
});