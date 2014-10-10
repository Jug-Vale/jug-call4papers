$(function() {
	$( document ).ready(function() {
		$.id = readURLParam('id');
		criaDialogVoto();
		eventoEspecifico();
	});
	
	function criaDialogVoto(tituloPaper, idPaper) {
		if(!idPaper) return;
		$("#paper_voto_titulo").html(tituloPaper)
		$("#voto_paper_confirmacao").modal('show');
		$("#btn_confirmar_voto").unbind("click").click(function () {
			var r = new REST.Request();
			r.setURI("./rest/paper/" + idPaper + "/votar");
			r.setMethod("POST");	
			r.execute(function(status, request, response, entity) {
				console.log(status + " - " + response)
				var alertaVoto = $("#status_voto");
				if(status == 200) {
					alertaVoto
						.removeClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
						.addClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
						.html("Voto para \"" +  tituloPaper + "\" aceito com sucesso!");
					preenchePapers($.id)
				}
				else if (status == 401 || status == 403){
					alertaVoto
						.removeClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
						.addClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
						.html("Opa, votação não autorizada! Já votou nessa submissão?");
				}
				else {
					alertaVoto
						.removeClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
						.addClass( "alert alert-danger alert-dismissible" )
						.html("Erro no servidor ao realizar a votação...<br /> " + response);
				}
				alertaVoto.fadeOut(200, function() {
					alertaVoto.fadeIn(200);
				});
				$("#btn_close_alerta_voto").removeClass("hide");
				$("#voto_paper_confirmacao").modal("hide");
			});
		});
	}

	function eventoEspecifico() {
		if(!$.id) naoEncontrado();
		var evento = EventoResource.buscaPorId({id:$.id} );
		if(! evento)  naoEncontrado();
		var maps = "http://maps.googleapis.com/maps/api/staticmap?center=" + evento.local + "&zoom=15&size=580x300&sensor=false";
		
		$("#mapa_id").attr("src", maps);
				
		if(evento.aceitandoTrabalhos === true) {
			$("#aceitando_paper").addClass( "alert alert-info alert-dismissible" )
								 .append("Uhul !!!!! Este evento está aceitando Papers. " +
								 		 	"<a href='paper.html?evento=" + evento.id+ "' class='alert-link'>Submeta o seu</a>");
		} else {
			$("#aceitando_paper").addClass( "alert alert-danger alert-dismissible" ).append("Ops!!! Já encerramos os papers :(");
		}
		if(evento.inscricoesAbertas === true) {
			$("#aceitando_inscricao").addClass( "alert alert-info alert-dismissible" )
								 .append("Inscrições abertas para esse evento!! \o/ " +
								 		 	"<a href='inscricao.html?evento=" + evento.id+ "' class='alert-link'>Inscrição.</a>");
		} else {
			$("#aceitando_inscricao").addClass( "alert alert-danger alert-dismissible" ).append("Inscrições estão fechadas... :(");
		}
		aceitando_inscricao
		
		$("#nome_evento_id").append( "<h1>" + evento.nome + "</h1>" );
		preenchePapers($.id);
		$("#descricao_id").append( evento.descricao );
		$("#data_inicio_id").append( evento.dataInicio );
		$("#data_fim_id").append( evento.dataFim );
		$("#local_id").append( evento.local );		
		$("#site").attr( "href", evento.url).append(evento.url);
		
	}
	
	function preenchePapers(id) {
		$(".paper_accordion").remove();
		var papers = EventoResource.listaPapersPorEvento({eventoId:id});
		papers.sort(function (p1, p2) {
			return  p2.nota-p1.nota;
		});
		$.each(papers, function(key, value) {
			var accordion = $("#accordion_hide").clone();
			var id_paper = value.id;
			
			accordion.removeClass("hide");
			accordion.addClass("paper_accordion");
			accordion.attr('id', 'accordion' + id_paper);
			
			accordion.find("#titulo_id")
						.attr( 'href', '#'+ id_paper )
						.attr('id','id_' + id_paper)
						.append( value.titulo );
			accordion.find("#paper_total_votos").html(value.nota + " votos");
			accordion.find("#votar_evento")
				.attr('id', 'votar_evento' + id_paper)
				.click(function() {
				criaDialogVoto(value.titulo, id_paper);
			});
			accordion.find(".panel-collapse")
						.attr('id', id_paper );
			accordion.find(".panel-body")
						.attr('id', 'painel_body_' + id_paper );
			accordion.find("#descricao_paper_id")
						.attr('id', 'descricao_paper_id_' + id_paper )
						.append(value.descricao);
			accordion.find("#tipo_paper_id")
						.attr('id', 'tipo_paper_id_' + id_paper )
						.append(value.tipo);
			accordion.find("#autores_id").append(montaTextoAccordion(value.autores));
			$("#papers_id").append(accordion);
		});
	}
	
	function montaTextoAccordion(autores) {
		var desc = "";
		$.each(autores, function(key, value) {
			desc += 	"<dl id='dl_autores_id'>" +
            		 		"<dt id='nome_autor_id_'" + value.id+  ">" + value.nome +"</dt>" +
            		 		"<dd id='descricao_autor_id_'>" + 
            		 				value.miniCurriculo + 
            		 					"<p>" + 
            		 						"<a href='" + value.site + "'>" + value.site + "</a>"
            		 					"</p>" + 
            		 		"</dd>" +
            		 	"</dl>";
		});	
		return desc;
	}
});