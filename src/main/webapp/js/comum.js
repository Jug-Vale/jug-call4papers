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