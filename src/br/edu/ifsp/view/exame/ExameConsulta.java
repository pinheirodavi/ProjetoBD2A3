package br.edu.ifsp.view.exame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import br.edu.ifsp.controller.ExameController;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.exame.Exame;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ExameConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbExame;
	private ExameModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	private Atendente atendente;
	
	public ExameConsulta(Atendente atendente) {
		this.atendente = atendente;
		setTitle("Consulta de Exames");
		setSize(780, 320);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoExames = null;
		String excecaoPacientes = null;
		String excecaoMedicos = null;
		String excecaoAtendentes = null;

		List<Exame> exames = new ExameController().consultaExames();
		excecaoExames = new ExameController().getExcecao();

		List<Paciente> pacientes = new ExameController().recuperaPacientes();
		excecaoPacientes = new ExameController().getExcecao();

		List<Medico> medicos = new ExameController().recuperaMedicos();
		excecaoMedicos = new ExameController().getExcecao();

		List<Atendente> atendentes = new ExameController().recuperaAtendentes();
		excecaoAtendentes = new ExameController().getExcecao();
		if (excecaoExames != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de pacientes:\n" + excecaoPacientes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ExameModeloTabela();
		} else if (excecaoPacientes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de pacientes:\n" + excecaoPacientes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ExameModeloTabela();
		} else if (excecaoMedicos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados de médicos:\n" + excecaoMedicos,
					"Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new ExameModeloTabela();
		} else if (excecaoAtendentes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de atendentes:\n" + excecaoAtendentes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ExameModeloTabela();
		} else
			mtTabela = new ExameModeloTabela(exames, pacientes, medicos, atendentes);

		lbTitulo = new JLabel("Consulta de Exames");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		tbExame = new JTable(mtTabela);
		spTabela = new JScrollPane(tbExame);

		tbExame.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbExame.getColumnModel().getColumn(0).setPreferredWidth(48);
		tbExame.getColumnModel().getColumn(1).setPreferredWidth(99);
		tbExame.getColumnModel().getColumn(2).setPreferredWidth(130);
		tbExame.getColumnModel().getColumn(3).setPreferredWidth(160);
		tbExame.getColumnModel().getColumn(4).setPreferredWidth(160);
		tbExame.getColumnModel().getColumn(5).setPreferredWidth(120);

		tbExame.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbExame.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		tbExame.getColumnModel().getColumn(1).setCellRenderer(dtcrCentro);
		tbExame.getColumnModel().getColumn(2).setCellRenderer(dtcrCentro);
		tbExame.getColumnModel().getColumn(3).setCellRenderer(dtcrCentro);
		tbExame.getColumnModel().getColumn(4).setCellRenderer(dtcrCentro);
		tbExame.getColumnModel().getColumn(5).setCellRenderer(dtcrCentro);

		tbExame.getTableHeader().setReorderingAllowed(false);
		tbExame.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(215, 10, 300, 25);
		spTabela.setBounds(20, 40, 720, 182);
		btAlterar.setBounds(215, 240, 100, 25);
		btExcluir.setBounds(459, 240, 100, 25);

		cp.add(lbTitulo);
		cp.add(spTabela);
		cp.add(btAlterar);
		cp.add(btExcluir);

		btAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAlterarAction();
			}
		});

		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btExcluirAction();
			}
		});
	}

	public void btAlterarAction() {
		if (tbExame.getSelectedRow() != -1) {
			int linhaSelecionada = tbExame.getSelectedRow();

			int id = Integer.parseInt(tbExame.getModel().getValueAt(linhaSelecionada, 0).toString());
			String dataExame = tbExame.getModel().getValueAt(linhaSelecionada, 1).toString();
			String descricao = tbExame.getModel().getValueAt(linhaSelecionada, 2).toString();
			String paciente = tbExame.getModel().getValueAt(linhaSelecionada, 3).toString();
			String medico = tbExame.getModel().getValueAt(linhaSelecionada, 4).toString();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() { new ExameAlteracao(id, dataExame, descricao, paciente, medico, atendente, linhaSelecionada, mtTabela).setVisible(true);}
				
			});
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um Exame.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void btExcluirAction() {
		if (tbExame.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				int linhaSelecionada = tbExame.getSelectedRow();

				int id = Integer.parseInt(tbExame.getModel().getValueAt(linhaSelecionada, 0).toString());
				String erro = "";

				erro = new ExameController().excluiExame(id);

				if (erro == null) {
					JOptionPane.showMessageDialog(this, "Exame excluído com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeExameTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o Exame: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação",
						JOptionPane.INFORMATION_MESSAGE);
		} else
			JOptionPane.showMessageDialog(this, "Selecione um Exame.", "Mensagem", JOptionPane.WARNING_MESSAGE);
	}
}
