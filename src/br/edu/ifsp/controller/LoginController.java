package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.LoginDao;
import br.edu.ifsp.model.atendente.Atendente;

public class LoginController {
	private Atendente atendente;
	private List<String> erros;

	public List<String> insereLogin(String nome, String cpf, String login, String senha) {
		recebeDadosLogin(null, nome, cpf, login, senha);

		if (erros.size() == 0)
			erros.add(new LoginDao().insereLogin(atendente));

		return erros;
	}

	public void recebeDadosLogin(Integer idAtendente, String nome, String cpf, String login, String senha) {
		atendente = new Atendente();
		erros = new ArrayList<String>();

		atendente.setIdAtendente(idAtendente);
		atendente.setNome(nome);
		atendente.setCPF(cpf);
		atendente.setLogin(login);
		atendente.setSenha(senha);

	}

	public String getExcecao() {
		return new LoginDao().getExcecao();
	}

	public List<Atendente> consultaLogins() {
		return new LoginDao().consultaLogins();
	}

	public List<String> alteraLogin(Integer id, String nome, String cpf, String login, String senha) {
		recebeDadosLogin(id, nome, cpf, login, senha);

		if (erros.size() == 0)
			erros.add(new LoginDao().alteraLogin(atendente));

		return erros;
	}

	public String excluiLogin(Integer id) {
		String erro = new LoginDao().excluiLogin(id);
		return erro;
	}

	public Atendente fazerLogin(String login, String senha) {
		return new LoginDao().fazerLogin(login, senha);
	}
}
