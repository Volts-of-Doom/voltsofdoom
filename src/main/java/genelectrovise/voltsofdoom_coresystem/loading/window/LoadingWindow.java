package genelectrovise.voltsofdoom_coresystem.loading.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class LoadingWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean launched = false;

	JPanel foregroundPanel = new JPanel();
	JLabel lblVoltsOfDoom = new JLabel("Volts of Doom");
	JButton btnLaunch = new JButton("Launch");
	JButton btnCancel = new JButton("Cancel");

	public LoadingWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateContents();
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LoadingWindow w = new LoadingWindow();
				w.updateContents();
				w.setVisible(true);
			}
		});
	}

	private void updateContents() {
		// JFrame
		setResizable(false);
		setBackground(UIManager.getColor("CheckBox.focus"));
		setForeground(UIManager.getColor("Button.focus"));
		setTitle("Volts of Doom - Launcher");
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		setBounds(10, 11, 450, 300);

		// Foreground JPanel
		foregroundPanel.setBackground(UIManager.getColor("Button.light"));
		foregroundPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		foregroundPanel.setBounds(10, 11, 424, 249);
		foregroundPanel.setLayout(null);

		// Label
		lblVoltsOfDoom.setFont(new Font("Yu Gothic UI Light", Font.BOLD | Font.ITALIC, 15));
		lblVoltsOfDoom.setBounds(10, 11, 111, 21);

		// Launch button
		btnLaunch.setBounds(325, 181, 89, 23);
		ActionListener launchBtnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!launched) {
					launched = true;
					btnLaunch.setEnabled(false);
					LoadingWindowLogicController.launch();
				}
			}
		};
		btnLaunch.addActionListener(launchBtnListener);

		// Cancel button
		btnCancel.setBounds(325, 215, 89, 23);
		ActionListener cancelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setEnabled(false);
				System.exit(0);
			}
		};
		btnCancel.addActionListener(cancelActionListener);

		// Add components
		getContentPane().add(foregroundPanel);
		foregroundPanel.add(lblVoltsOfDoom);
		foregroundPanel.add(btnLaunch);
		foregroundPanel.add(btnCancel);
	}
}
