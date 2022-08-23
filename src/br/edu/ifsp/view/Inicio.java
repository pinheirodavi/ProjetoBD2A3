package br.edu.ifsp.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import br.edu.ifsp.model.atendente.Atendente;
import br.edu.ifsp.view.consulta.ConsultaCadastro;
import br.edu.ifsp.view.consulta.ConsultaConsulta;
import br.edu.ifsp.view.exame.ExameCadastro;
import br.edu.ifsp.view.exame.ExameConsulta;
import br.edu.ifsp.view.medico.MedicoCadastro;
import br.edu.ifsp.view.medico.MedicoConsulta;
import br.edu.ifsp.view.paciente.PacienteCadastro;
import br.edu.ifsp.view.paciente.PacienteConsulta;

@SuppressWarnings("serial")
public class Inicio extends JFrame {
	private JMenuBar mbBarra;
	private JMenu mnPaciente;
	private JMenu mnMedico;
	private JMenu mnConsulta;
	private JMenu mnExame;

	private JMenuItem miCadPaciente;
	private JMenuItem miConsPaciente;

	private JMenuItem miCadMedico;
	private JMenuItem miConsMedico;

	private JMenuItem miCadConsulta;
	private JMenuItem miConsConsulta;
	
	private JMenuItem miCadExame;
	private JMenuItem miConsExame;
	
	private Atendente atendente;

	public Inicio(Atendente atendente) {
		this.atendente = atendente;
		
		mbBarra = new JMenuBar();
		mnPaciente = new JMenu("Paciente");
		mnMedico = new JMenu("Médico");
		mnConsulta = new JMenu("Consultas");
		mnExame = new JMenu("Exames");
		miCadPaciente = new JMenuItem("Cadastrar Pacientes");
		miConsPaciente = new JMenuItem("Consultar Pacientes");
		miCadMedico = new JMenuItem("Cadastrar Médicos");
		miConsMedico = new JMenuItem("Consultar Médicos");
		miCadConsulta = new JMenuItem("Cadastrar Consultas");
		miConsConsulta = new JMenuItem("Consultar Consultas");
		miCadExame = new JMenuItem("Cadastrar Exames");
		miConsExame = new JMenuItem("Consultar Exames");

		setTitle("Sitema de Gerenciamento Hospital CareHealth");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(750, 460);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(210, 210, 210));
		mnPaciente.add(miCadPaciente);
		mnPaciente.add(miConsPaciente);
		mnMedico.add(miCadMedico);
		mnMedico.add(miConsMedico);
		mnConsulta.add(miCadConsulta);
		mnConsulta.add(miConsConsulta);
		mnExame.add(miCadExame);
		mnExame.add(miConsExame);
		mbBarra.add(mnPaciente);
		mbBarra.add(mnMedico);
		mbBarra.add(mnConsulta);
		mbBarra.add(mnExame);
		setJMenuBar(mbBarra);

		miCadPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCadPacienteAction();
			}
		});

		miConsPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miConsPacienteAction();
			}
		});

		miCadMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCadMedicoAction();
			}
		});

		miConsMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miConsMedicoAction();
			}
		});

		miCadConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCadConsultaAction();
			}
		});

		miConsConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miConsConsultaAction();
			}
		});
		
		miCadExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCadExameAction();
			}
		});

		miConsExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miConsExameAction();
			}
		});
	}

	private void miCadPacienteAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PacienteCadastro().setVisible(true);
			}
		});
	}

	private void miConsPacienteAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PacienteConsulta().setVisible(true);
			}
		});
	}

	private void miCadMedicoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MedicoCadastro().setVisible(true);
			}
		});
	}

	private void miConsMedicoAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MedicoConsulta().setVisible(true);
			}
		});
	}

	private void miCadConsultaAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ConsultaCadastro(atendente).setVisible(true);
			}
		});
	}

	private void miConsConsultaAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ConsultaConsulta(atendente).setVisible(true);
			}
		});
	}
	
	private void miCadExameAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ExameCadastro(atendente).setVisible(true);
			}
		});
	}

	private void miConsExameAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ExameConsulta(atendente).setVisible(true);
			}
		});
	}
}
