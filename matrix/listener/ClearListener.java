package matrix.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import matrix.r.R;

public class ClearListener implements ActionListener {

	private ArrayList<JTextField[][]> textList;
	private ArrayList<JButton> buttonList;

	public ClearListener() {
		textList = new ArrayList<JTextField[][]>();
		buttonList = new ArrayList<JButton>();
		R r = R.getInstance();
		try {
			JButton[] btn = (JButton[]) r.getObject("btn_back_and_clear");
			buttonList.add(btn[1]);
			buttonList.add(btn[3]);

			textList.add((JTextField[][]) r.getObject("txt_group_1"));
			textList.add((JTextField[][]) r.getObject("txt_group_2"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < buttonList.size(); ++i) {
			if (arg0.getSource() == buttonList.get(i)) {
				clearTextField(textList.get(i));
				break;
			}
		}
	}

	// Çå³ý°´Å¥
	private void clearTextField(JTextField[][] text) {
		for (int i = 0; i < text.length; i++)
			for (int j = 0; j < text[0].length; j++)
				text[i][j].setText("");
		text[0][0].requestFocusInWindow();
	}
}
