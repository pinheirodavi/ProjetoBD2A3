package br.edu.ifsp.model.consulta;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

public class Consulta {
	private Integer idConsulta;
	private Paciente paciente;
	private Medico medico;
	private Atendente atendente;
	private String dataConsulta;

	public Integer getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Atendente getAtendente() {
		return atendente;
	}

	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}

	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

}
