package matrix.listener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import matrix.r.R;

public class ButtonGroupListener implements ActionListener {

	private JComboBox<String> combobox;
	private ArrayList<JButton[][]> buttonGroupList;
	private ArrayList<JButton> buttonCancelList;
	private ArrayList<JButton> buttonClearList;
	private ArrayList<JPanel> panelList;
	private ArrayList<JTextField[][]> textList;

	@SuppressWarnings("unchecked")
	public ButtonGroupListener() {
		buttonGroupList = new ArrayList<JButton[][]>();
		buttonCancelList = new ArrayList<JButton>();
		buttonClearList = new ArrayList<JButton>();
		panelList = new ArrayList<JPanel>();
		textList = new ArrayList<JTextField[][]>();

		R r = R.getInstance();
		try {
			combobox = (JComboBox<String>) r.getObject("combobox");

			buttonGroupList.add((JButton[][]) r.getObject("btn_group_1"));
			buttonGroupList.add((JButton[][]) r.getObject("btn_group_2"));

			JButton[] btn = (JButton[]) r.getObject("btn_back_and_clear");
			buttonCancelList.add(btn[0]);
			buttonCancelList.add(btn[2]);

			buttonClearList.add(btn[1]);
			buttonClearList.add(btn[3]);

			panelList.add((JPanel) r.getObject("panel_1"));
			panelList.add((JPanel) r.getObject("panel_2"));

			textList.add((JTextField[][]) r.getObject("txt_group_1"));
			textList.add((JTextField[][]) r.getObject("txt_group_2"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int item = combobox.getSelectedIndex();
		if (item == 0) {
			JOptionPane.showMessageDialog(null, "请先选择运算类型", "提醒",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for (int k = 0; k < 2; ++k) {
			JButton[][] buttonGroup = buttonGroupList.get(k);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == buttonGroup[i][j]) {
						setButtonDefaultColor(buttonGroup, i, j);

						boolean turn = panelList.get(1 - k).getComponents()[0] instanceof JTextField;
						GridLayout gridLayout = (GridLayout) panelList.get(
								1 - k).getLayout();
						int row = gridLayout.getRows();
						int column = gridLayout.getColumns();

						if ((item == 1 || item == 3) && i != j)
							JOptionPane.showMessageDialog(null,
									"矩阵的行数和列数必须相等！", "提醒",
									JOptionPane.WARNING_MESSAGE);
						else if (item == 5 && (i != 2 || j != 2))
							JOptionPane.showMessageDialog(null,
									"对不起，特征值运算只限三阶矩阵！", "提醒",
									JOptionPane.WARNING_MESSAGE);
						else if ((item == 10 || item == 9) && turn
								&& ((i + 1) != row || (j + 1) != column))
							JOptionPane.showMessageDialog(null, "请输入同型矩阵！",
									"提醒", JOptionPane.WARNING_MESSAGE);
						else if ((item == 11 || item == 12) && turn
								&& (i + 1) != row)
							JOptionPane.showMessageDialog(null, "两个矩阵的行数必须相同！",
									"提醒", JOptionPane.WARNING_MESSAGE);
						else if (item == 8 && turn && (j + 1) != row)
							JOptionPane.showMessageDialog(null,
									"第一个矩阵的列数必须与第二个矩阵的行数相同！", "提醒",
									JOptionPane.WARNING_MESSAGE);
						else {
							JPanel panel = panelList.get(k);
							JTextField[][] text = textList.get(k);

							panel.removeAll();
							panel.setLayout(new GridLayout(i + 1, j + 1));

							for (int x = 0; x <= i; x++) {
								for (int y = 0; y <= j; y++) {
									text[x][y].setText("");
									panel.add(text[x][y]);
								}
							}
							text[0][0].requestFocusInWindow();
							panel.validate();
							panel.repaint();

							buttonCancelList.get(k).setVisible(true);
							buttonClearList.get(k).setVisible(true);
						}
						return;
					}
				}
			}
		}
	}

	private void setButtonDefaultColor(JButton[][] buttonGroup, int x, int y) {
		Color color = new Color(234, 234, 234);
		for (int i = 0; i <= x; ++i)
			for (int j = 0; j <= y; ++j)
				buttonGroup[i][j].setBackground(color);
	}

}
