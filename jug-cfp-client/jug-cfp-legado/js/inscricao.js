$(function() {
	carregaEvento();
	
	$("#btnInscricao").click(enviaInscricao);
	
	
	function enviaInscricao() {

		if(haErrosNosForms()) {
			$("#status_inscricao")
				.addClass( "alert alert-danger alert-dismissible" )
				.html("Problema nos campos do form, por gentileza, verifique.");
			return false;
		}
	
		$.participante = {
				 nivel: $('#level').val(),
		         nome: $("#nome").val(),
		         email: $("#email").val(),
		         rg:$("#rg").val(),
		         empresa: $("#empresa").val(),
		         instituicao: $("#instituicao").val()
		};
		InscricaoResource.inscrever({
			eventoId: readURLParam("evento"), 
			$entity: $.participante,
			$callback: function(httpCode, xmlHttpRequest, evento) {
				if(httpCode == 200){
					$("#status_inscricao")
					.removeClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
					.addClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
					.html("Parabéns, inscrição realizada com sucesso!");
				} 
				else if (httpCode == 409){
					$("#status_inscricao")
					.removeClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
					.addClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
						.html("Parece que você já se inscreveu para esse evento! (o e-mail utilizado já consta em nossa base de dados)");
				}
				else if (httpCode == 404){
					naoEncontrado();
				}
				else {
					$("#status_inscricao")
					.removeClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
					.addClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
					.html("Ocorreu um erro interno. Por gentileza, envie um e-mail para jugvale@gmail.com");				
				}
			}
		});		
	}
	
	function carregaEvento() {
		var idEvento = readURLParam("evento");
		if(!idEvento) naoEncontrado();
		EventoResource.buscaPorId({
			id: idEvento, 
			$callback: function(httpCode, xmlHttpRequest, evento) {
				if(httpCode == 200){
					$("#evento_nome").html(evento.nome);
					$.evento = evento;
				} else if (httpCode == 404){
					naoEncontrado();
				}
			}
		});
	}
});