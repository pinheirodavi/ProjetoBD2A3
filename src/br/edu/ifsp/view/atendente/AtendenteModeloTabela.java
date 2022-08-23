package br.edu.ifsp.view.atendente;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.atendente.Atendente;

@SuppressWarnings("serial")
public class AtendenteModeloTabela extends AbstractTableModel {
	private String[] colunas = { "Código", "Nome", "CPF", "Login" };
	private List<Atendente> atendentes;

	public AtendenteModeloTabela() {
	}

	public AtendenteModeloTabela(List<Atendente> atendentes) {
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
		if (atendentes != null)
			return atendentes.size();
		return 0;
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Atendente atendente = atendentes.get(linha);
		Object valor = null;

		switch (coluna) {
		case 0:
			valor = atendente.getIdAtendente();
			break;
		case 1:
			valor = atendente.getNome();
			break;
		case 2:
			valor = atendente.getCPF();
			break;
		case 3:
			valor = atendente.getLogin();
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
			atendentes.get(linha).setNome(valor.toString());
			break;
		case 2:
			atendentes.get(linha).setCPF(valor.toString());
			break;
		case 3:
			atendentes.get(linha).setLogin(valor.toString());
			break;
		}
		
		fireTableCellUpdated(linha, coluna);
	}
	
	public void removeAtendenteTabela(int linha) {
		atendentes.remove(linha);
		
		fireTableRowsDeleted(linha, linha);
	}
}
