package de.virtualprocessmanagement.tools;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Dialog extends JDialog {
	
	private JTextField ipAdress = new JTextField("localhost");
	private JRadioButton rb = new JRadioButton("Servermode");
	
	public Dialog() {
	
		setTitle("Set mode");
		
		setLayout(new BorderLayout());
		 
		setSize(300,200);

		setModal(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
		Box hBox = Box.createHorizontalBox();
		
		JPanel mainPanel = new JPanel();
		JPanel checkboxPanel = new JPanel(new BorderLayout());
		JPanel textfieldPanel = new JPanel(new BorderLayout());
		JPanel labelPanel1 = new JPanel(new BorderLayout());
		JPanel labelPanel2 = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		mainPanel.setLayout(new BoxLayout( mainPanel, BoxLayout.Y_AXIS));
		
		JButton button = new JButton("Ok!");
		button.setFocusable(true);
		button.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setVisible(false); } });    
		buttonPanel.add(button, BorderLayout.WEST);
		
		ipAdress.setColumns(15);
		textfieldPanel.add(ipAdress, BorderLayout.WEST);
		
		ButtonGroup bg = new ButtonGroup();
		
		rb.setSelected(true);
		bg.add(rb);
		hBox.add(rb);
		
		JRadioButton rb2 = new JRadioButton("Clientmode");
		bg.add(rb2);
		hBox.add(rb2);
		
		checkboxPanel.add(hBox);

		mainPanel.add(Box.createVerticalStrut(10));
		labelPanel1.add(new JLabel("<html><b>Choose the gui-mode</b></html>"), BorderLayout.WEST);
		mainPanel.add(labelPanel1);
		mainPanel.add(checkboxPanel);
		mainPanel.add(Box.createVerticalStrut(5));
		labelPanel2.add(new JLabel("<html><b>Enter the Server-IP</b></html>"), BorderLayout.WEST); //, BorderLayout.WEST);
		mainPanel.add(labelPanel2);
		mainPanel.add(textfieldPanel);
		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(buttonPanel);

		add(mainPanel, BorderLayout.NORTH);

		setVisible(true);
	}
	
	public JTextField getIpAdress() {
		return ipAdress;
	}

	public JRadioButton getServerMode() {
		return rb;
	}


}
