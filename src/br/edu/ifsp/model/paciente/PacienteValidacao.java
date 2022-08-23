package br.edu.ifsp.model.paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteValidacao {
	private static List<String> errosValidacao;

	public static List<String> validaPaciente(Paciente paciente) {
		errosValidacao = new ArrayList<>();

		if (!paciente.getNome().equals("")) {
			if (paciente.getNome().length() < 5 || paciente.getNome().length() > 100)
				errosValidacao.add("* O nome deve ter entre 5 e 100 caracteres.");
		} else {
			errosValidacao.add("* O nome não foi informado.");
		}

		return errosValidacao;
	}
}
