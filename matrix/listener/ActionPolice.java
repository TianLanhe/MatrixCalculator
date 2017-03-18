package matrix.listener;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import matrix.Matrix;
import matrix.r.R;
import matrix.window.WindowIn;

public class ActionPolice implements ActionListener {
	private JPanel panel1, panel2;
	private JTextField text1[][], text2[][];
	private JTextField text;// 数乘实数文本框
	private WindowIn win;
	private JComboBox<String> combobox;

	@SuppressWarnings("unchecked")
	public ActionPolice() {
		R r = R.getInstance();
		try {
			win = (WindowIn) r.getObject("window_in");

			panel1 = (JPanel) r.getObject("panel_1");
			panel2 = (JPanel) r.getObject("panel_2");

			text1 = (JTextField[][]) r.getObject("txt_group_1");
			text2 = (JTextField[][]) r.getObject("txt_group_2");
			text = (JTextField) r.getObject("txt_hint_mult");

			combobox = (JComboBox<String>) r.getObject("combobox");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int item = combobox.getSelectedIndex();

		if (item == 0) {
			JOptionPane.showMessageDialog(null, "请选择计算类型！", "提醒",
					JOptionPane.WARNING_MESSAGE);
		} else if (item > 7 && item < 13) { // 两个矩阵
			if (!isSelected(panel1)) {
				JOptionPane.showMessageDialog(null, "请选择第一个矩阵的行列数！", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else if (!isSelected(panel2)) {
				JOptionPane.showMessageDialog(null, "请选择第二个矩阵的行列数！", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel1, text1)) {
				JOptionPane.showMessageDialog(win, "您没有为第一个矩阵的每个元素赋值", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel2, text2)) {
				JOptionPane.showMessageDialog(win, "您没有为第二个矩阵的每个元素赋值", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Matrix matrix1 = new Matrix(
						getValueFromTextField(panel1, text1));
				Matrix matrix2 = new Matrix(
						getValueFromTextField(panel2, text2));

				switch (item) {
					case 8 :// 相乘
						matrix1.matrixmuti(matrix2);
						popupDialog("相乘", matrix1.getOutput());
						break;
					case 9 :// 相加
						matrix1.add(matrix2);
						popupDialog("相加", matrix1.getOutput());
						break;
					case 10 :// 相减
						matrix1.sub(matrix2);
						popupDialog("相减", matrix1.getOutput());
						break;
					case 11 :// 向量组等价
						if (matrix1.isEqual(matrix2))
							JOptionPane.showMessageDialog(win, "这两个向量组等价！",
									"计算结果", JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(win, "这两个向量组不等价！",
									"计算结果", JOptionPane.PLAIN_MESSAGE);
						break;
					case 12 :// 能否线性表示
						if (matrix1.canRepresent(matrix2))
							JOptionPane.showMessageDialog(win,
									"B(右边)向量组能被A(左边)向量组线性表示！", "计算结果",
									JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(win,
									"B(右边)向量组不能被A(左边)向量组线性表示！", "计算结果",
									JOptionPane.PLAIN_MESSAGE);
						break;
				}
			}
		} else { // 一个矩阵
			if (!isSelected(panel1)) {
				JOptionPane.showMessageDialog(null, "请选择矩阵的行列数！", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel1, text1)) {
				JOptionPane.showMessageDialog(win, "您没有为矩阵的每个元素赋值", "提醒",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Matrix matrix = new Matrix(getValueFromTextField(panel1, text1));

				if (item == 1) {
					double result = matrix.getResult();
					JOptionPane.showMessageDialog(win, "行列式的值为：" + result,
							"计算结果", JOptionPane.PLAIN_MESSAGE);
				} else if (item == 2) {
					if (text.getText().equals(""))
						JOptionPane.showMessageDialog(null, "您没有输入相乘的实数！",
								"提醒", JOptionPane.WARNING_MESSAGE);
					else {
						matrix.scalarmuti(Double.parseDouble(text.getText()));
						popupDialog("数乘", matrix.getOutput());
					}
				} else if (item == 3) {
					if (matrix.getResult() == 0)
						JOptionPane.showMessageDialog(win,
								"这个矩阵的行列式为0，无法求其逆阵！", "计算结果",
								JOptionPane.WARNING_MESSAGE);
					else {
						matrix.inverse();
						popupDialog("逆阵", matrix.getOutput());
					}
				} else if (item == 4) {
					int rank = matrix.getRank();
					JOptionPane.showMessageDialog(win, "矩阵的秩为：" + rank, "计算结果",
							JOptionPane.PLAIN_MESSAGE);
				} else if (item == 5) {
					double given[] = matrix.given();
					String out = null;
					if (given[0] == given[1] && given[1] == given[2])
						out = "λ1=λ2=λ3=" + given[0];
					else if (given[1] == given[2])
						out = "λ1=" + given[0] + "  " + "λ2=λ3=" + given[1];
					else if (given[0] == given[2])
						out = "λ1=λ2=" + given[0] + "  " + "λ3=" + given[1];
					else
						out = "λ1=" + given[0] + "  " + "λ2=" + given[1] + "  "
								+ "λ3=" + given[2];
					JOptionPane.showMessageDialog(win, "矩阵的特征值为：\n" + out,
							"计算结果", JOptionPane.PLAIN_MESSAGE);
				} else if (item == 6) {
					matrix.simplest();
					popupDialog("最简", matrix.getOutput());
				} else if (item == 7) {
					matrix.maxIrrelevant();
					popupDialog("无关", matrix.getOutput());
				} else if (item == 13) {
					if (matrix.isRelevant())
						JOptionPane.showMessageDialog(win, "此向量组线性相关！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(win, "此向量组线性无关！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
				} else if (item == 14) {
					if (matrix.getInput()[0].length == matrix.getRank())
						JOptionPane.showMessageDialog(win,
								"齐次线性方程组的解唯一，无通解，自己算→_→！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
					else {
						matrix.homogen();
						popupDialog("齐通", matrix.getOutput());
					}
				} else if (item == 15) {
					if (matrix.getInput()[0].length == matrix.getRank())
						JOptionPane.showMessageDialog(win,
								"齐次线性方程组的解唯一，无基础解系，自己算→_→！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
					else {
						matrix.homogenBasic();
						popupDialog("齐系", matrix.getOutput());
					}
				} else if (item == 16) {
					boolean operation = true;
					matrix.simplest();
					double[][] f = matrix.getOutput();
					for (int i = 0; i < f[0].length; i++) {
						if (f[f.length - 1][i] == 1 && i == f[0].length - 1) {
							operation = false;
							JOptionPane.showMessageDialog(win, "非齐次线性方程组无解！",
									"计算结果", JOptionPane.PLAIN_MESSAGE);
						}
					}
					if (f[0].length == matrix.getRank() + 1) {
						operation = false;
						JOptionPane.showMessageDialog(win,
								"齐次线性方程组的解唯一，无通解，自己算→_→！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
					}
					if (operation) {
						matrix.nonhomogen();
						popupDialog("非通", matrix.getOutput());
					}
				} else if (item == 17) {
					boolean operation = true;
					matrix.simplest();
					double[][] f = matrix.getOutput();
					matrix.simplest();
					for (int i = 0; i < f[0].length; i++) {
						if (f[f.length - 1][i] == 1 && i == f[0].length - 1) {
							operation = false;
							JOptionPane.showMessageDialog(win, "非齐次线性方程组无解！",
									"计算结果", JOptionPane.PLAIN_MESSAGE);
						}
					}
					if (f[0].length == matrix.getRank() + 1) {
						operation = false;
						JOptionPane.showMessageDialog(win,
								"齐次线性方程组的解唯一，无基础解系，自己算→_→！", "计算结果",
								JOptionPane.PLAIN_MESSAGE);
					}
					if (operation) {
						matrix.nonhomogenBasic();
						popupDialog("非系", matrix.getOutput());
					}
				}
			}
		}
	}

	private void popupDialog(String a, double[][] f) { // 输出运算结果
		JLabel label = new JLabel("");
		if (a == "齐通" || a == "非通" || a == "齐系" || a == "非系") {
			if (a == "齐通")
				label.setText("齐次线性方程组的通解为：");
			if (a == "非通")
				label.setText("非齐次线性方程组的通解为：");
			if (a == "齐系")
				label.setText("齐次线性方程组的基础解析系为");
			if (a == "非系")
				label.setText("非齐次线性方程组的基础解析系为：");
			JPanel pane = new JPanel();
			JTextField text[][][] = new JTextField[3][f.length][f[0].length];
			pane.setLayout(new GridLayout(f.length, f[0].length * 3));
			for (int k = 0; k < 3; k++) {
				for (int i = 0; i < f.length; i++) {
					for (int j = 0; j < f[0].length; j++) {
						text[k][i][j] = new JTextField(2);
						if (k == 0 && i == (f.length - 1) / 2) {
							if (j == 0)
								text[0][i][j].setText("=");
							else
								text[0][i][j].setText("+");
						} else if (k == 1 && i == (f.length - 1) / 2) {
							if (a == "齐系" || a == "非系")
								text[1][i][j].setText("ξ" + (j + 1));
							else if (a == "非通" && j != f[0].length - 1)
								text[1][i][j].setText("c" + (j + 1));
						} else if (k == 2) {
							text[2][i][j].setText(String.valueOf(f[i][j]));
						}
						text[k][i][j].setEditable(false);
					}
				}
			}
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					for (int k = 0; k < 3; k++) {
						pane.add(text[k][i][j]);
					}
				}
			}
			JPanel pane_out = new JPanel();
			pane_out.setLayout(new BorderLayout());
			pane_out.add(label, BorderLayout.NORTH);
			pane_out.add(pane, BorderLayout.SOUTH);
			JOptionPane.showMessageDialog(win, pane_out, "计算结果",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			if (a == "逆阵")
				label.setText("矩阵的逆矩阵为：");
			if (a == "相乘")
				label.setText("两个矩阵相乘：");
			if (a == "相加")
				label.setText("同型矩阵相加：");
			if (a == "相减")
				label.setText("同型矩阵相减：");
			if (a == "数乘")
				label.setText("数和矩阵相乘：");
			if (a == "最简")
				label.setText("矩阵的行最简形为：");
			if (a == "无关")
				label.setText("向量组的最大线性无关组为：");
			JPanel pane = new JPanel();
			JTextField text_out[][] = new JTextField[f.length][f[0].length];
			pane.setLayout(new GridLayout(f.length, f[0].length));
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					text_out[i][j] = new JTextField(2);
					text_out[i][j].setText(String.format("%.2f", f[i][j]));//保留位数问题
					text_out[i][j].setEditable(false);
					pane.add(text_out[i][j]);
				}
			}
			JPanel pane_out = new JPanel();
			pane_out.setLayout(new BorderLayout());
			pane_out.add(label, BorderLayout.NORTH);
			pane_out.add(pane, BorderLayout.SOUTH);
			JOptionPane.showMessageDialog(win, pane_out, "计算结果",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 将文本框的内容转存到数组中
	private double[][] getValueFromTextField(JPanel panel, JTextField[][] text) {
		GridLayout gridLayout = (GridLayout) panel.getLayout();
		int row = gridLayout.getRows();
		int column = gridLayout.getColumns();
		double[][] ret = new double[row][column];
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < column; ++j)
				ret[i][j] = Double.parseDouble(text[i][j].getText());
		return ret;
	}

	// 判断该面板中的TextField是否都有赋值
	private boolean hasAssigned(JPanel panel, JTextField[][] text) {
		GridLayout gridLayout = (GridLayout) panel.getLayout();
		int row = gridLayout.getRows();
		int column = gridLayout.getColumns();
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < column; ++j)
				if ("".equals(text[i][j].getText()))
					return false;
		return true;
	}

	// 判断该面板是否选择了行列数
	private boolean isSelected(JPanel panel) {
		return panel.getComponents()[0] instanceof JTextField;
	}
}