package br.edu.ifsp.view.atendente;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.edu.ifsp.controller.LoginController;

@SuppressWarnings("serial")
public class AtendenteCadastro extends JDialog {
	private JLabel lbTitulo, lbNome, lbCPF, lbLogin, lbSenha;
	private JTextField tfNome, tfLogin;
	private JFormattedTextField tfCPF;
	private JPasswordField tfSenha;
	private JButton btCadastrar;
	private Container cp;
	
	public AtendenteCadastro() {
		setTitle("Cadastro de Atendentes");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Atendentes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		lbNome = new JLabel("Nome");
		lbCPF = new JLabel("CPF");
		lbLogin = new JLabel("Login");
		lbSenha = new JLabel("Senha");
		
		tfNome = new JTextField();
		tfLogin = new JTextField();
		tfSenha = new JPasswordField();
		
		try {
			tfCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		} catch (Exception e) {
		}
		
		btCadastrar = new JButton("Cadastrar");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(150, 50, 300, 25);
		lbCPF.setBounds(20, 90, 100, 25);
		tfCPF.setBounds(150, 90, 300, 25);
		lbLogin.setBounds(20, 130, 100, 25);
		tfLogin.setBounds(150, 130, 300, 25);
		lbSenha.setBounds(20, 170, 100, 25);
		tfSenha.setBounds(150, 170, 300, 25);
		btCadastrar.setBounds(195, 210, 100, 25);
				
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbCPF);
		cp.add(tfCPF);
		cp.add(lbLogin);
		cp.add(tfLogin);
		cp.add(lbSenha);
		cp.add(tfSenha);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btCadastrarAction();
			}
		});
		
	}
	
	private void btCadastrarAction() {
		List<String> erros = new ArrayList<String>();
		
		erros = new LoginController().insereLogin(tfNome.getText(), tfCPF.getText(), tfLogin.getText(), String.valueOf(tfSenha.getPassword()));
		
		if(erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Atendente cadastrado com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		} else {
			String mensagem = "Não foi possível cadastrar o atendente: \n";
			for (String e : erros)
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
