package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.atendente.Atendente;

public class LoginDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;

	public String insereLogin(Atendente atendente) {
		instrucaoSql = ("INSERT INTO Atendente (Nome, CPF, Login, Senha) VALUES (?, ?, ?, ?)");
		return insereAlteraExclui(instrucaoSql, atendente.getNome(), atendente.getCPF(), atendente.getLogin(),
				atendente.getSenha());
	}

	public String getExcecao() {
		return excecao;
	}

	public List<Atendente> consultaLogins() {
		Atendente atendente;
		List<Atendente> atendentes = new ArrayList<Atendente>();
		instrucaoSql = "SELECT * FROM Atendente";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						atendente = new Atendente();
						atendente.setIdAtendente(registros.getInt("IdAtendente"));
						atendente.setNome(registros.getString("Nome"));
						atendente.setCPF(registros.getString("CPF"));
						atendente.setLogin(registros.getString("Login"));
						atendente.setSenha(registros.getString("Senha"));
						atendentes.add(atendente);
					}
				}
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			atendentes = null;
		}
		return atendentes;
	}

	public String alteraLogin(Atendente atendente) {
		instrucaoSql = "UPDATE Atendente SET Nome = ?, CPF = ?, Login = ?, Senha = ? WHERE idAtendente = ?";
		return insereAlteraExclui(instrucaoSql, atendente.getNome(), atendente.getCPF(), atendente.getLogin(),
				atendente.getSenha(), atendente.getIdAtendente());
	}
	
	public String excluiLogin(int id) {
		instrucaoSql = "DELETE FROM Atendente WHERE idAtendente = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
	
	public Atendente fazerLogin(String login, String senha) {
		Atendente atendente = null;
		instrucaoSql = "SELECT * FROM Atendente WHERE login = ? and senha = ?";
		try {
			excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				comando.setObject(1, login);
				comando.setObject(2, senha);
				
				registros = comando.executeQuery();
				
				if(registros.next()) {
					registros.beforeFirst();
					registros.next();
					atendente = new Atendente();
					atendente.setIdAtendente(registros.getInt("IdAtendente"));
					atendente.setNome(registros.getString("Nome"));
					atendente.setCPF(registros.getString("CPF"));
					atendente.setLogin(registros.getString("Login"));
					atendente.setSenha(registros.getString("Senha"));
				}
				else 
					atendente = null;
				
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
		}
		return atendente;
	}
}
