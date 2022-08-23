package br.edu.ifsp.view.medico;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.edu.ifsp.controller.MedicoController;

@SuppressWarnings("serial")
public class MedicoAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbCRM, lbDataNasc, lbCPF, lbSexo, lbEspecialidade;
	private JTextField tfNome;
	private JFormattedTextField tfCRM, tfDataNasc, tfCPF;
	private static final String valSexo[] = { "Masculino", "Feminino" };
	private JRadioButton rbSexo[];
	private ButtonGroup bgSexo;
	private JComboBox<String> cbEspecialidade;
	private JButton btSalvar;
	private Container cp;
	private int idMedico;
	private int linhaSelecionada;
	private MedicoModeloTabela mtTabela;

	public MedicoAlteracao(int idMedico, String nome, String crm, String datanasc, String cpf, String sexo,
			String especialidade, int linha, MedicoModeloTabela mtTabela) {
		setTitle("Alteração de Médicos");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Alteração de Médicos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		lbNome = new JLabel("Nome");
		lbCRM = new JLabel("CRM");
		lbDataNasc = new JLabel("Data de Nascimento");
		lbCPF = new JLabel("CPF");
		lbSexo = new JLabel("Sexo");
		lbEspecialidade = new JLabel("Especialidade");

		tfNome = new JTextField();

		try {
			tfCRM = new JFormattedTextField(new MaskFormatter("########-#"));
		} catch (Exception e) {
		}

		try {
			tfDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (Exception e) {
		}

		try {
			tfCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		} catch (Exception e) {
		}

		rbSexo = new JRadioButton[2];
		bgSexo = new ButtonGroup();
		for (int i = 0; i < rbSexo.length; i++) {
			rbSexo[i] = new JRadioButton(valSexo[i]);
			rbSexo[i].setBackground(new Color(210, 210, 210));
			bgSexo.add(rbSexo[i]);
		}
		rbSexo[0].setSelected(true);

		cbEspecialidade = new JComboBox<>();
		cbEspecialidade.addItem("Clínico Geral");
		cbEspecialidade.addItem("Ortopedista");
		cbEspecialidade.addItem("Oftalmologista");
		cbEspecialidade.addItem("Cardiologista");
		cbEspecialidade.addItem("Endocrinologista");
		cbEspecialidade.addItem("Ginecologista");
		cbEspecialidade.addItem("Pediatra");
		cbEspecialidade.addItem("Geriatra");
		cbEspecialidade.addItem("Neurologista");
		cbEspecialidade.addItem("Urologista");
		cbEspecialidade.addItem("Gastroenterologista");
		cbEspecialidade.addItem("Dermatologista");

		btSalvar = new JButton("Salvar");

		this.idMedico = idMedico;

		tfNome.setText(nome);
		tfCRM.setText(crm);
		tfDataNasc.setText(datanasc);
		tfCPF.setText(cpf);

		if (sexo.equals("Masculino"))
			rbSexo[0].setSelected(true);
		else
			rbSexo[1].setSelected(true);

		for (int i = 0; i < cbEspecialidade.getItemCount(); i++)
			if (cbEspecialidade.getItemAt(i).equals(especialidade))
				cbEspecialidade.setSelectedItem(cbEspecialidade.getItemAt(i));

		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(160, 50, 300, 25);
		lbCRM.setBounds(20, 90, 100, 25);
		tfCRM.setBounds(160, 90, 100, 25);
		lbDataNasc.setBounds(20, 130, 130, 25);
		tfDataNasc.setBounds(160, 130, 100, 25);
		lbCPF.setBounds(20, 170, 100, 25);
		tfCPF.setBounds(160, 170, 100, 25);
		lbSexo.setBounds(20, 210, 100, 25);
		rbSexo[0].setBounds(160, 210, 100, 25);
		rbSexo[1].setBounds(260, 210, 100, 25);
		lbEspecialidade.setBounds(20, 250, 100, 25);
		cbEspecialidade.setBounds(160, 250, 220, 25);
		btSalvar.setBounds(195, 325, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbCRM);
		cp.add(tfCRM);
		cp.add(lbDataNasc);
		cp.add(tfDataNasc);
		cp.add(lbCPF);
		cp.add(tfCPF);
		cp.add(lbSexo);
		cp.add(rbSexo[0]);
		cp.add(rbSexo[1]);
		cp.add(lbEspecialidade);
		cp.add(cbEspecialidade);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});

	}

	private void btSalvarAction() {
		Character sexo = null;

		for (JRadioButton rb : rbSexo)
			if (rb.isSelected())
				sexo = rb.getText().charAt(0);

		List<String> erros = new ArrayList<String>();

		erros = new MedicoController().alteraMedico(this.idMedico, tfNome.getText(), tfCRM.getText(),
				tfDataNasc.getText(), tfCPF.getText(), sexo, cbEspecialidade.getSelectedItem().toString());

		if (erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Médico alterado com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(tfCRM.getText(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfDataNasc.getText(), this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(tfCPF.getText(), this.linhaSelecionada, 4);
			this.mtTabela.setValueAt(sexo, this.linhaSelecionada, 5);
			this.mtTabela.setValueAt(cbEspecialidade.getSelectedItem(), this.linhaSelecionada, 6);
			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível alterar o médico: \n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
