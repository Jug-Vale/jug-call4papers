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
 * 
 * REGEX para validação
 * */

var VALIDACAO_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var VALIDACAO_SITE = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;

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

function haErrosNosForms() {
	var temErro = false;
	var funcaoVerificaCampos = function () {
		var el = $(this);
		el.find("input[type=text], textarea, input[type=tel]").each(function () {
			if(validar(this)) {
				el.removeClass(CLASSE_CSS_ERRO_FORM).addClass(CLASSE_CSS_SUCESSO_FORM);
			} else {
				el.addClass(CLASSE_CSS_ERRO_FORM);
				temErro = true;
			}
		});
	};
	$(".form-group").each(funcaoVerificaCampos);
	return temErro;
}

function validar (el) {
	var validador = /.+/;
	if(el.id == 'input_email' || el.id == 'email') {
		validador = VALIDACAO_EMAIL;
	}
	if (el.id == 'input_site') {
		validador = VALIDACAO_SITE;
	}
	return validador.test(el.value);
}

function limpaCamposForm() {
	$(".form-group").each(function (){
		$(this).removeClass(CLASSE_CSS_SUCESSO_FORM)
			.removeClass(CLASSE_CSS_ERRO_FORM)
	    	.find("input[type=text], textarea, input[type=tel]").each(function () {
	    	this.value = "";
	    })
	});
}

function converteData(dataStr) {
	return new Date(dataStr.substring(6, 10), parseInt(dataStr.substring(3, 5)) - 1, dataStr.substring(0, 2), dataStr.substring(11, 13), dataStr.substring(14, 16));
}
