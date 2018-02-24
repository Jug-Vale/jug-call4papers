$(function() {

	$(document).ready(function() {
		carregaDadosIniciais();
	});

	function carregaDadosIniciais() {
		var eventos = EventoResource.listarTodos();
		var size = "";
		var passadosHtml = "";
		var agora = new Date();
		var novosEventos = $.grep(eventos, function(e, i) {
			var data = converteParaData(e.dataFim);
			return data > agora;
		});
		var eventosPassados = $.grep(eventos, function(e, i) {
			var data = converteParaData(e.dataFim);
			return data < agora;
		});
		$.each(novosEventos, function(key, value) {

			size += "<div class='evento_box' id='evento_box'>"
					+ "<div class='row'>" + "<div class='col-xs-6 col-md-12'>"
					+ "<div class='panel panel-default'>";
			size += "<div class='panel-heading'>";
			size += "<a href='./evento.html?id=" + value.id + "'>" + value.nome
					+ "</a></div>";
			size += "<div class='panel-body'>";
			size += "<p><small> <b>Inicio: </b>" + value.dataInicio
					+ "</small></p>";
			size += "<p><small> <b>Fim: </b>" + value.dataFim + "</small></p>";
			size += "<p><small> <b>Local:</b> " + value.local + "</small></p>";
			size += "</div>";
			size += "</div></div></div></div>";

		});
		
		$.each(eventosPassados, function(key, evt) {
			passadosHtml += "<li><a href='./evento.html?id=" + evt.id + "'>" + evt.nome + "</a></li>" ;
		});
		

		if(novosEventos.length > 0) {
			$('#eventos_box').html(size);
		}
		if(eventosPassados.length > 0) {
			$('#eventos_passados').html(passadosHtml);
		}

	}

});