package br.edu.ifsp.model.medico;

public class Medico {
	private Integer idMedico;
	private String nome;
	private String CRM;
	private String DataNasc;
	private String CPF;
	private Character sexo;
	private String especialidade;

	public Integer getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Integer idMedico) {
		this.idMedico = idMedico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCRM() {
		return CRM;
	}

	public void setCRM(String cRM) {
		CRM = cRM;
	}

	public String getDataNasc() {
		return DataNasc;
	}

	public void setDataNasc(String dataNasc) {
		DataNasc = dataNasc;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
