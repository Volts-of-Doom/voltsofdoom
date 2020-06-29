package vision.voltsofdoom.coresystem.loading.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class LoadingWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel foregroundPanel = new JPanel();
	private JLabel lblVoltsOfDoom = new JLabel("Volts of Doom");
	
	private volatile LoadingWindowStatus status = LoadingWindowStatus.OPENING_WINDOW;

	public LoadingWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateContents();
	}

	public static void main(String[] args) {
		Thread.currentThread().setName("loading_window");
		new LoadingWindow().run();
	}

	public void run() {
		EventQueue.invokeLater(() -> {
			updateContents();
			setVisible(true);
		});
	}

	private void updateContents() {
		// JFrame
		setResizable(false);
		setBackground(UIManager.getColor("CheckBox.focus"));
		setForeground(UIManager.getColor("Button.focus"));
		setTitle("Volts of Doom - Loading");
		getContentPane().setBackground(UIManager.getColor("Button.background"));
		getContentPane().setForeground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		setBounds(10, 11, 300, 150);

		// Foreground JPanel
		foregroundPanel.setBackground(UIManager.getColor("Button.light"));
		foregroundPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		foregroundPanel.setBounds(10, 11, 274, 99);
		foregroundPanel.setLayout(null);
		lblVoltsOfDoom.setHorizontalAlignment(SwingConstants.CENTER);

		// Name Label
		lblVoltsOfDoom.setFont(new Font("Yu Gothic UI Light", Font.BOLD | Font.ITALIC, 15));
		lblVoltsOfDoom.setBounds(10, 11, 254, 21);

		// Add components
		getContentPane().add(foregroundPanel);
		foregroundPanel.add(lblVoltsOfDoom);

		JLabel lblLoadingPleaseWait = new JLabel("Loading... Please wait...");
		lblLoadingPleaseWait.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadingPleaseWait.setBounds(10, 46, 254, 14);
		foregroundPanel.add(lblLoadingPleaseWait);
		
		JLabel lblStatus = new JLabel("Status: " + status.getMsg());
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(10, 74, 254, 14);
		foregroundPanel.add(lblStatus);
	}
	
	public synchronized void setStatus(LoadingWindowStatus status) {
		this.status = status;
	}

	public void disableAndDispose() {
		EventQueue.invokeLater(() -> {
			setEnabled(false);
			dispose();
		});
	}
}
