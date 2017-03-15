package matrix.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import matrix.r.R;
import matrix.window.WindowIn;

public class ItemPolice implements ItemListener {
	private JComboBox<String> combobox;
	private WindowIn win;
	private JPanel pane2, pane3;
	private JLabel label2;
	private JLabel label3;// 数乘提示文字
	private JTextField text;// 数乘输入数字
	private JButton button3, button4;

	@SuppressWarnings("unchecked")
	public ItemPolice() {
		try {
			R r = R.getInstance();

			win = (WindowIn) r.getObject("window_in");

			combobox = (JComboBox<String>) r.getObject("combobox");

			label2 = (JLabel) r.getObject("lbl_2");
			label3 = (JLabel) r.getObject("lbl_3");

			pane2 = (JPanel) r.getObject("panel_2");
			pane3 = (JPanel) r.getObject("panel_cal");

			text = (JTextField) r.getObject("txt_hint_mult");

			JButton[] btn = (JButton[]) r.getObject("btn_back_and_clear");
			button3 = btn[2];
			button4 = btn[3];

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int item = combobox.getSelectedIndex();

			// 根据用户选择的计算类型，隐藏或显示矩阵
			if (item > 7 && item < 13) { // 两个矩阵
				win.setBounds(100, 100, 800, 380);
				if (label2.getText().startsWith("选择")) {
					if (pane2.getX() != 290)
						label2.setBounds(330, 40, 300, 20);
				}
				pane2.setBounds(290, 70, 200, 200);
				pane3.setBounds(508, 74, 280, 150);
				button3.setLocation(290, 280);
				button4.setLocation(390, 280);
				text.setVisible(false);
				label3.setVisible(false);
			} else if (item != 0) { // 一个矩阵
				win.setBounds(350, 100, 550, 380);
				pane2.setLocation(990, 70);
				pane3.setLocation(248, 74);
				label2.setLocation(580, 40);
				button3.setLocation(580, 40);
				button4.setLocation(580, 40);
				text.setVisible(false);
				label3.setVisible(false);
				if (item == 2) {
					text.setVisible(true);
					label3.setVisible(true);
				}
			}
		}
	}
}