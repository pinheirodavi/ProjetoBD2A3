package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.consulta.Consulta;
import br.edu.ifsp.model.paciente.Paciente;
import br.edu.ifsp.model.medico.Medico;

public class ConsultaDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;

	public String insereConsulta(Consulta consulta) {
		instrucaoSql = ("INSERT INTO CONSULTA (idPaciente, idMedico, DataConsulta, idAtendente) VALUES (?, ?, ?, ?)");
		return insereAlteraExclui(instrucaoSql, consulta.getPaciente().getIdPaciente(),
				consulta.getMedico().getIdMedico(), consulta.getDataConsulta(),
				consulta.getAtendente().getIdAtendente());
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

	public List<Consulta> consultaConsultas() {
		Consulta consulta;
		Paciente paciente;
		Medico medico;
		Atendente atendente;
		List<Consulta> consultas = new ArrayList<Consulta>();
		instrucaoSql = "SELECT * FROM CONSULTA";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						consulta = new Consulta();
						consulta.setIdConsulta(registros.getInt("IdConsulta"));
						consulta.setDataConsulta(registros.getString("DataConsulta"));

						paciente = new Paciente();
						paciente.setIdPaciente(registros.getInt("IdPaciente"));
						consulta.setPaciente(paciente);

						medico = new Medico();
						medico.setIdMedico(registros.getInt("IdMedico"));
						consulta.setMedico(medico);

						atendente = new Atendente();
						atendente.setIdAtendente(registros.getInt("IdAtendente"));
						consulta.setAtendente(atendente);

						consultas.add(consulta);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			consultas = null;
		}
		return consultas;
	}

	public String alteraConsulta(Consulta consulta) {
		instrucaoSql = "UPDATE CONSULTA SET idPaciente = ?, idMedico = ?, DataConsulta = ?, idAtendente = ? WHERE idConsulta = ?";
		return insereAlteraExclui(instrucaoSql, consulta.getPaciente().getIdPaciente(),
				consulta.getMedico().getIdMedico(), consulta.getDataConsulta(),
				consulta.getAtendente().getIdAtendente(), consulta.getIdConsulta());
	}

	public String excluiConsulta(int id) {
		instrucaoSql = "DELETE FROM CONSULTA WHERE IdConsulta = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
