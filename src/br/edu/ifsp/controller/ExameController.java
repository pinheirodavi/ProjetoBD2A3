package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.ExameDao;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.exame.Exame;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

public class ExameController {
	private Exame exame;
	private List<String> erros;

	public List<String> insereExame(String dataExame, String descricao, Paciente paciente, Medico medico,
			Atendente atendente) {
		recebeDadosExames(null, dataExame, descricao, paciente, medico, atendente);

		if (erros.size() == 0)
			erros.add(new ExameDao().insereExame(exame));

		return erros;
	}

	public void recebeDadosExames(Integer idExame, String dataExame, String descricao, Paciente paciente, Medico medico,
			Atendente atendente) {
		exame = new Exame();
		erros = new ArrayList<String>();

		exame.setIdExame(idExame);
		exame.setDataExame(dataExame);
		exame.setDescricaoExame(descricao);
		exame.setPaciente(paciente);
		exame.setMedico(medico);
		exame.setAtendente(atendente);
	}

	public List<Paciente> recuperaPacientes() {
		return new ExameDao().recuperaPacientes();
	}

	public List<Medico> recuperaMedicos() {
		return new ExameDao().recuperaMedicos();
	}

	public List<Atendente> recuperaAtendentes() {
		return new ExameDao().recuperaAtendentes();
	}

	public String getExcecao() {
		return new ExameDao().getExcecao();
	}

	public List<Exame> consultaExames() {
		return new ExameDao().consultaExames();
	}

	public List<String> alteraExame(Integer idExame, String dataExame, String descricao, Paciente paciente,
			Medico medico, Atendente atendente) {
		recebeDadosExames(idExame, dataExame, descricao, paciente, medico, atendente);

		if (erros.size() == 0)
			erros.add(new ExameDao().alteraExame(exame));

		return erros;
	}

	public String excluiExame(Integer id) {
		String erro = new ExameDao().excluiExame(id);
		return erro;
	}
}
