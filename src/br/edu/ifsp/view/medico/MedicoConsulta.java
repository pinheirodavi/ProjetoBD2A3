package br.edu.ifsp.view.medico;

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

import br.edu.ifsp.controller.MedicoController;
import br.edu.ifsp.model.medico.Medico;

@SuppressWarnings("serial")
public class MedicoConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbMedico;
	private MedicoModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	public MedicoConsulta() {
		setTitle("Consulta de Médicos");
		setSize(750, 310);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoMedicos = null;

		List<Medico> medicos = new MedicoController().consultaMedicos();
		excecaoMedicos = new MedicoController().getExcecao();

		if (excecaoMedicos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados de médicos:\n" + excecaoMedicos,
					"Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new MedicoModeloTabela();
		} else
			mtTabela = new MedicoModeloTabela(medicos);

		lbTitulo = new JLabel("Consulta de Médicos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		tbMedico = new JTable(mtTabela);
		spTabela = new JScrollPane(tbMedico);

		tbMedico.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbMedico.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbMedico.getColumnModel().getColumn(1).setPreferredWidth(159);
		tbMedico.getColumnModel().getColumn(2).setPreferredWidth(90);
		tbMedico.getColumnModel().getColumn(3).setPreferredWidth(90);
		tbMedico.getColumnModel().getColumn(4).setPreferredWidth(110);
		tbMedico.getColumnModel().getColumn(5).setPreferredWidth(80);
		tbMedico.getColumnModel().getColumn(6).setPreferredWidth(110);

		tbMedico.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbMedico.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(1).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(2).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(3).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(4).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(5).setCellRenderer(dtcrCentro);
		tbMedico.getColumnModel().getColumn(6).setCellRenderer(dtcrCentro);

		tbMedico.getTableHeader().setReorderingAllowed(false);
		tbMedico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(215, 10, 300, 25);
		spTabela.setBounds(20, 40, 692, 182);
		btAlterar.setBounds(201, 233, 100, 25);
		btExcluir.setBounds(415, 233, 100, 25);

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
		if (tbMedico.getSelectedRow() != -1) {
			int linhaSelecionada = tbMedico.getSelectedRow();

			int id = Integer.parseInt(tbMedico.getModel().getValueAt(linhaSelecionada, 0).toString());
			String nome = tbMedico.getModel().getValueAt(linhaSelecionada, 1).toString();
			String crm = tbMedico.getModel().getValueAt(linhaSelecionada, 2).toString();
			String datanasc = tbMedico.getModel().getValueAt(linhaSelecionada, 3).toString();
			String cpf = tbMedico.getModel().getValueAt(linhaSelecionada, 4).toString();
			String sexo = tbMedico.getModel().getValueAt(linhaSelecionada, 5).toString();
			String especialidade = tbMedico.getModel().getValueAt(linhaSelecionada, 6).toString();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new MedicoAlteracao(id, nome, crm, datanasc, cpf, sexo, especialidade, linhaSelecionada, mtTabela)
							.setVisible(true);
				}
			});
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um médico.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void btExcluirAction() {
		if (tbMedico.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resposta == 0) {
				int linhaSelecionada = tbMedico.getSelectedRow();

				int id = Integer.parseInt(tbMedico.getModel().getValueAt(linhaSelecionada, 0).toString());

				String erro = "";

				erro = new MedicoController().excluiMedico(id);
				if (erro == null) {
					JOptionPane.showMessageDialog(this, "Médico excluído com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeMedicoTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o médico: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (resposta == 1) {
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um médico.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
