package vision.voltsofdoom.coresystem.loading.window;

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

import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean launched = false;

	private static Launcher window;
	private JPanel foregroundPanel = new JPanel();
	private JLabel lblVoltsOfDoom = new JLabel("Volts of Doom");
	private JLabel lblInfoMsg = new JLabel("Launcher info will appear here!");
	private JButton btnLaunch = new JButton("Launch");
	private JButton btnCancel = new JButton("Cancel");

	public Launcher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateContents();
	}
	
	public static void main(String[] args) {
		Thread.currentThread().setName("launcher");
		run();
	}

	private static void run() {
		EventQueue.invokeLater(() -> {
			window = new Launcher();
			window.updateContents();
			window.setVisible(true);
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

		// Name Label
		lblVoltsOfDoom.setFont(new Font("Yu Gothic UI Light", Font.BOLD | Font.ITALIC, 15));
		lblVoltsOfDoom.setBounds(10, 11, 111, 21);

		// Msg label
		lblInfoMsg.setFont(new Font("Yu Gothic UI Light", Font.BOLD | Font.ITALIC, 11));
		lblInfoMsg.setBounds(30, 31, 111, 21);

		// Launch button
		btnLaunch.setBounds(325, 181, 89, 23);
		ActionListener launchBtnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!launched) {
					launched = true;
					btnLaunch.setEnabled(false);
					new VoltsOfDoomCoreSystem().start();
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
		foregroundPanel.add(lblInfoMsg);
	}
}
