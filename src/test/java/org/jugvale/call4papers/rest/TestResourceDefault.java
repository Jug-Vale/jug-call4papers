package org.jugvale.call4papers.rest;


public interface TestResourceDefault {

	public void setUp() throws Exception;

	/*
	 * STATUS 200 OK
	 */
	public void deveRetornarOkAoBuscarLista();

	public void deveRetornarOkAoBuscarPorId();
	
	public void deveRetornar201AoCadastrar();
	
	public void deveRetornarOkAoApagar();
	
	public void deveRetornarOkAoAtualizar();

	/*
	 * STATUS 404 NOT FOUND
	 */
	public void deveRetornarNotFoundAoBuscarPorId();
	
	public void deveRetornarNotFoundAoApagar();
	
	public void deveRetornarNotFoundAoAtualizar();

	/*
	 * STATUS 401 UNAUTHORIZED
	 */
	public void deveRetornarUnauthorizedAoCadastrar();

	public void deveRetornarStatusUnauthorizedAoAtualizar();

	public void deveRetornarStatusUnauthorizedAoApagar();

}