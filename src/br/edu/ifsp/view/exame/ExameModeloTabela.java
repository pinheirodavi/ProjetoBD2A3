package br.edu.ifsp.view.exame;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.exame.Exame;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ExameModeloTabela extends AbstractTableModel {
	private String[] colunas = { "Código", "Data do Exame", "Descrição", "Paciente", "Médico", "Atendente" };
	private List<Exame> exames;
	private List<Paciente> pacientes;
	private List<Medico> medicos;
	private List<Atendente> atendentes;

	public ExameModeloTabela() {
	}

	public ExameModeloTabela(List<Exame> exames, List<Paciente> pacientes, List<Medico> medicos,
			List<Atendente> atendentes) {
		this.exames = exames;
		this.pacientes = pacientes;
		this.medicos = medicos;
		this.atendentes = atendentes;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}

	@Override
	public int getRowCount() {
		if (exames != null)
			return exames.size();
		return 0;
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return getValueAt(0, coluna).getClass();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Exame exame = exames.get(linha);
		Object valor = null;

		switch (coluna) {
		case 0:
			valor = exame.getIdExame();
			break;
		case 1:
			valor = exame.getDataExame();
			break;
		case 2:
			valor = exame.getDescricaoExame();
			break;
		case 3:
			if (pacientes != null)
				for (Paciente p : pacientes)
					if (p.getIdPaciente() == exame.getPaciente().getIdPaciente())
						valor = p;
			break;
		case 4:
			if (medicos != null)
				for (Medico m : medicos)
					if (m.getIdMedico() == exame.getMedico().getIdMedico())
						valor = m;
			break;
		case 5:
			if (atendentes != null)
				for (Atendente a : atendentes)
					if (a.getIdAtendente() == exame.getAtendente().getIdAtendente())
						valor = a;
			break;
		}
		return valor;
	}

	@Override
	public boolean isCellEditable(int linha, int coluna) {
		return false;
	}

	public void setValueAt(Object valor, int linha, int coluna) {
		switch (coluna) {
		case 1:
			exames.get(linha).setDataExame(valor.toString());
			break;
		case 2:
			exames.get(linha).setDescricaoExame(valor.toString());
			break;
		case 3:
			exames.get(linha).setPaciente((Paciente) valor);
			break;
		case 4:
			exames.get(linha).setMedico((Medico) valor);
			break;
		case 5:
			exames.get(linha).setAtendente((Atendente) valor);
			break;
		}
		fireTableCellUpdated(linha, coluna);
	}
	
	public void removeExameTabela(int linha) {
		exames.remove(linha);
		
		fireTableRowsDeleted(linha, linha);
	}
}
