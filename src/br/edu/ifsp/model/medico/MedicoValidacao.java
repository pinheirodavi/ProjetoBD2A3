package br.edu.ifsp.model.medico;

import java.util.ArrayList;
import java.util.List;

public class MedicoValidacao {
	private static List<String> errosValidacao;

	public static List<String> validaMedico(Medico medico) {
		errosValidacao = new ArrayList<>();

		if (!medico.getNome().equals("")) {
			if (medico.getNome().length() < 5 || medico.getNome().length() > 100)
				errosValidacao.add("* O nome deve ter entre 5 e 100 caracteres.");
		} else {
			errosValidacao.add("* O nome não foi informado.");
		}

		return errosValidacao;
	}
}
