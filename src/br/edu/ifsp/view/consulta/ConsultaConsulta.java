package br.edu.ifsp.view.consulta;

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

import br.edu.ifsp.controller.ConsultaController;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.consulta.Consulta;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ConsultaConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbConsulta;
	private ConsultaModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	private Atendente atendente;
	
	public ConsultaConsulta(Atendente atendente) {
		this.atendente = atendente;
		
		setTitle("Consulta de Consultas");
		setSize(675, 320);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoConsultas = null;
		String excecaoPacientes = null;
		String excecaoMedicos = null;
		String excecaoAtendentes = null;

		List<Consulta> consultas = new ConsultaController().consultaConsultas();
		excecaoConsultas = new ConsultaController().getExcecao();

		List<Paciente> pacientes = new ConsultaController().recuperaPacientes();
		excecaoPacientes = new ConsultaController().getExcecao();

		List<Medico> medicos = new ConsultaController().recuperaMedicos();
		excecaoMedicos = new ConsultaController().getExcecao();

		List<Atendente> atendentes = new ConsultaController().recuperaAtendentes();
		excecaoAtendentes = new ConsultaController().getExcecao();

		if (excecaoConsultas != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de consultas:\n" + excecaoConsultas, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ConsultaModeloTabela();
		} else if (excecaoPacientes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de pacientes:\n" + excecaoPacientes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ConsultaModeloTabela();
		} else if (excecaoMedicos != null) {
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados de médicos:\n" + excecaoMedicos,
					"Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new ConsultaModeloTabela();
		} else if (excecaoAtendentes != null) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível recuperar os dados de atendentes:\n" + excecaoAtendentes, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new ConsultaModeloTabela();
		} else
			mtTabela = new ConsultaModeloTabela(consultas, pacientes, medicos, atendentes);

		lbTitulo = new JLabel("Consulta de Consultas");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		tbConsulta = new JTable(mtTabela);
		spTabela = new JScrollPane(tbConsulta);

		tbConsulta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbConsulta.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbConsulta.getColumnModel().getColumn(1).setPreferredWidth(166);
		tbConsulta.getColumnModel().getColumn(2).setPreferredWidth(166);
		tbConsulta.getColumnModel().getColumn(3).setPreferredWidth(130);
		tbConsulta.getColumnModel().getColumn(4).setPreferredWidth(100);

		tbConsulta.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbConsulta.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		tbConsulta.getColumnModel().getColumn(1).setCellRenderer(dtcrCentro);
		tbConsulta.getColumnModel().getColumn(2).setCellRenderer(dtcrCentro);
		tbConsulta.getColumnModel().getColumn(3).setCellRenderer(dtcrCentro);
		tbConsulta.getColumnModel().getColumn(4).setCellRenderer(dtcrCentro);

		tbConsulta.getTableHeader().setReorderingAllowed(false);
		tbConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(215, 10, 300, 25);
		spTabela.setBounds(20, 40, 615, 182);
		btAlterar.setBounds(190, 240, 100, 25);
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

	public void btAlterarAction() {
		if (tbConsulta.getSelectedRow() != -1) {
			int linhaSelecionada = tbConsulta.getSelectedRow();
			
			int id = Integer.parseInt(tbConsulta.getModel().getValueAt(linhaSelecionada, 0).toString());
			String paciente = tbConsulta.getModel().getValueAt(linhaSelecionada, 1).toString();
			String medico = tbConsulta.getModel().getValueAt(linhaSelecionada, 2).toString();
			String dataConsulta = tbConsulta.getModel().getValueAt(linhaSelecionada, 3).toString();
						
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() { new ConsultaAlteracao(id, paciente, medico, dataConsulta, atendente, linhaSelecionada, mtTabela).setVisible(true);}
				
			});
		}else {
			JOptionPane.showMessageDialog(this, "Selecione uma Consulta.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void btExcluirAction() {
		if (tbConsulta.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", 
					 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				int linhaSelecionada = tbConsulta.getSelectedRow();
				
				int id = Integer.parseInt(tbConsulta.getModel().getValueAt(linhaSelecionada, 0).toString());
				String erro = "";
				
				erro = new ConsultaController().excluiConsulta(id);
				
				if(erro == null) {
					JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso.", 
                            "Informação", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeConsultaTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir a Consulta: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
		}else
			JOptionPane.showMessageDialog(this, "Selecione uma Consulta.", "Mensagem", JOptionPane.WARNING_MESSAGE);
	}
}
