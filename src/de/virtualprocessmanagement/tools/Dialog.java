package de.virtualprocessmanagement.tools;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

public class Dialog extends JDialog implements ItemListener {
	
	public static final int SERVER_MODE = 0;
	public static final int SERVER_CLIENT_MODE = 1;
	public static final int CLIENT_MODE = 2;
	
	
	private JTextField ipAdress = new JTextField("localhost");
	private JRadioButton rb = new JRadioButton("Server");
	private JRadioButton rb1 = new JRadioButton("Server & Client", true);
	private JRadioButton rb2 = new JRadioButton("Client");
	
	private JPanel labelPanel2 = new JPanel(new BorderLayout());

	public Dialog() {
	
		setTitle("Set mode");
		
		setLayout(new BorderLayout());
		 
		setSize(255, 170);

		setModal(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
		Box hBox = Box.createHorizontalBox();
		
		JPanel mainPanel = new JPanel();
		JPanel checkboxPanel = new JPanel(new BorderLayout());
		JPanel textfieldPanel = new JPanel(new BorderLayout());
		JPanel labelPanel1 = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		mainPanel.setLayout(new BoxLayout( mainPanel, BoxLayout.Y_AXIS));
		
		JButton button = new JButton("Ok!");
		button.setFocusable(true);
		button.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setVisible(false); } });    
		buttonPanel.add(button, BorderLayout.WEST);
		
		labelPanel2.setVisible(false);
		ipAdress.setVisible(false);
		ipAdress.setColumns(15);
		textfieldPanel.add(ipAdress, BorderLayout.WEST);
		
		ButtonGroup bg = new ButtonGroup();
		
		rb.addItemListener(this);
		rb1.addItemListener(this);
		rb2.addItemListener(this);
		
		bg.add(rb);
		hBox.add(rb);
		hBox.add(Box.createHorizontalStrut(4));
		
		bg.add(rb1);
		hBox.add(rb1);
		hBox.add(Box.createHorizontalStrut(4));
		
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

	public int getGuiMode() {
		
		if(rb.isSelected())
			return SERVER_MODE;
		else
			if(rb1.isSelected())
				return SERVER_CLIENT_MODE;
			else
				return CLIENT_MODE;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		System.out.println(((JRadioButton)arg0.getSource()).getText());
		
		if(((JRadioButton)arg0.getSource()).getText().equals("Client"))
		{
			ipAdress.setVisible(true);
			labelPanel2.setVisible(true);
		}
		else
		{
			ipAdress.setText("localhost");
			ipAdress.setVisible(false);
			labelPanel2.setVisible(false);
		}
	}


}
