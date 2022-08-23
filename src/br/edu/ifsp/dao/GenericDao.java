package br.edu.ifsp.dao;

import java.sql.PreparedStatement;

public class GenericDao {
	private PreparedStatement comando;
	
	protected String insereAlteraExclui(String instrucaoSql, Object... parametros) {
		try {
			String excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				
				for(int i = 0; i<parametros.length;i++) {
					comando.setObject(i + 1, parametros[i]);
				}
				comando.execute();
				
				comando.close();
				
				ConnectionDatabase.getConexaoBd().close();
			} else {
				return excecao;
			}
		} catch(Exception e) {
			return "Tipo de Exceção: " +e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
		}
		return null;
	}
}
