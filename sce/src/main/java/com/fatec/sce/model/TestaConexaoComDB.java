package com.fatec.sce.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.fatec.sce.model.ConfiguraDB;
import com.fatec.sce.model.FabricaDeConexoes;

public class TestaConexaoComDB {
	/**
	 * Objetivo - verificar o comportamento do sistema na conexao ao DB com
	 * configuracao valida Pré-condição - a configuracao default da fabrica de
	 * conexoes é valida
	 */
	@Test
	public void quandoConectaComOBancoRetornaOK() {
		// cenario
		FabricaDeConexoes fabrica;
		// acao
		fabrica = new FabricaDeConexoes();
		// verificacao
		assertNotNull(fabrica.getConnection());
	}
	
	/**
	 * Objetivo - verificar o comportamento do sistema na conexao ao DB com usuario de
	 * acesso invalida Pré-condição - o usuario cadastrado é "root"
	 */
	@Test
	public void quandoConectaComSenhaInvalida_SQLException() {
		// cenario
		String url = "jdbc:mysql://localhost:3306/biblioteca";
		String driver = "com.mysql.jdbc.Driver";
		String usuario = "root"; 
		String senha = "1as"; // senha errada
		FabricaDeConexoes fabricaDeConexoes = null;
		ConfiguraDB configuraDB = new ConfiguraDB(url, driver, usuario, senha);
		fabricaDeConexoes = new FabricaDeConexoes(configuraDB);
		try {
			// acao
			fabricaDeConexoes.getConnection();
			fail("deveria falhar");
		} catch (Exception e) {
			// verificacao
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(),"java.sql.SQLException: Access denied for user 'root'@'localhost'(using password: YES)");
		}
	}

	/**
	 * Objetivo - verificar o comportamento do sistema na conexao ao DB com usuario de
	 * acesso invalida Pré-condição - o usuario cadastrado é "root"
	 */
	@Test
	public void quandoConectaComUsuarioInvalido_SQLException() {
		// cenario
		String url = "jdbc:mysql://localhost:3306/biblioteca";
		String driver = "com.mysql.jdbc.Driver";
		String usuario = "root1"; // usuario errado
		String senha = ""; 
		FabricaDeConexoes fabricaDeConexoes = null;
		ConfiguraDB configuraDB = new ConfiguraDB(url, driver, usuario, senha);
		fabricaDeConexoes = new FabricaDeConexoes(configuraDB);
		try {
			// acao
			fabricaDeConexoes.getConnection();
			fail("deveria falhar");
		} catch (Exception e) {
			// verificacao
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(),"java.sql.SQLException: Access denied for user 'root1'@'localhost'(using password: YES)");
		}
	}

	/**
	 * Objetivo - verificar o comportamento do sistema na conexao ao DB com driver de
	 * acesso invalido Pré-condição - o driver cadastrado é "com.mysql.jdbc.Driver"
	 */
	@Test
	public void quandoConectaComDriverInvalido_SQLException() {
		// cenario
		String url = "jdbc:mysql://localhost:3306/biblioteca";
		String driver = "commysqljdbc.Driver"; // driver errado
		String usuario = "root";
		String senha = ""; 
		FabricaDeConexoes fabricaDeConexoes = null;
		ConfiguraDB configuraDB = new ConfiguraDB(url, driver, usuario, senha);
		fabricaDeConexoes = new FabricaDeConexoes(configuraDB);
		try {
			// acao
			fabricaDeConexoes.getConnection();
			fail("deveria falhar");
		} catch (Exception e) {
			// verificacao
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(),"java.lang.ClassNotFoundException: commysqljdbc.Driver");
		}
	}

	/**
	 * Objetivo - verificar o comportamento do sistema na conexao ao DB com porta de
	 * acesso invalida Pré-condição - a porta cadastrada é "3306"
	 */
	@Test
	public void quandoConectaComCommunicationInvalido_SQLException() {
		// cenario
		String url = "jdbc:mysql://localhost:3310/biblioteca"; // porta errada
		String driver = "com.mysql.jdbc.Driver";
		String usuario = "root";
		String senha = "";
		FabricaDeConexoes fabricaDeConexoes = null;
		ConfiguraDB configuraDB = new ConfiguraDB(url, driver, usuario, senha);
		fabricaDeConexoes = new FabricaDeConexoes(configuraDB);
		try {
			// acao
			fabricaDeConexoes.getConnection();
			fail("deveria falhar");
		} catch (Exception e) {
			// verificacao
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(),"com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure\r\n" + 
					"\r\n" + 
					"The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.");
		}
	}
}
