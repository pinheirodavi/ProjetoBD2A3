package br.edu.ifsp.view.exame;

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

import br.edu.ifsp.controller.ExameController;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.model.medico.Medico;
import br.edu.ifsp.model.paciente.Paciente;

@SuppressWarnings("serial")
public class ExameAlteracao extends JDialog {
	private JLabel lbTitulo, lbDataExame, lbDescricaoExame, lbPaciente, lbMedico;
	private JFormattedTextField tfDataExame;
	private JComboBox<String> cbDescricao;
	private JComboBox<Paciente> cbPaciente;
	private JComboBox<Medico> cbMedico;
	private JButton btSalvar;
	private Container cp;
	private int idExame;
	private int linhaSelecionada;
	private ExameModeloTabela mtTabela;

	private Atendente atendente;

	public ExameAlteracao(int id, String dataExame, String descricao, String paciente, String medico,
			Atendente atendente, int linha, ExameModeloTabela mtTabela) {
		this.atendente = atendente;
		setTitle("Alteração de Exames");
		setSize(450, 300);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Alteração de Consultas");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		lbDataExame = new JLabel("Data do Exame");
		lbDescricaoExame = new JLabel("Descricao");
		lbPaciente = new JLabel("Paciente");
		lbMedico = new JLabel("Medico");

		try {
			tfDataExame = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (Exception e) {
		}

		cbDescricao = new JComboBox<>();
		cbDescricao.addItem("Ultrassonografia");
		cbDescricao.addItem("Eletrocardiograma");
		cbDescricao.addItem("Endoscopia");
		cbDescricao.addItem("Radiografia");
		cbDescricao.addItem("Exame de Sangue");
		cbDescricao.addItem("Exame de Fezes");
		cbDescricao.addItem("Exame de Urina");
		cbDescricao.addItem("Ecocardiograma");
		cbDescricao.addItem("Teste de Gravidez");

		cbPaciente = new JComboBox<>();

		List<Paciente> pacientes = new ArrayList<Paciente>();

		pacientes = new ExameController().recuperaPacientes();
		if (pacientes != null)
			for (Paciente p : pacientes)
				cbPaciente.addItem(p);

		ArrayList<String> erros = new ArrayList<String>();
		erros.add(new ExameController().getExcecao());

		cbMedico = new JComboBox<>();

		List<Medico> medicos = new ArrayList<Medico>();

		medicos = new ExameController().recuperaMedicos();
		if (medicos != null)
			for (Medico m : medicos)
				cbMedico.addItem(m);

		erros.add(new ExameController().getExcecao());

		if (erros.get(0) != null) {
			String mensagem = "Não foi possível recuperar pacientes/médicos:\n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}

		btSalvar = new JButton("Salvar");

		this.idExame = id;
		tfDataExame.setText(dataExame);

		for (int i = 0; i < cbDescricao.getItemCount(); i++)
			if (cbDescricao.getItemAt(i).equals(descricao))
				cbDescricao.setSelectedItem(cbDescricao.getItemAt(i));

		for (int i = 0; i < cbPaciente.getItemCount(); i++)
			if (cbPaciente.getItemAt(i).getNome().equals(paciente))
				cbPaciente.setSelectedItem(cbPaciente.getItemAt(i));

		for (int i = 0; i < cbMedico.getItemCount(); i++)
			if (cbMedico.getItemAt(i).getNome().equals(medico))
				cbMedico.setSelectedItem(cbMedico.getItemAt(i));

		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(125, 10, 300, 25);
		lbDataExame.setBounds(20, 50, 140, 25);
		tfDataExame.setBounds(170, 50, 220, 25);
		lbDescricaoExame.setBounds(20, 90, 140, 25);
		cbDescricao.setBounds(170, 90, 220, 25);
		lbPaciente.setBounds(20, 130, 140, 25);
		cbPaciente.setBounds(170, 130, 220, 25);
		lbMedico.setBounds(20, 170, 140, 25);
		cbMedico.setBounds(170, 170, 220, 25);
		btSalvar.setBounds(170, 212, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbDataExame);
		cp.add(tfDataExame);
		cp.add(lbDescricaoExame);
		cp.add(cbDescricao);
		cp.add(lbPaciente);
		cp.add(cbPaciente);
		cp.add(lbMedico);
		cp.add(cbMedico);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	}

	private void btSalvarAction() {
		List<String> erros = new ArrayList<String>();

		erros = new ExameController().alteraExame(this.idExame, tfDataExame.getText(),
				cbDescricao.getSelectedItem().toString(), (Paciente) cbPaciente.getSelectedItem(),
				(Medico) cbMedico.getSelectedItem(), (Atendente) atendente);

		if (erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Exame alterado com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);

			this.mtTabela.setValueAt(tfDataExame.getText(), linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbDescricao.getSelectedItem(), linhaSelecionada, 2);
			this.mtTabela.setValueAt(cbPaciente.getSelectedItem(), linhaSelecionada, 3);
			this.mtTabela.setValueAt(cbMedico.getSelectedItem(), linhaSelecionada, 4);
			this.mtTabela.setValueAt(atendente, linhaSelecionada, 5);

			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível alterar o exame: \n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
