package br.edu.ifsp.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.edu.ifsp.controller.LoginController;
import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.view.atendente.AtendenteMenu;

@SuppressWarnings("serial")
public class Login extends JDialog {
	private JLabel lbTitulo, lbLogin, lbSenha;
	private JTextField tfLogin;
	private JPasswordField tfSenha;
	private JButton btLogin;
	private JButton btAdm;
	private Container cp;

	public Login() {
		setTitle("Login");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Login");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		lbLogin = new JLabel("Login");
		lbSenha = new JLabel("Senha");

		tfLogin = new JTextField();
		tfSenha = new JPasswordField();

		btLogin = new JButton("Fazer Login");
		btAdm = new JButton("Gerenciar");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		lbTitulo.setBounds(161, 11, 58, 25);
		lbLogin.setBounds(53, 50, 107, 25);
		tfLogin.setBounds(161, 50, 165, 25);
		lbSenha.setBounds(53, 86, 107, 25);
		tfSenha.setBounds(161, 86, 165, 25);
		btLogin.setBounds(63, 125, 100, 25);
		btAdm.setBounds(207, 125, 100, 25);

		cp.add(lbTitulo);
		cp.add(lbLogin);
		cp.add(tfLogin);
		cp.add(lbSenha);
		cp.add(tfSenha);
		cp.add(btLogin);
		cp.add(btAdm);

		btLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btLoginAction();
			}
		});

		btAdm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btAdmAction();
			}
		});
	}

	public void btLoginAction() {
		Atendente atendente;

		String login = tfLogin.getText();
		String senha = String.valueOf(tfSenha.getPassword());

		atendente = new LoginController().fazerLogin(login, senha);

		if (atendente != null) {
			JOptionPane.showMessageDialog(this, "Login feito com sucesso.", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Inicio(atendente).setVisible(true);
				}
			});
		} else {
			JOptionPane.showMessageDialog(this, "Falha no login!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void btAdmAction() {
		String login = tfLogin.getText();
		String senha = String.valueOf(tfSenha.getPassword());

		if (login.equals("adm") && senha.equals("123")) {
			int resposta = JOptionPane.showConfirmDialog(this,
					"Deseja acessar a página de gerenciamento de Atendentes?", "Confirmação", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				this.dispose();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new AtendenteMenu().setVisible(true);
					}
				});
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Operação cancelada.", "Confirmação",
						JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Você não é o administrador!", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Login().setVisible(true);
			}
		});
	}
}
