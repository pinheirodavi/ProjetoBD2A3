package br.edu.ifsp.view.medico;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.medico.Medico;

@SuppressWarnings("serial")
public class MedicoModeloTabela extends AbstractTableModel {
	private String[] colunas = { "Código", "Nome", "CRM", "Data Nasc.", "CPF", "Sexo", "Especialidade" };
	private List<Medico> medicos;

	public MedicoModeloTabela() {
	}

	public MedicoModeloTabela(List<Medico> medicos) {
		this.medicos = medicos;
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
		if (medicos != null)
			return medicos.size();
		return 0;
	}

	/*
	 * @Override public Class<?> getColumnClass(int coluna) { return getValueAt(0,
	 * coluna).getClass(); }
	 */

	@Override
	public Object getValueAt(int linha, int coluna) {
		Medico medico = medicos.get(linha);
		Object valor = null;

		switch (coluna) {
		case 0:
			valor = medico.getIdMedico();
			break;
		case 1:
			valor = medico.getNome();
			break;
		case 2:
			valor = medico.getCRM();
			break;
		case 3:
			valor = medico.getDataNasc();
			break;
		case 4:
			valor = medico.getCPF();
			break;
		case 5:
			if (medico.getSexo() == 'M')
				valor = "Masculino";
			else
				valor = "Feminino";
			break;
		case 6:
			valor = medico.getEspecialidade();
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
			medicos.get(linha).setNome(valor.toString());
			break;
		case 2:
			medicos.get(linha).setCRM(valor.toString());
			break;
		case 3:
			medicos.get(linha).setDataNasc(valor.toString());
			break;
		case 4:
			medicos.get(linha).setCPF(valor.toString());
			break;
		case 5:
			medicos.get(linha).setSexo(valor.toString().charAt(0));
			break;
		case 6:
			medicos.get(linha).setEspecialidade(valor.toString());
			break;
		}

		fireTableCellUpdated(linha, coluna);
	}

	public void removeMedicoTabela(int linha) {
		medicos.remove(linha);

		fireTableRowsDeleted(linha, linha);
	}
}
