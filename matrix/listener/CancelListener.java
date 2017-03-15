package matrix.listener;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import matrix.r.R;

public class CancelListener implements ActionListener {

	private ArrayList<JButton[][]> buttonGroupList;
	private ArrayList<JPanel> panelList;
	private ArrayList<JButton> buttonCancelList;
	private ArrayList<JButton> buttonClearList;
	private ArrayList<JLabel> labelList;

	public CancelListener() {
		buttonGroupList = new ArrayList<JButton[][]>();
		panelList = new ArrayList<JPanel>();
		buttonCancelList = new ArrayList<JButton>();
		buttonClearList = new ArrayList<JButton>();
		labelList = new ArrayList<JLabel>();

		R r = R.getInstance();
		try {
			buttonGroupList.add((JButton[][]) r.getObject("btn_group_1"));
			buttonGroupList.add((JButton[][]) r.getObject("btn_group_2"));

			panelList.add((JPanel) r.getObject("panel_1"));
			panelList.add((JPanel) r.getObject("panel_2"));

			JButton[] btn = (JButton[]) r.getObject("btn_back_and_clear");
			buttonCancelList.add(btn[0]);
			buttonCancelList.add(btn[2]);

			buttonClearList.add(btn[1]);
			buttonClearList.add(btn[3]);

			labelList.add((JLabel) r.getObject("lbl_1"));
			labelList.add((JLabel) r.getObject("lbl_2"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < buttonCancelList.size(); ++i) {
			if (arg0.getSource() == buttonCancelList.get(i)) {
				cancel(panelList.get(i), buttonGroupList.get(i),
						labelList.get(i), buttonCancelList.get(i),
						buttonClearList.get(i));
				break;
			}
		}
	}

	// 撤销按钮
	private void cancel(JPanel panel, JButton[][] button1, JLabel label1,
			JButton btn_cancel, JButton btn_clear) {
		btn_clear.setVisible(false);
		btn_cancel.setVisible(false);
		panel.removeAll();
		panel.setLayout(new GridLayout(8, 8));

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				panel.add(button1[i][j]);

		label1.setText("请选择矩阵的行数和列数.");
		
		if(label1==labelList.get(1))
			label1.setBounds(280,40,300,20);
		else
			label1.setBounds(20,40,300,20);
		
		panel.validate();
		panel.repaint();
	}
}
