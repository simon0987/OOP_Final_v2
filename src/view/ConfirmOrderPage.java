package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controll.BookTicket;

public class ConfirmOrderPage{
	private JFrame frame;
	private JPanel contentPane;
	private JLabel goStart;
	private JLabel goEnd;
	private JLabel goT1;
	private JLabel goT2;
	private JLabel count;
	private JLabel goType;
	private JLabel backStart;
	private JLabel backEnd;
	private JLabel backT1;
	private JLabel backT2;
	private JLabel countt;
	private JLabel backType;
	private JLabel price;
	private BookTicket con;
	
	/**
	 * Show the frame.
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Set Content.
	 * @param title title
	 */
	public void showOrderContent(String startStation, String endStation, int goStartTime, int goEndTime, 
			int backStartTime, int backEndTime, String type, int num, int pri,boolean ifGoBack, BookTicket control) {
		goStart.setText(startStation);
		goEnd.setText(endStation);
		goT1.setText(String.format("%02d:%02d", goStartTime / 100, goStartTime % 100));
		goT2.setText(String.format("%02d:%02d", goEndTime / 100, goEndTime % 100));
		count.setText(Integer.toString(num));
		goType.setText(type);
		if(ifGoBack) {
			backStart.setText(endStation);
			backEnd.setText(startStation);
			backT1.setText(String.format("%02d:%02d", backStartTime / 100, backStartTime % 100));
			backT2.setText(String.format("%02d:%02d", backEndTime / 100, backEndTime % 100));
			countt.setText(Integer.toString(num));
			backType.setText(type);
		}
		else {
			backStart.setText("-");
			backEnd.setText("-");
			backT1.setText("-");
			backT2.setText("-");
			countt.setText("-");
			backType.setText("-");
		}
		price.setText(Integer.toString(pri));
		con = control;
	}
	
	/**
	 * Create the application.
	 */
	public ConfirmOrderPage() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 720, 350);
		frame.setTitle("確認訂單");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u53BB\u7A0B");
		lblNewLabel.setBounds(15, 70, 71, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u56DE\u7A0B");
		lblNewLabel_1.setBounds(15, 108, 71, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u7E3D\u50F9");
		lblNewLabel_2.setBounds(15, 176, 71, 23);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u8D77\u7AD9");
		lblNewLabel_3.setBounds(97, 26, 71, 23);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u8FC4\u7AD9");
		lblNewLabel_4.setBounds(201, 26, 71, 23);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u51FA\u767C\u6642\u9593");
		lblNewLabel_5.setBounds(296, 26, 87, 23);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u62B5\u9054\u6642\u9593");
		lblNewLabel_6.setBounds(424, 26, 87, 23);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("\u5F35\u6578");
		lblNewLabel_7.setBounds(641, 26, 71, 23);
		contentPane.add(lblNewLabel_7);
		
		goStart = new JLabel();
		goStart.setBounds(97, 70, 71, 23);
		contentPane.add(goStart);
		
		goEnd = new JLabel();
		goEnd.setBounds(201, 70, 71, 23);
		contentPane.add(goEnd);
		
		goT1 = new JLabel();
		goT1.setBounds(296, 70, 71, 23);
		contentPane.add(goT1);
		
		goT2 = new JLabel();
		goT2.setBounds(424, 70, 71, 23);
		contentPane.add(goT2);
		
		count = new JLabel();
		count.setBounds(641, 70, 71, 23);
		contentPane.add(count);
		
		backStart = new JLabel();
		backStart.setBounds(97, 108, 71, 23);
		contentPane.add(backStart);
		
		JLabel label = new JLabel("\u7968\u7A2E");
		label.setBounds(555, 26, 71, 23);
		contentPane.add(label);
		
		goType = new JLabel();
		goType.setBounds(555, 70, 71, 23);
		contentPane.add(goType);
		
		backEnd = new JLabel();
		backEnd.setBounds(201, 108, 71, 23);
		contentPane.add(backEnd);
		
		backT1 = new JLabel();
		backT1.setBounds(296, 108, 71, 23);
		contentPane.add(backT1);
		
		backT2 = new JLabel();
		backT2.setBounds(424, 108, 71, 23);
		contentPane.add(backT2);
		
		backType = new JLabel();
		backType.setBounds(555, 108, 71, 23);
		contentPane.add(backType);
		
		countt = new JLabel();
		countt.setBounds(641, 108, 71, 23);
		contentPane.add(countt);
		
		price = new JLabel();
		price.setBounds(97, 176, 71, 23);
		contentPane.add(price);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(65, 225, 219, 31);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				return;
			}
		});
		
		JButton confirmButton = new JButton("確認");
		confirmButton.setBounds(329, 225, 320, 31);
		contentPane.add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				con.showOrder();
				int code = con.getCode();
				int price = con.getTotalPrice();
				JOptionPane.showMessageDialog(null, "訂票完成\n訂位代號: " + code + "\n應付金額: " + price,
						"InfoBox: Successed", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
/*	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfirmOrderPage window = new ConfirmOrderPage();
					window.showOrderContent("台北","高雄",	1230, 1530,920,1000,"一般",5,1800);
					int i =window.show();
					System.out.println(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
}
