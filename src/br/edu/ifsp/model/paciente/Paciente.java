package br.edu.ifsp.model.paciente;

public class Paciente {
	private Integer idPaciente;
	private String nome;
	private String CPF;
	private String datanasc;
	private Character sexo;
	private String planodeSaude;

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getDatanasc() {
		return datanasc;
	}

	public void setDatanasc(String datanasc) {
		this.datanasc = datanasc;
	}

	public String getPlanodeSaude() {
		return planodeSaude;
	}

	public void setPlanodeSaude(String planodeSaude) {
		this.planodeSaude = planodeSaude;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
