package matrix.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import matrix.r.R;

public class MousePolice implements MouseListener, MouseMotionListener {
	private ArrayList<JButton[][]> buttonList;
	private ArrayList<JLabel> labelList;

	public MousePolice() {
		R r = R.getInstance();
		buttonList = new ArrayList<JButton[][]>();
		labelList = new ArrayList<JLabel>();
		try {
			buttonList.add((JButton[][]) r.getObject("btn_group_1"));
			buttonList.add((JButton[][]) r.getObject("btn_group_2"));

			labelList.add((JLabel) r.getObject("lbl_1"));
			labelList.add((JLabel) r.getObject("lbl_2"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int k = 0; k < buttonList.size(); ++k) {
			JButton[][] button = buttonList.get(k);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == button[i][j]) {
						labelList.get(k).setBounds(70 + k * 260, 40, 300, 20);
						labelList.get(k).setText(
								"选择：" + (i + 1) + " × " + (j + 1) + " ");
						for (int x = 0; x <= i; x++) {
							for (int y = 0; y <= j; y++) {
								button[x][y].setBackground(new Color(45, 239,
										226));
							}
						}
						return;
					}
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int k = 0; k < buttonList.size(); ++k) {
			JButton[][] button = buttonList.get(k);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == button[i][j]) {
						labelList.get(k).setBounds(20 + k * 260, 40, 300, 20);
						labelList.get(k).setText("请选择矩阵的行数和列数.");
						for (int x = 0; x < 8; x++) {
							for (int y = 0; y < 8; y++) {
								button[x][y].setBackground(new Color(234, 234,
										234));
							}
						}
						return;
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}