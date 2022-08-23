package br.edu.ifsp.view.paciente;

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

import br.edu.ifsp.controller.PacienteController;

@SuppressWarnings("serial")
public class PacienteAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbCPF, lbDataNasc, lbSexo, lbPlanoDeSaude;
	private JTextField tfNome;
	private JFormattedTextField tfCPF;
	private JFormattedTextField tfDataNasc;
	private static final String valSexo[] = { "Masculino", "Feminino" };
	private JRadioButton rbSexo[];
	private ButtonGroup bgSexo;
	private JComboBox<String> cbPlanoDeSaude;
	private JButton btSalvar;
	private Container cp;
	private int idPaciente;
	private int linhaSelecionada;
	private PacienteModeloTabela mtTabela;

	public PacienteAlteracao(int idPaciente, String nome, String cpf, String datanasc, String sexo, String planoDeSaude,
			int linha, PacienteModeloTabela mtTabela) {
		setTitle("Alteração de Pacientes");
		setSize(500, 365);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Alteração de Pacientes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		lbNome = new JLabel("Nome");
		lbSexo = new JLabel("Sexo");
		lbCPF = new JLabel("CPF");
		lbDataNasc = new JLabel("Data de Nascimento");
		lbPlanoDeSaude = new JLabel("Plano de Saúde");

		tfNome = new JTextField();

		try {
			tfCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		} catch (Exception e) {
		}

		try {
			tfDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (Exception e) {
		}

		rbSexo = new JRadioButton[2];
		bgSexo = new ButtonGroup();
		for (int i = 0; i < rbSexo.length; i++) {
			rbSexo[i] = new JRadioButton(valSexo[i]);
			rbSexo[i].setBackground(new Color(230, 230, 230));
			bgSexo.add(rbSexo[i]);
		}
		rbSexo[0].setSelected(true);

		cbPlanoDeSaude = new JComboBox<>();
		cbPlanoDeSaude.addItem("Unimed");
		cbPlanoDeSaude.addItem("Amil");
		cbPlanoDeSaude.addItem("SulAmérica");
		cbPlanoDeSaude.addItem("Prevent Senior");
		cbPlanoDeSaude.addItem("Porto Seguro");

		btSalvar = new JButton("Salvar");

		this.idPaciente = idPaciente;

		tfNome.setText(nome);
		tfCPF.setText(cpf);
		tfDataNasc.setText(datanasc);

		if (sexo.equals("Masculino"))
			rbSexo[0].setSelected(true);
		else
			rbSexo[1].setSelected(true);

		for (int i = 0; i < cbPlanoDeSaude.getItemCount(); i++)
			if (cbPlanoDeSaude.getItemAt(i).equals(planoDeSaude))
				cbPlanoDeSaude.setSelectedItem(cbPlanoDeSaude.getItemAt(i));

		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(150, 50, 312, 25);
		lbSexo.setBounds(20, 90, 100, 25);
		rbSexo[0].setBounds(150, 90, 100, 25);
		rbSexo[1].setBounds(250, 90, 100, 25);
		lbCPF.setBounds(20, 130, 100, 25);
		tfCPF.setBounds(150, 130, 100, 25);
		lbDataNasc.setBounds(20, 170, 116, 25);
		tfDataNasc.setBounds(150, 170, 100, 25);
		lbPlanoDeSaude.setBounds(20, 210, 100, 25);
		cbPlanoDeSaude.setBounds(150, 210, 220, 25);
		btSalvar.setBounds(195, 290, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbSexo);
		cp.add(rbSexo[0]);
		cp.add(rbSexo[1]);
		cp.add(lbCPF);
		cp.add(tfCPF);
		cp.add(lbDataNasc);
		cp.add(tfDataNasc);
		cp.add(lbPlanoDeSaude);
		cp.add(cbPlanoDeSaude);
		cp.add(btSalvar);

		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	}

	private void btSalvarAction() {
		Character sexo = null;

		for (JRadioButton rb : rbSexo) // Recupera o texto do JRadionButton selecionado.
			if (rb.isSelected())
				sexo = rb.getText().charAt(0);

		List<String> erros = new ArrayList<String>();

		erros = new PacienteController().alteraPaciente(this.idPaciente, tfNome.getText(), tfCPF.getText(),
				tfDataNasc.getText(), sexo, cbPlanoDeSaude.getSelectedItem().toString());

		if (erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Paciente alterado com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);

			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(tfCPF.getText(), this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(tfDataNasc.getText(), this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(sexo, this.linhaSelecionada, 4);
			this.mtTabela.setValueAt(cbPlanoDeSaude.getSelectedItem(), this.linhaSelecionada, 5);
			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível alterar o paciente: \n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}

}
