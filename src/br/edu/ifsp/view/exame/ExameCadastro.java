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
public class ExameCadastro extends JDialog {
	private JLabel lbTitulo, lbDataExame, lbDescricaoExame, lbPaciente, lbMedico;
	private JFormattedTextField tfDataExame;
	private JComboBox<String> cbDescricao;
	private JComboBox<Paciente> cbPaciente;
	private JComboBox<Medico> cbMedico;
	private JButton btCadastrar;
	private Container cp;

	private Atendente atendente;
	
	public ExameCadastro(Atendente atendente) {
		this.atendente = atendente;
		
		setTitle("Cadastro de Exames");
		setSize(450, 300);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Cadastro de Consultas");
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

		btCadastrar = new JButton("Cadastrar");

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
		btCadastrar.setBounds(170, 214, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbDataExame);
		cp.add(tfDataExame);
		cp.add(lbDescricaoExame);
		cp.add(cbDescricao);
		cp.add(lbPaciente);
		cp.add(cbPaciente);
		cp.add(lbMedico);
		cp.add(cbMedico);
		cp.add(btCadastrar);

		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btCadastrarAction();
			}
		});
	}

	private void btCadastrarAction() {
		List<String> erros = new ArrayList<String>();

		erros = new ExameController().insereExame(tfDataExame.getText(), cbDescricao.getSelectedItem().toString(),
				(Paciente) cbPaciente.getSelectedItem(), (Medico) cbMedico.getSelectedItem(), (Atendente) atendente);

		if (erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Exame cadastrado com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível cadastrar o exame: \n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
