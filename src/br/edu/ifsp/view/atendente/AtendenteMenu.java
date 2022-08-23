package br.edu.ifsp.view.atendente;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import br.edu.ifsp.view.Login;

@SuppressWarnings("serial")
public class AtendenteMenu extends JDialog {
	private JLabel lbTitulo;
	private JButton btCadastrar, btConsultar, btVoltar;
	private Container cp;

	public AtendenteMenu() {
		setTitle("Menu de Gerência");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Gerência de Atendentes");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));

		btCadastrar = new JButton("Cadastrar");
		btConsultar = new JButton("Consultar");
		btVoltar = new JButton("Voltar");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(220, 220, 220));

		btCadastrar.setBounds(59, 74, 100, 45);
		btConsultar.setBounds(223, 74, 100, 45);
		btVoltar.setBounds(5, 5, 70, 25);

		cp.add(btCadastrar);
		cp.add(btConsultar);
		cp.add(btVoltar);

		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btCadastrarAction();
			}
		});

		btConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btConsultarAction();
			}
		});

		btVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btVoltarAction();
			}
		});
	}

	private void btCadastrarAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AtendenteCadastro().setVisible(true);
			}
		});
	}

	private void btConsultarAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AtendenteConsulta().setVisible(true);
			}
		});
	}

	public void btVoltarAction() {
		this.dispose();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Login().setVisible(true);
			}
		});
	}

}
