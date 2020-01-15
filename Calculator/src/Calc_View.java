import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Calc_View extends JFrame{
	private JButton[][] button = new JButton[4][4];
	private JPanel calcpanel;
	private JPanel displayPanel;
	private JTextArea display = new JTextArea("0");
	private JButton plus = new JButton("+");
	private JButton minus = new JButton("-");
	private JButton mult = new JButton("*");
	private JButton divid = new JButton("/");
	private JButton clear = new JButton("C");
	private JButton zero = new JButton("0");
	private JButton equal = new JButton("=");
	private JMenu menu = new JMenu("Option") ;
	private JMenuBar bar = new JMenuBar();
	private JMenuItem save = new JMenuItem("save");
	private JMenuItem load = new JMenuItem("load");
	private JMenuItem importxml = new JMenuItem("export to XML");
	private JMenuItem exportxml = new JMenuItem("import from XML");
	public Calc_View(){
		this.setName("Calculator");
		this.setLayout(new BorderLayout());
		calcpanel = new JPanel(new GridLayout(4,4));
		displayPanel = new JPanel(new BorderLayout());
		this.add(displayPanel,BorderLayout.PAGE_START);
		this.add(calcpanel,BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setVisible(true);
		displayPanel.add(bar,BorderLayout.PAGE_START);
		bar.add(menu);
		menu.add(save);
		menu.add(load);
		menu.add(importxml);
		menu.add(exportxml);
		displayPanel.add(display,BorderLayout.CENTER);
		display.setSize(200,200);
		display.setEditable(false);
		int num = 1;
		for(int i = 0 ; i< 3;i++) {
			for(int j = 0; j< 3;j++) {
				button[i][j] = new JButton();
				button[i][j].setText(String.valueOf(num));
				num++;
			}
		}
		button[0][3] = divid;
		button[1][3] = mult;
		button[2][3] = minus;
		button[3][3] = plus;
		button[3][0] = zero;
		button[3][1] = equal;
		button[3][2] = clear;
		for(int i=0; i < 4;i++) {
			for(int j = 0;j<4;j++) {
				calcpanel.add(button[i][j]);
			}
		}
	}
	public void addButtonListener (int i,int j,ActionListener e){
		button[i][j].addActionListener(e);
	}
	public void addDividListener(ActionListener e) {
		divid.addActionListener(e);
	}
	public void addMultListener(ActionListener e) {
		mult.addActionListener(e);
	
	}
	public void addMinusListener(ActionListener e) {
		minus.addActionListener(e);
	}
	public void addPlusListener(ActionListener e) {
		plus.addActionListener(e);
	}
	public void addEqualListener(ActionListener e) {
		equal.addActionListener(e);
	}
	public void addClearListener(ActionListener e) {
		clear.addActionListener(e);
	}
	public void addZeroListener(ActionListener e) {
		zero.addActionListener(e);
	}
	public void setDisplay(double num) {
		display.setText(String.valueOf(num));
	}
	public void clearDisplay() {
		display.setText("0");
	}
	public void addLoadListener(ActionListener e) {
		load.addActionListener(e);
	}
	public void addSaveListener(ActionListener e) {
		save.addActionListener(e);
	}
}

