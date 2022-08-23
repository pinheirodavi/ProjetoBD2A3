package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.ConsultaDao;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.consulta.Consulta;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

public class ConsultaController {
	private Consulta consulta;
	private List<String> erros;

	public List<String> insereConsulta(Paciente paciente, Medico medico, String dataConsulta, Atendente atendente) {
		recebeDadosConsulta(null, paciente, medico, dataConsulta, atendente);

		if (erros.size() == 0)
			erros.add(new ConsultaDao().insereConsulta(consulta));

		return erros;
	}

	public void recebeDadosConsulta(Integer idConsulta, Paciente paciente, Medico medico, String dataConsulta,
			Atendente atendente) {
		consulta = new Consulta();
		erros = new ArrayList<String>();

		consulta.setIdConsulta(idConsulta);
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);
		consulta.setDataConsulta(dataConsulta);
		consulta.setAtendente(atendente);
	}

	public List<Paciente> recuperaPacientes() {
		return new ConsultaDao().recuperaPacientes();
	}

	public List<Medico> recuperaMedicos() {
		return new ConsultaDao().recuperaMedicos();
	}

	public List<Atendente> recuperaAtendentes() {
		return new ConsultaDao().recuperaAtendentes();
	}

	public String getExcecao() {
		return new ConsultaDao().getExcecao();
	}

	public List<Consulta> consultaConsultas() {
		return new ConsultaDao().consultaConsultas();
	}

	public List<String> alteraConsulta(Integer idConsulta, Paciente paciente, Medico medico, String dataConsulta,
			Atendente atendente) {
		recebeDadosConsulta(idConsulta, paciente, medico, dataConsulta, atendente);

		if (erros.size() == 0)
			erros.add(new ConsultaDao().alteraConsulta(consulta));

		return erros;
	}

	public String excluiConsulta(Integer id) {
		String erro = new ConsultaDao().excluiConsulta(id);
		return erro;
	}
}
