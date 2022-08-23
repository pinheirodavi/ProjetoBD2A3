package br.edu.ifsp.view.paciente;

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

import br.edu.ifsp.controller.PacienteController;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class PacienteConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbPaciente;
	private PacienteModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	public PacienteConsulta() {
		setTitle("Consulta de Pacientes");
		setSize(700, 320);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoPacientes = null;

		List<Paciente> pacientes = new PacienteController().consultaPacientes();
		excecaoPacientes = new PacienteController().getExcecao();

		if (excecaoPacientes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de pacientes:\n" + excecaoPacientes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new PacienteModeloTabela();
		} else
			mtTabela = new PacienteModeloTabela(pacientes);

		lbTitulo = new JLabel("Consulta de Pacientes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		tbPaciente = new JTable(mtTabela);
		spTabela = new JScrollPane(tbPaciente);

		tbPaciente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbPaciente.getColumnModel().getColumn(0).setPreferredWidth(70);
		tbPaciente.getColumnModel().getColumn(1).setPreferredWidth(162);
		tbPaciente.getColumnModel().getColumn(2).setPreferredWidth(110);
		tbPaciente.getColumnModel().getColumn(3).setPreferredWidth(90);
		tbPaciente.getColumnModel().getColumn(4).setPreferredWidth(90);
		tbPaciente.getColumnModel().getColumn(5).setPreferredWidth(120);

		tbPaciente.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbPaciente.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		tbPaciente.getColumnModel().getColumn(1).setCellRenderer(dtcrCentro);
		tbPaciente.getColumnModel().getColumn(2).setCellRenderer(dtcrCentro);
		tbPaciente.getColumnModel().getColumn(3).setCellRenderer(dtcrCentro);
		tbPaciente.getColumnModel().getColumn(4).setCellRenderer(dtcrCentro);
		tbPaciente.getColumnModel().getColumn(5).setCellRenderer(dtcrCentro);

		tbPaciente.getTableHeader().setReorderingAllowed(false);
		tbPaciente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(215, 10, 300, 25);
		spTabela.setBounds(20, 40, 645, 182);
		btAlterar.setBounds(215, 240, 100, 25);
		btExcluir.setBounds(355, 240, 100, 25);

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
		if (tbPaciente.getSelectedRow() != -1) {
			int linhaSelecionada = tbPaciente.getSelectedRow();

			int id = Integer.parseInt(tbPaciente.getModel().getValueAt(linhaSelecionada, 0).toString());
			String nome = tbPaciente.getModel().getValueAt(linhaSelecionada, 1).toString();
			String cpf = tbPaciente.getModel().getValueAt(linhaSelecionada, 2).toString();
			String datanasc = tbPaciente.getModel().getValueAt(linhaSelecionada, 3).toString();
			String sexo = tbPaciente.getModel().getValueAt(linhaSelecionada, 4).toString();
			String planoDeSaude = tbPaciente.getModel().getValueAt(linhaSelecionada, 5).toString();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new PacienteAlteracao(id, nome, cpf, datanasc, sexo, planoDeSaude, linhaSelecionada, mtTabela)
							.setVisible(true);
				}
			});

		} else {
			JOptionPane.showMessageDialog(this, "Selecione um paciente.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void btExcluirAction() {
		if (tbPaciente.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (resposta == 0) {
				int linhaSelecionada = tbPaciente.getSelectedRow();

				int id = Integer.parseInt(tbPaciente.getModel().getValueAt(linhaSelecionada, 0).toString());

				String erro = "";

				erro = new PacienteController().excluiPaciente(id);

				if (erro == null) {
					JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso.", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removePacienteTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o paciente: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação",
						JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um paciente.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
