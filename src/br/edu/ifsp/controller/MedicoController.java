package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.MedicoDao;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.medico.MedicoValidacao;

public class MedicoController {
	private Medico medico;
	private List<String> erros;

	public List<String> insereMedico(String nome, String crm, String dataNasc, String cpf, Character sexo,
			String especialidade) {
		recebeDadosMedico(null, nome, crm, dataNasc, cpf, sexo, especialidade);

		if (erros.size() == 0)
			erros.add(new MedicoDao().insereMedico(medico));

		return erros;
	}

	public void recebeDadosMedico(Integer id, String nome, String crm, String dataNasc, String cpf, Character sexo,
			String especialidade) {
		medico = new Medico();
		erros = new ArrayList<String>();

		medico.setIdMedico(id);
		medico.setNome(nome);
		medico.setCRM(crm);
		medico.setDataNasc(dataNasc);
		medico.setCPF(cpf);
		medico.setSexo(sexo);
		medico.setEspecialidade(especialidade);

		erros = MedicoValidacao.validaMedico(medico);
	}

	public String getExcecao() {
		return new MedicoDao().getExcecao();
	}

	public List<Medico> consultaMedicos() {
		return new MedicoDao().consultaMedicos();
	}

	public List<String> alteraMedico(Integer id, String nome, String crm, String dataNasc, String cpf, Character sexo,
			String especialidade) {
		recebeDadosMedico(id, nome, crm, dataNasc, cpf, sexo, especialidade);

		if (erros.size() == 0)
			erros.add(new MedicoDao().alteraMedico(medico));

		return erros;
	}

	public String excluiMedico(Integer id) {
		String erro = new MedicoDao().excluiMedico(id);
		return erro;
	}
}
