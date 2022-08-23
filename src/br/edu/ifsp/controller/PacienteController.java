package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.PacienteDao;
import br.edu.ifsp.model.paciente.Paciente;
import br.edu.ifsp.model.paciente.PacienteValidacao;

public class PacienteController {
	private Paciente paciente;
	private List<String> erros;

	public List<String> inserePaciente(String nome, String CPF, String datanasc, Character sexo, String planoDeSaude) {
		recebeDadosPaciente(null, nome, CPF, datanasc, sexo, planoDeSaude);

		if (erros.size() == 0)
			erros.add(new PacienteDao().inserePaciente(paciente));

		return erros;
	}

	public void recebeDadosPaciente(Integer id, String nome, String CPF, String datanasc, Character sexo,
			String planoDeSaude) {
		paciente = new Paciente();
		erros = new ArrayList<String>();

		paciente.setIdPaciente(id);
		paciente.setNome(nome);
		paciente.setCPF(CPF);
		paciente.setDatanasc(datanasc);
		paciente.setSexo(sexo);
		paciente.setPlanodeSaude(planoDeSaude);

		erros = PacienteValidacao.validaPaciente(paciente);
	}

	public String getExcecao() {
		return new PacienteDao().getExcecao();
	}

	public List<Paciente> consultaPacientes() {
		return new PacienteDao().consultaPacientes();
	}

	public List<String> alteraPaciente(Integer id, String nome, String CPF, String datanasc, Character sexo,
			String planoDeSaude) {
		recebeDadosPaciente(id, nome, CPF, datanasc, sexo, planoDeSaude);

		if (erros.size() == 0)
			erros.add(new PacienteDao().alteraPaciente(paciente));

		return erros;
	}

	public String excluiPaciente(Integer id) {
		String erro = new PacienteDao().excluiPaciente(id);
		return erro;
	}
}
