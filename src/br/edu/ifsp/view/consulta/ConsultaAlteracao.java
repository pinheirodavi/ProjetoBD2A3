package br.edu.ifsp.view.consulta;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import br.edu.ifsp.controller.ConsultaController;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ConsultaAlteracao extends JDialog {
	private JLabel lbTitulo, lbPaciente, lbMedico, lbDataConsulta;
	private JComboBox<Paciente> cbPaciente;
	private JComboBox<Medico> cbMedico;
	private JFormattedTextField tfDataConsulta;
	private JButton btSalvar;
	private Container cp;
	private int idConsulta;
	private int linhaSelecionada;
	private ConsultaModeloTabela mtTabela;
	
	private Atendente atendente;

	public ConsultaAlteracao(int id, String paciente, String medico, String dataConsulta, Atendente atendente,
			int linha, ConsultaModeloTabela mtTabela) {
		this.atendente = atendente;
		
		setTitle("Alteracão de Consultas");
		setSize(450, 250);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Alteracão de Consultas");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		lbPaciente = new JLabel("Paciente");
		lbMedico = new JLabel("Medico");
		lbDataConsulta = new JLabel("Data da Consulta");

		cbPaciente = new JComboBox<>();

		List<Paciente> pacientes = new ArrayList<Paciente>();

		pacientes = new ConsultaController().recuperaPacientes();
		if (pacientes != null)
			for (Paciente p : pacientes)
				cbPaciente.addItem(p);

		ArrayList<String> erros = new ArrayList<String>();
		erros.add(new ConsultaController().getExcecao());

		cbMedico = new JComboBox<>();

		List<Medico> medicos = new ArrayList<Medico>();

		medicos = new ConsultaController().recuperaMedicos();
		if (medicos != null)
			for (Medico m : medicos)
				cbMedico.addItem(m);

		erros.add(new ConsultaController().getExcecao());

		if (erros.get(0) != null) {
			String mensagem = "Não foi possível recuperar pacientes/médicos:\n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}

		try {
			tfDataConsulta = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (Exception e) {
		}

		btSalvar = new JButton("Salvar");

		this.idConsulta = id;

		for (int i = 0; i < cbPaciente.getItemCount(); i++)
			if (cbPaciente.getItemAt(i).getNome().equals(paciente))
				cbPaciente.setSelectedItem(cbPaciente.getItemAt(i));

		for (int i = 0; i < cbMedico.getItemCount(); i++)
			if (cbMedico.getItemAt(i).getNome().equals(medico))
				cbMedico.setSelectedItem(cbMedico.getItemAt(i));

		tfDataConsulta.setText(dataConsulta);

		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(125, 10, 300, 25);
		lbPaciente.setBounds(20, 50, 140, 25);
		cbPaciente.setBounds(170, 50, 220, 25);
		lbMedico.setBounds(20, 90, 140, 25);
		cbMedico.setBounds(170, 90, 220, 25);
		lbDataConsulta.setBounds(20, 130, 140, 25);
		tfDataConsulta.setBounds(170, 130, 220, 25);
		btSalvar.setBounds(170, 175, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbPaciente);
		cp.add(cbPaciente);
		cp.add(lbMedico);
		cp.add(cbMedico);
		cp.add(lbDataConsulta);
		cp.add(tfDataConsulta);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	}

	private void btSalvarAction() {
		List<String> erros = new ArrayList<String>();

		erros = new ConsultaController().alteraConsulta(this.idConsulta, (Paciente) cbPaciente.getSelectedItem(),
				(Medico) cbMedico.getSelectedItem(), tfDataConsulta.getText(), atendente);

		if (erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Consulta alterada com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			
			this.mtTabela.setValueAt(cbPaciente.getSelectedItem(), linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbMedico.getSelectedItem(), linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfDataConsulta.getText(), linhaSelecionada, 3);
			this.mtTabela.setValueAt(atendente, linhaSelecionada, 4);
			
			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível alterar a consulta: \n";
			for (String e : erros)
				mensagem = mensagem + e;
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
