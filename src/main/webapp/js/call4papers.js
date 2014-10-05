$(function() {
	
	$( document ).ready(function() {
		eventoEspecifico();
	});
	
	function eventoEspecifico() {
		$.id = readyURL('id');
		var evento = EventoResource.buscaPorId( {id:$.id} );
		
		var maps = "http://maps.googleapis.com/maps/api/staticmap?center=" + evento.local + "&zoom=15&size=580x300&sensor=false";
		
		$("#mapa_id").attr("src", maps);
		
		$("#btn_close").removeClass("hide");
		
		if(evento.aceitandoTrabalhos === true) {
			$("#aceitando_paper").addClass( "alert alert-success alert-dismissible" )
								 .append("Uhul !!!!! Este evento está aceitando Papers. <a href='paper.html' class='alert-link'>Faça o seu</a>");
		} else {
			$("#aceitando_paper").addClass( "alert alert-danger alert-dismissible" ).append("Ops !!! Já encerramos os papers :(");
		}
		
		$("#nome_evento_id").append( "<h1>" + evento.nome + "</h1>" );
		
		preenchePapers($.id);
		
		$("#descricao_id").append( evento.descricao );
		
		$("#data_inicio_id").append( evento.dataInicio );
		$("#data_fim_id").append( evento.dataFim );
		
		$("#local_id").append( evento.local );
		
		$("#site").attr( "href", evento.url).append(evento.url);
		
	}
	
	function preenchePapers(id) {
		
		var papers = EventoResource.listaPapersPorEvento({eventoId:id});
		
		$.each(papers, function(key, value) {
			var accordion = $("#accordion_hide").clone();
			var id_paper = value.id;
			
			accordion.removeClass("hide");
			accordion.attr('id', 'accordion');
			
			accordion.find("#titulo_id")
						.attr( 'href', '#'+ id_paper )
						.attr('id','id_' + id_paper)
						.append( value.titulo );
			
			if(value.aceito === true) {
				accordion.find("#aceito_id").addClass("label label-success").append("ACEITO");
			} else {
				accordion.find("#aceito_id").addClass("label label-danger").append("NÃO ACEITO");
			}
			
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
	
	function readyURL(param) {
		var results = new RegExp('[\?&amp;]' + param + '=([^&amp;#]*)').exec(window.location.href);
		if(results !== null)
			return results[1] || 0;
	}
	
});