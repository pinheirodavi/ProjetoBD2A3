package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.medico.Medico;

public class MedicoDao extends GenericDao {
	private String instrucaoSql;
	private PreparedStatement comando;
	private ResultSet registros;
	private static String excecao = null;

	public String insereMedico(Medico medico) {
		instrucaoSql = ("INSERT INTO MEDICO (Nome, CRM, DataNasc, CPF, Sexo, Especialidade) VALUES (?, ?, ?, ?, ?, ?)");
		return insereAlteraExclui(instrucaoSql, medico.getNome(), medico.getCRM(), medico.getDataNasc(),
				medico.getCPF(), medico.getSexo().toString(), medico.getEspecialidade());
	}

	public String getExcecao() {
		return excecao;
	}

	public List<Medico> consultaMedicos() {
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
						medico.setIdMedico(registros.getInt("idMedico"));
						medico.setNome(registros.getString("Nome"));
						medico.setCRM(registros.getString("CRM"));
						medico.setDataNasc(registros.getString("DataNasc"));
						medico.setCPF(registros.getString("CPF"));
						medico.setSexo(registros.getString("Sexo").charAt(0));
						medico.setEspecialidade(registros.getString("Especialidade"));
						medicos.add(medico);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			medico = null;
		}
		return medicos;
	}

	public String alteraMedico(Medico medico) {
		instrucaoSql = "UPDATE MEDICO SET Nome = ?, CRM = ?, DataNasc = ?, CPF = ?, Sexo = ?, Especialidade = ? WHERE idMedico = ?";
		return insereAlteraExclui(instrucaoSql, medico.getNome(), medico.getCRM(), medico.getDataNasc(),
				medico.getCPF(), medico.getSexo().toString(), medico.getEspecialidade(), medico.getIdMedico());
	}

	public String excluiMedico(int id) {
		instrucaoSql = "DELETE FROM MEDICO WHERE idMedico = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
