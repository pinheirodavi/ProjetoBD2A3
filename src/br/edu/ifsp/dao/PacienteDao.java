package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.paciente.Paciente;

public class PacienteDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;

	public String inserePaciente(Paciente paciente) {
		instrucaoSql = ("INSERT INTO PACIENTE (Nome, CPF, DataNasc, Sexo, PlanoDeSaude) VALUES (?, ?, ?, ?, ?)");
		return insereAlteraExclui(instrucaoSql, paciente.getNome(), paciente.getCPF(), paciente.getDatanasc(),
				paciente.getSexo().toString(), paciente.getPlanodeSaude());
	}

	public String getExcecao() {
		return excecao;
	}

	public List<Paciente> consultaPacientes() {
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
						paciente.setIdPaciente(registros.getInt("idPaciente"));
						paciente.setNome(registros.getString("Nome"));
						paciente.setCPF(registros.getString("CPF"));
						paciente.setDatanasc(registros.getString("DataNasc"));
						paciente.setSexo(registros.getString("Sexo").charAt(0));
						paciente.setPlanodeSaude(registros.getString("PlanoDeSaude"));
						pacientes.add(paciente);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			pacientes = null;
		}
		return pacientes;
	}

	public String alteraPaciente(Paciente paciente) {
		instrucaoSql = "UPDATE PACIENTE SET Nome = ?, CPF = ?, DataNasc = ?, Sexo = ?, PlanoDeSaude = ? WHERE IdPaciente = ?";
		return insereAlteraExclui(instrucaoSql, paciente.getNome(), paciente.getCPF(), paciente.getDatanasc(),
				paciente.getSexo().toString(), paciente.getPlanodeSaude(), paciente.getIdPaciente());
	}

	public String excluiPaciente(int id) {
		instrucaoSql = "DELETE FROM PACIENTE WHERE IdPaciente = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
