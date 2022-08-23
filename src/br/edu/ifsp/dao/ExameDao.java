package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.exame.Exame;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

public class ExameDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;

	public String insereExame(Exame exame) {
		instrucaoSql = ("INSERT INTO EXAME (DataExame, Descricao, idPaciente, idMedico, idAtendente) VALUES (?, ?, ?, ?, ?)");
		return insereAlteraExclui(instrucaoSql, exame.getDataExame(), exame.getDescricaoExame(),
				exame.getPaciente().getIdPaciente(), exame.getMedico().getIdMedico(),
				exame.getAtendente().getIdAtendente());
	}

	public List<Paciente> recuperaPacientes() {
		Paciente paciente;
		List<Paciente> pacientes = new ArrayList<Paciente>();
		instrucaoSql = "SELECT * FROM PACIENTE";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						paciente = new Paciente();
						paciente.setIdPaciente(registros.getInt("IdPaciente"));
						paciente.setNome(registros.getString("Nome"));
						pacientes.add(paciente);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			pacientes = null;
		}
		return pacientes;
	}

	public List<Medico> recuperaMedicos() {
		Medico medico;
		List<Medico> medicos = new ArrayList<Medico>();
		instrucaoSql = "SELECT * FROM MEDICO";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						medico = new Medico();
						medico.setIdMedico(registros.getInt("IdMedico"));
						medico.setNome(registros.getString("Nome"));
						medicos.add(medico);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			medicos = null;
		}

		return medicos;
	}

	public List<Atendente> recuperaAtendentes() {
		Atendente atendente;
		List<Atendente> atendentes = new ArrayList<Atendente>();
		instrucaoSql = "SELECT * FROM ATENDENTE";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						atendente = new Atendente();
						atendente.setIdAtendente(registros.getInt("idAtendente"));
						atendente.setNome(registros.getString("Nome"));
						atendentes.add(atendente);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			atendentes = null;
		}

		return atendentes;
	}

	public String getExcecao() {
		return excecao;
	}

	public List<Exame> consultaExames() {
		Exame exame;
		Paciente paciente;
		Medico medico;
		Atendente atendente;
		List<Exame> exames = new ArrayList<Exame>();
		instrucaoSql = "SELECT * FROM EXAME";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();
				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						exame = new Exame();
						exame.setIdExame(registros.getInt("IdExame"));
						exame.setDataExame(registros.getString("DataExame"));
						exame.setDescricaoExame(registros.getString("Descricao"));

						paciente = new Paciente();
						paciente.setIdPaciente(registros.getInt("IdPaciente"));
						exame.setPaciente(paciente);

						medico = new Medico();
						medico.setIdMedico(registros.getInt("IdMedico"));
						exame.setMedico(medico);

						atendente = new Atendente();
						atendente.setIdAtendente(registros.getInt("IdAtendente"));
						exame.setAtendente(atendente);

						exames.add(exame);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			exames = null;
		}
		return exames;
	}

	public String alteraExame(Exame exame) {
		instrucaoSql = "UPDATE EXAME SET DataExame = ?, Descricao = ?, IdPaciente = ?, IdMedico = ?, IdAtendente = ? WHERE IdExame = ?";
		return insereAlteraExclui(instrucaoSql, exame.getDataExame(), exame.getDescricaoExame(),
				exame.getPaciente().getIdPaciente(), exame.getMedico().getIdMedico(),
				exame.getAtendente().getIdAtendente(), exame.getIdExame());
	}
	
	public String excluiExame(int id) {
		instrucaoSql = "DELETE FROM EXAME WHERE IdExame = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
