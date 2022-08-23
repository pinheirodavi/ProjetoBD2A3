package br.edu.ifsp.view.consulta;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.consulta.Consulta;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ConsultaModeloTabela extends AbstractTableModel {
	private String[] colunas = { "Código", "Paciente", "Médico", "Data da Consulta", "Atendente" };
	private List<Consulta> consultas;
	private List<Paciente> pacientes;
	private List<Medico> medicos;
	private List<Atendente> atendentes;

	public ConsultaModeloTabela() {
	}

	public ConsultaModeloTabela(List<Consulta> consultas, List<Paciente> pacientes, List<Medico> medicos,
			List<Atendente> atendentes) {
		this.consultas = consultas;
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
		if (consultas != null)
			return consultas.size();
		return 0;
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return getValueAt(0, coluna).getClass();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Consulta consulta = consultas.get(linha);
		Object valor = null;

		switch (coluna) {
		case 0:
			valor = consulta.getIdConsulta();
			break;
		case 1:
			if (pacientes != null)
				for (Paciente p : pacientes)
					if (p.getIdPaciente() == consulta.getPaciente().getIdPaciente())
						valor = p;
			break;
		case 2:
			if (medicos != null)
				for (Medico m : medicos)
					if (m.getIdMedico() == consulta.getMedico().getIdMedico())
						valor = m;
			break;
		case 3:
			valor = consulta.getDataConsulta();
			break;
		case 4:
			if (atendentes != null)
				for (Atendente a : atendentes)
					if (a.getIdAtendente() == consulta.getAtendente().getIdAtendente())
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
			consultas.get(linha).setPaciente((Paciente) valor);
			break;
		case 2:
			consultas.get(linha).setMedico((Medico) valor);
			break;
		case 3:
			consultas.get(linha).setDataConsulta(valor.toString());
			break;
		case 4:
			consultas.get(linha).setAtendente((Atendente) valor);
			break;
		}

		fireTableCellUpdated(linha, coluna);
	}

	public void removeConsultaTabela(int linha) {
		consultas.remove(linha);

		fireTableRowsDeleted(linha, linha);
	}
}
