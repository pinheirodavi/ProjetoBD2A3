package br.edu.ifsp.view.paciente;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class PacienteModeloTabela extends AbstractTableModel {
	private String[] colunas = { "Código", "Nome", "CPF", "Data Nasc.", "Sexo", "P. Saúde" };
	private List<Paciente> pacientes;

	public PacienteModeloTabela() {
	}

	public PacienteModeloTabela(List<Paciente> pacientes) {
		this.pacientes = pacientes;
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
		if (pacientes != null)
			return pacientes.size();
		return 0;
	}

	/*
	 * @Override public Class<?> getColumnClass(int coluna) { return getValueAt(0,
	 * coluna).getClass(); }
	 */

	@Override
	public Object getValueAt(int linha, int coluna) {
		Paciente paciente = pacientes.get(linha);
		Object valor = null;

		switch (coluna) {
		case 0:
			valor = paciente.getIdPaciente();
			break;
		case 1:
			valor = paciente.getNome();
			break;
		case 2:
			valor = paciente.getCPF();
			break;
		case 3:
			valor = paciente.getDatanasc();
			break;
		case 4:
			if (paciente.getSexo() == 'M')
				valor = "Masculino";
			else
				valor = "Feminino";
			break;
		case 5:
			valor = paciente.getPlanodeSaude();
			break;
		}
		return valor;
	}

	@Override
	public boolean isCellEditable(int linha, int coluna) {
		return false;
	}

	@Override
	public void setValueAt(Object valor, int linha, int coluna) {
		switch (coluna) {
		case 1:
			pacientes.get(linha).setNome(valor.toString());
			break;
		case 2:
			pacientes.get(linha).setCPF(valor.toString());
			break;
		case 3:
			pacientes.get(linha).setDatanasc(valor.toString());
			break;
		case 4:
			pacientes.get(linha).setSexo(valor.toString().charAt(0));
		case 5:
			pacientes.get(linha).setPlanodeSaude(valor.toString());
			break;
		}

		fireTableCellUpdated(linha, coluna);
	}

	public void removePacienteTabela(int linha) {
		pacientes.remove(linha);

		fireTableRowsDeleted(linha, linha);
	}
}
