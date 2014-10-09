/**
 * Variáveis Globais
 * 
*/
var PAGINA_404 = "./naoEncontrado.html"
var CLASSE_CSS_ERRO_FORM = "has-error";
var CLASSE_CSS_SUCESSO_FORM = "has-success";

var CLASSE_CSS_SUBMISSAO_PROBLEMA = "alert alert-danger alert-dismissible";
var CLASSE_CSS_SUBMISSAO_SUCESSO = "alert alert-success alert-dismissible";

/**
 * Funções comuns
 * 
 */
function readURLParam(param) {
	var results = new RegExp('[\?&amp;]' + param + '=([^&amp;#]*)')
			.exec(window.location.href);
	if (results !== null)
		return results[1] || 0;
}

function naoEncontrado() {
	window.location = PAGINA_404;
}