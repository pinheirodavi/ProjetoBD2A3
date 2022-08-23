package br.edu.ifsp.view.atendente;

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

import br.edu.ifsp.controller.LoginController;
import br.edu.ifsp.model.atendente.Atendente;

@SuppressWarnings("serial")
public class AtendenteConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbAtendente;
	private AtendenteModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	public AtendenteConsulta() {
		setTitle("Consulta de Atendentes");
		setSize(500, 320);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoAtendentes = null;

		List<Atendente> atendentes = new LoginController().consultaLogins();
		excecaoAtendentes = new LoginController().getExcecao();

		if (excecaoAtendentes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de atendentes:\n" + excecaoAtendentes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new AtendenteModeloTabela();
		} else {
			mtTabela = new AtendenteModeloTabela(atendentes);
		}

		lbTitulo = new JLabel("Consulta de Atendentes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		tbAtendente = new JTable(mtTabela);
		spTabela = new JScrollPane(tbAtendente);

		tbAtendente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbAtendente.getColumnModel().getColumn(0).setPreferredWidth(47);
		tbAtendente.getColumnModel().getColumn(1).setPreferredWidth(165);
		tbAtendente.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbAtendente.getColumnModel().getColumn(3).setPreferredWidth(120);

		tbAtendente.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbAtendente.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		tbAtendente.getColumnModel().getColumn(1).setCellRenderer(dtcrCentro);
		tbAtendente.getColumnModel().getColumn(2).setCellRenderer(dtcrCentro);
		tbAtendente.getColumnModel().getColumn(3).setCellRenderer(dtcrCentro);

		tbAtendente.getTableHeader().setReorderingAllowed(false);
		tbAtendente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(110, 11, 300, 25);
		spTabela.setBounds(20, 40, 435, 182);
		btAlterar.setBounds(77, 240, 100, 25);
		btExcluir.setBounds(293, 240, 100, 25);

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

	private void btAlterarAction() {
		if (tbAtendente.getSelectedRow() != -1) {
			int linhaSelecionada = tbAtendente.getSelectedRow();

			int id = Integer.parseInt(tbAtendente.getModel().getValueAt(linhaSelecionada, 0).toString());
			String nome = tbAtendente.getModel().getValueAt(linhaSelecionada, 1).toString();
			String cpf = tbAtendente.getModel().getValueAt(linhaSelecionada, 2).toString();
			String login = tbAtendente.getModel().getValueAt(linhaSelecionada, 3).toString();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new AtendenteAlteracao(id, nome, cpf, login, linhaSelecionada, mtTabela).setVisible(true);
				}
			});
		}
	}

	private void btExcluirAction() {
		if (tbAtendente.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resposta == 0) {
				int linhaSelecionada = tbAtendente.getSelectedRow();

				int id = Integer.parseInt(tbAtendente.getModel().getValueAt(linhaSelecionada, 0).toString());

				String erro = "";

				erro = new LoginController().excluiLogin(id);
				
				if(erro == null) {
					JOptionPane.showMessageDialog(this, "Atendente excluído com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeAtendenteTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o atendente: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (resposta == 1) {
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um atendente.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
