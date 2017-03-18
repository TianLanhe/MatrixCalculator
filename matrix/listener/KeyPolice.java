package matrix.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import matrix.r.R;

public class KeyPolice implements KeyListener {
	private JButton button_cal, button_exit;

	public KeyPolice() {
		R r = R.getInstance();
		try {
			button_cal = (JButton) r.getObject("btn_cal");
			button_exit = (JButton) r.getObject("btn_exit");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			if (e.getSource() == button_exit) {
				System.exit(0);
			} else if (e.getSource() == button_cal) {
				ActionListener[] listeners = button_cal.getActionListeners();
				ActionEvent event=new ActionEvent(button_cal, 0, null);
				for(ActionListener listener:listeners)
					listener.actionPerformed(event);	//手动向调用该监听器的方法
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() instanceof JTextField) {
			if (!((e.getKeyChar() > 47 && e.getKeyChar() < 58)
					|| e.getKeyChar() == 46
					|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e
						.getKeyChar() == 45)) {
				JTextField textfield = (JTextField) e.getSource();
				textfield.setText("");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
}