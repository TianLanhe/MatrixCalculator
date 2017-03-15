package matrix.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import matrix.Matrix;
import matrix.r.R;
import matrix.window.WindowIn;

public class ActionPolice implements ActionListener{
	private JButton button1,button2;
	private JTextField text1[][],text2[][];
	private JTextField text;//数乘实数文本框
	private WindowIn win;
	private Matrix matrix1;
	private Matrix matrix2;
	private JComboBox<String> combobox;
	
	@SuppressWarnings("unchecked")
	public ActionPolice(){
		R r=R.getInstance();
		matrix1=new Matrix();
		matrix2=new Matrix();
		try{
			win=(WindowIn) r.getObject("window_in");
			
			JButton[] btn=(JButton[]) r.getObject("btn_back_and_clear");
			button1=btn[0];
			button2=btn[2];
			
			text1=(JTextField[][]) r.getObject("txt_group_1");
			text2=(JTextField[][]) r.getObject("txt_group_2");
			text=(JTextField) r.getObject("txt_hint_mult");
			
			matrix1.setWindow(win);
			matrix2.setWindow(win);
			
			combobox=(JComboBox<String>) r.getObject("combobox");
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		int item=combobox.getSelectedIndex();
		if(item>7&&item<13){                                          //两个矩阵
			if(!(button1.isVisible()&&button2.isVisible())){
				if(!button1.isVisible()&&!button2.isVisible())JOptionPane.showMessageDialog(null,"请选择两个矩阵的行列数！","提醒",JOptionPane.WARNING_MESSAGE);
				else if(!button1.isVisible())JOptionPane.showMessageDialog(null,"请选择第一个矩阵的行列数！","提醒",JOptionPane.WARNING_MESSAGE);
				else JOptionPane.showMessageDialog(null,"请选择第二个矩阵的行列数！","提醒",JOptionPane.WARNING_MESSAGE);
			}else{
				matrix1.input(text1);
				matrix2.input(text2);
				if(item==8&&matrix1.turn&&matrix2.turn){
					matrix1.matrixmuti(matrix2);
					matrix1.output("相乘");
				}else if(item==9&&matrix1.turn&&matrix2.turn){
					matrix1.add(matrix2);
					matrix1.output("相加");
				}else if(item==10&&matrix1.turn&&matrix2.turn){
					matrix1.sub(matrix2);
					matrix1.output("相减");
				}else if(item==11&&matrix1.turn&&matrix2.turn){
					if(matrix1.isEqual(matrix2))JOptionPane.showMessageDialog(win,"这两个向量组等价！","计算结果",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"这两个向量组不等价！","计算结果",JOptionPane.PLAIN_MESSAGE);
				}else if(item==12&&matrix1.turn&&matrix2.turn){
					if(matrix1.canRepresent(matrix2))JOptionPane.showMessageDialog(win,"B(右边)向量组能被A(左边)向量组线性表示！","计算结果",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"B(右边)向量组不能被A(左边)向量组线性表示！","计算结果",JOptionPane.PLAIN_MESSAGE);
				}
			}
		}else if(item==0){JOptionPane.showMessageDialog(null,"请选择计算类型！","提醒",JOptionPane.WARNING_MESSAGE);
		}else{                                                                  //一个矩阵
			if(!button1.isVisible()){
				JOptionPane.showMessageDialog(null,"请选择矩阵的行列数！","提醒",JOptionPane.WARNING_MESSAGE);
			}else{
				matrix1.input(text1);
				if(item==1&&matrix1.turn){
					double result=matrix1.getResult();
					JOptionPane.showMessageDialog(win,"行列式的值为："+result,"计算结果",JOptionPane.PLAIN_MESSAGE);
				}else if(item==2){
					if(text.getText().equals(""))JOptionPane.showMessageDialog(null,"您没有输入相乘的实数！","提醒",JOptionPane.WARNING_MESSAGE);
					else if(matrix1.turn){
						matrix1.scalarmuti(Double.parseDouble(text.getText()));
						matrix1.output("数乘");
					}
				}else if(item==3&&matrix1.turn){
					if(matrix1.getResult()==0)JOptionPane.showMessageDialog(win,"这个矩阵的行列式为0，无法求其逆阵！","计算结果",JOptionPane.WARNING_MESSAGE);
					else{
						matrix1.inverse();
						matrix1.output("逆阵");
					}
				}else if(item==4&&matrix1.turn){
					int rank=matrix1.getRank();
					JOptionPane.showMessageDialog(win,"矩阵的秩为："+rank,"计算结果",JOptionPane.PLAIN_MESSAGE);
				}else if(item==5){
					double given[]=matrix1.given();
					String out=null;
					if(given[0]==given[1]&&given[1]==given[2])out="λ1=λ2=λ3="+given[0];
	            else if(given[1]==given[2])out="λ1="+given[0]+"  "+"λ2=λ3="+given[1];
	            else if(given[0]==given[2])out="λ1=λ2="+given[0]+"  "+"λ3="+given[1];
	            else out="λ1="+given[0]+"  "+"λ2="+given[1]+"  "+"λ3="+given[2];
					if(matrix1.turn)JOptionPane.showMessageDialog(win,"矩阵的特征值为：\n"+out,"计算结果",JOptionPane.PLAIN_MESSAGE);
				}else if(item==6){
					matrix1.simplest();
					matrix1.output("最简");
				}else if(item==7){
					matrix1.maxIrrelevant();
					matrix1.output("无关");
				}else if(item==13){
					if(matrix1.isRelevant())JOptionPane.showMessageDialog(win,"此向量组线性相关！","计算结果",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"此向量组线性无关！","计算结果",JOptionPane.PLAIN_MESSAGE);
				}else if(item==14){
					if(matrix1.b[0].length==matrix1.getRank())JOptionPane.showMessageDialog(win,"齐次线性方程组的解唯一，无通解，自己算→_→！","计算结果",JOptionPane.PLAIN_MESSAGE);
					else {
						matrix1.homogen();
						matrix1.output("齐通");
					}
				}else if(item==15){
					if(matrix1.b[0].length==matrix1.getRank())JOptionPane.showMessageDialog(win,"齐次线性方程组的解唯一，无基础解系，自己算→_→！","计算结果",JOptionPane.PLAIN_MESSAGE);
					else {
						matrix1.homogenBasic();
						matrix1.output("齐系");
					}
				}else if(item==16){
					boolean operation=true;
					matrix1.simplest();
					for(int i=0;i<matrix1.f[0].length;i++){
						if(matrix1.f[matrix1.f.length-1][i]==1&&i==matrix1.f[0].length-1){
							operation=false;
							JOptionPane.showMessageDialog(win,"非齐次线性方程组无解！","计算结果",JOptionPane.PLAIN_MESSAGE);
						}
					}
					if(matrix1.f[0].length==matrix1.getRank()+1){
						operation=false;
						JOptionPane.showMessageDialog(win,"齐次线性方程组的解唯一，无通解，自己算→_→！","计算结果",JOptionPane.PLAIN_MESSAGE);
					}
					if(operation){
						matrix1.nonhomogen();
						matrix1.output("非通");
					}
				}else if(item==17){
					boolean operation=true;
					matrix1.simplest();
					for(int i=0;i<matrix1.f[0].length;i++){
						if(matrix1.f[matrix1.f.length-1][i]==1&&i==matrix1.f[0].length-1){
							operation=false;
							JOptionPane.showMessageDialog(win,"非齐次线性方程组无解！","计算结果",JOptionPane.PLAIN_MESSAGE);
						}
					}
					if(matrix1.f[0].length==matrix1.getRank()+1){
						operation=false;
						JOptionPane.showMessageDialog(win,"齐次线性方程组的解唯一，无基础解系，自己算→_→！","计算结果",JOptionPane.PLAIN_MESSAGE);
					}
					if(operation){
						matrix1.nonhomogenBasic();
						matrix1.output("非系");
					}
				}
			}
		}
	}
}