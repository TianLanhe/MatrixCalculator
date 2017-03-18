package matrix;

public class Matrix {
	public double b[][]; // 储存输入的数组
	public double f[][]; // 储存运算结果

	public Matrix() {
		b = null;
		f = null;
	}

	public Matrix(double[][] input) {
		setInput(input);
	}

	public void setInput(double[][] input) {
		b = copyArray(input);
	}
	
	public double[][] getInput(){
		return b;
	}

	public double[][] getOutput() {
		Double zero = new Double(-0.0); // 将负零转化为正零，去掉零的负号
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (zero.equals(f[i][j]))
					f[i][j] *= -1;
				if (Math.ceil(f[i][j]) - f[i][j] <= 10e-5)
					f[i][j] = Math.ceil(f[i][j]);
				if (f[i][j] - Math.floor(f[i][j]) <= 10e-5)
					f[i][j] = Math.floor(f[i][j]);
			}
		}
		return f;
	}

	/*
	 * 将矩阵化为上三角形，再将对角线的元素相乘。首先依次检索对角线（x行x列）上的元素，若是0则此行所有
	 * 元素加上下面一行元素使其不是0.若依然是0，则加上下面第二行元素，以此类推。直至对角线上元素不为0或已
	 * 加到最后一行。然后此行一下所有行元素减去此行元素，使第x列元素均为零。
	 */
	public double getResult() { // 求n阶行列式的值
		int x = 0;
		double item, result = 1;
		double jz[][] = copyArray(b);
		for (int i = 0; i < b.length - 1; i++) {
			x = i + 1;
			while (Math.abs(b[i][i]) <= 1e-5) {
				for (int q = i; q < b[0].length; q++) {
					b[i][q] += b[x][q];
				}
				x += 1;
				if (x == b.length)
					break;
			}
			for (int j = i + 1; j < b.length; j++) {
				if (Math.abs(b[i][i]) <= 1e-5)
					break;
				item = b[j][i] / b[i][i];
				for (int k = i; k < b[0].length; k++)
					b[j][k] -= item * b[i][k];
			}
		}
		for (int i = 0; i < b.length; i++) { // 将错误的极小的数转化为0
			for (int j = i; j < b[0].length; j++) {
				if (Math.abs(b[i][j]) <= 1e-5)
					b[i][j] = 0;
			}
		}
		for (int i = 0; i < b.length; i++)
			result *= b[i][i];
		b = jz;

		/*
		 * Double zero = new Double(-0.0); // 将负零转化为正零，去掉零的负号 for (int i = 0; i
		 * < b.length; i++) { for (int j = 0; j < b[0].length; j++) { if
		 * (zero.equals(b[i][j])) b[i][j] *= -1; } }
		 */

		// 有时result=3但却会计算成2.99999999，所以要转换一下
		if (Math.ceil(result) - result <= 1e-5)
			result = Math.ceil(result);
		if (result - Math.floor(result) <= 1e-5)
			result = Math.floor(result);
		return result;
	}

	public void add(Matrix r) { // 求两个矩阵相加
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				f[i][j] = b[i][j] + r.b[i][j];
			}
		}
	}

	public void sub(Matrix r) { // 求两个矩阵相减
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				f[i][j] = b[i][j] - r.b[i][j];
			}
		}
	}

	public void scalarmuti(double n) { // 求矩阵的数乘
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++)
				f[i][j] = b[i][j] * n;
		}
	}

	public void trans() { // 求矩阵的转置
		f = new double[b[0].length][b.length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++)
				f[j][i] = b[i][j];
		}
	}

	/*
	 * 思路是依次求各元素的代数余子式，再将代数余子式的矩阵转置即可。重点是如何求各元素代数余子式组成
	 * 的数组。先确定某一元素，计算（-1）^(i+j)确定代数余子式符号，提取出与该元素不同行且不同列的所有元
	 * 素，储存在一个一位数组中，提取完成后将这些在一位数组中的元素放进一个二维数组中（形成矩阵），再求
	 * 矩阵的行列式的值，乘以代数余子式符号，即得一个代数余子式，如此便是一个循环，循环i行j列次并将结果
	 * 储存在中转数组i行j列，结束循环后便得到各元素代数余子式组成的矩阵，再将其转置即可
	 */
	public void adjoint() { // 求n阶矩阵的伴随阵
		double d[][] = new double[b.length][b[0].length];
		f = new double[b.length][b[0].length];
		int n = (b.length - 1) * (b.length - 1);
		double e[] = new double[n];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				int w = 0;
				double k = Math.pow(-1, j + i);
				for (int p = 0; p < b.length; p++) {
					if (p == i)
						continue;
					for (int q = 0; q < b[0].length; q++) {
						if (q == j)
							continue;
						e[w++] = b[p][q];
					}
				}
				w = 0;
				Matrix r = new Matrix();
				r.b = new double[b.length - 1][b.length - 1];
				for (int x = 0; x < r.b.length; x++) {
					for (int y = 0; y < r.b[0].length; y++)
						r.b[x][y] = e[w++];
				}
				d[i][j] = k * r.getResult();
			}
		}
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++)
				f[i][j] = d[j][i];
		}
	}
	/*
	 * 先用上面的方法求出矩阵的伴随，再用上面行列式求值的方法求出矩阵的行列式的值。但其值k不为0时，矩阵
	 * 有逆阵，再调用上面数乘的方法将k乘以伴随阵，得出逆矩阵。（中间新建了一个Matrix对象将运算结果伴随阵 转化为对象的输入结果进行数乘运算）
	 */
	public void inverse() { // 求n阶方阵的逆
		adjoint();
		double t = 1 / getResult();
		Matrix r = new Matrix();
		r.b = f;
		r.scalarmuti(t);
		f = r.f;
	}

	/*
	 * 新建f的长度为 b.length × r.b[0].length，即第一个矩阵的行数乘以第二个矩阵的列数。设置三个循环
	 * 一个控制第一个矩阵的行数，第二个循环控制第二个矩阵的列数，第三个循环依次检索第一（二）个矩阵那
	 * 一行（列）的各个元素，将其相乘并加起来，第二个循环循环一次（即第三个循环结束一次）得到新矩阵的 一个元素
	 */
	public void matrixmuti(Matrix r) { // 求两个矩阵相乘
		f = new double[b.length][r.b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int k = 0; k < r.b[0].length; k++) {
				for (int j = 0; j < b[0].length; j++) {
					f[i][k] += b[i][j] * r.b[j][k];
				}
			}
		}
	}

	public void simplest() { // 求矩阵的行最简形
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) { // 将b矩阵copy给f矩阵
			for (int j = 0; j < b[0].length; j++)
				f[i][j] = b[i][j]; // 由f矩阵运算出行最简形
		}
		for (int i = 0; i < f.length; i++) {
			int column = i;
			if (column == f[0].length)
				break;
			int x = i;
			if (Math.abs(f[i][column]) <= 10e-7)
				f[i][column] = 0;
			while (f[i][column] == 0) { // 若该书为0，此while可将其
				if (f[x][column] != 0) { // 加上下面行，使其不为0
					for (int q = column; q < f[0].length; q++)
						f[i][q] += f[x][q];
				}
				x++;
				if (x == f.length && f[i][column] == 0) {
					column++;
					x = i;
				}
				if (column == f[0].length) {
					column--;
					break;
				}
				if (Math.abs(f[i][column]) <= 10e-7)
					f[i][column] = 0;
			}
			if (Math.abs(f[i][column]) <= 10e-7)
				f[i][column] = 0;
			for (int j = 0; j < f.length; j++) { // 将非0数同列的数化为0
				if (f[i][column] == 0)
					break;
				if (j == i)
					continue;
				double item = f[j][column] / f[i][column];
				for (int k = column; k < f[0].length; k++) {
					f[j][k] -= item * f[i][k];
				}
			}
		}
		for (int i = 0; i < f.length; i++) {
			for (int j = i; j < f[0].length; j++) {
				if (f[i][j] == 0)
					continue;
				for (int k = f[0].length - 1; k >= j; k--)
					f[i][k] /= f[i][j];
				if (f[i][j] != 0)
					break;
			}
		}
	}

	/*
	 * 用上面的方法求出最简形（结果储存在f矩阵中），设置两个循环，一个控制最简形的行，一个控制列，
	 * 一行一行从左到右检索，碰到非0数则rank加1，并跳出循环，若一行没有检索到非0数，则rank不会加1
	 */
	public int getRank() { // 求矩阵的秩
		int rank = 0;
		simplest();
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					rank += 1;
					break;
				}
			}
		}
		return rank;
	}
	public double[] given() { // 只允许三阶矩阵，而且只能是整数 //求矩阵的特征值
		double a[] = {21, 21, 21};
		int n = 0;
		f = new double[b.length][b[0].length];
		Matrix r = new Matrix();
		for (int i = -20; i <= 20; i++) {
			for (int k = 0; k < b.length; k++) {
				for (int j = 0; j < b[0].length; j++)
					f[k][j] = b[k][j];
			}
			for (int j = 0; j < f.length; j++)
				f[j][j] -= i;
			r.b = f;
			if (r.getResult() == 0)
				a[n++] = i;
		}
		if (a[1] == 21) {
			a[1] = a[0];
			a[2] = a[0];
		} else if (a[2] == 21) {
			if (a[0] * a[1] * a[1] == getResult())
				a[2] = a[1];
			else
				a[2] = a[0];
		}
		return a;
	}
	public void homogen() { // 求齐次线性方程组的通解
		int rank = getRank(); // 超难超复杂，死了我好多脑细胞！！！
		if (f[0].length - rank != 0) { // 算法也不知道对不对，对了也不是最简单的！！！
			int c[] = new int[f[0].length];
			int r[] = new int[f[0].length];
			for (int i = 0; i < c.length; i++)
				c[i] = 1;
			for (int i = 0; i < r.length; i++)
				r[i] = 1;
			double t[][] = new double[f[0].length][f[0].length];
			for (int i = 0; i < f.length; i++) { // 确定哪些列不要
				for (int j = 0; j < f[0].length; j++) {
					if (f[i][j] != 0) {
						c[j] = 0;
						r[i] = 0;
						break;
					}
				}
			}
			for (int i = 0; i < f[0].length; i++) { // 将f矩阵copy给t矩阵，并将该取负的取负，
				for (int j = 0; j < f[0].length; j++) {
					if (i < f.length)
						t[i][j] = -f[i][j];
				}
			}
			int index = 0;
			int location = 0;
			int row[] = new int[rank];
			int column[] = new int[rank];
			for (int i = 0; i < r.length; i++) { // 将每一行第一个1的行数和列数记录下来
				if (r[i] == 1)
					continue; // 便于后面的行交换和列排除
				for (int j = location; j < c.length; j++) {
					if (c[j] == 0) {
						row[index] = i;
						column[index] = j;
						index++;
						location = j + 1;
						break;
					}
				}
			}
			index = rank - 1;
			for (int j = f[0].length; j >= 0; j--) { // 行交换
				if (j == column[index]) {
					if (column[index] != row[index]) {
						for (int q = 0; q < f[0].length; q++) {
							t[column[index]][q] = t[row[index]][q];
							t[row[index]][q] = 0;
						}
						index--;
					}
				}
			}
			for (int i = 0; i < f[0].length; i++) { // 将对应的元素改成1
				for (int j = 0; j < f[0].length; j++) {
					if (i == j)
						t[i][j] = 1;
				}
			}
			f = new double[t[0].length - rank][t[0].length];
			int count;
			for (int i = 0; i < f[0].length; i++) { // 抽取需要的列储存进f数组
				count = 0;
				for (int j = 0; j < f[0].length; j++) {
					if (c[j] == 0)
						continue;
					f[count][i] = t[i][j];
					count++;
				}
			}
			Matrix trans = new Matrix();
			trans.b = f;
			trans.trans();
			f = trans.f;
		} else
			; // 有唯一解的情况，待解决
	}
	public void nonhomogen() { // 求非齐次线性方程组的通解
		int rank = getRank();
		int c[] = new int[f[0].length];
		int r[] = new int[f[0].length];
		for (int i = 0; i < c.length; i++)
			c[i] = 1;
		for (int i = 0; i < r.length; i++)
			r[i] = 1;
		for (int i = 0; i < f.length; i++) { // 确定哪些列不要
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					c[j] = 0;
					r[i] = 0;
					break;
				}
			}
		}
		if ((f[0].length - rank - 1) != 0) {
			double t[][] = new double[f[0].length - 1][f[0].length];
			for (int i = 0; i < t.length; i++) { // 将f矩阵copy给t矩阵，并将该取负的取负，
				for (int j = 0; j < t[0].length; j++) {
					if (i < f.length) {
						if (j != f[0].length - 1)
							t[i][j] = -f[i][j];
						if (j == f[0].length - 1)
							t[i][j] = f[i][j];
					}
				}
			}
			int index = 0;
			int location = 0;
			int row[] = new int[rank];
			int column[] = new int[rank];
			for (int i = 0; i < r.length; i++) { // 将每一行第一个1的行数和列数记录下来
				if (r[i] == 1)
					continue; // 便于后面的行交换和列排除
				for (int j = location; j < c.length; j++) {
					if (c[j] == 0) {
						row[index] = i;
						column[index] = j;
						index++;
						location = j + 1;
						break;
					}
				}
			}
			index = rank - 1;
			for (int j = f[0].length; j >= 0; j--) { // 行交换
				if (j == column[index]) {
					if (column[index] != row[index]) {
						for (int q = 0; q < f[0].length; q++) {
							t[column[index]][q] = t[row[index]][q];
							t[row[index]][q] = 0;
						}
						index--;
					}
				}
			}
			for (int i = 0; i < t.length; i++) { // 将对应的元素改成1
				for (int j = 0; j < t[0].length; j++) {
					if (i == j)
						t[i][j] = 1;
				}
			}
			f = new double[t[0].length - rank][t.length];
			int count;
			for (int i = 0; i < t.length; i++) { // 抽取需要的列储存进f数组
				count = 0;
				for (int j = 0; j < t[0].length; j++) {
					if (c[j] == 0)
						continue;
					f[count][i] = t[i][j];
					count++;
				}
			}
			Matrix trans = new Matrix();
			trans.b = f;
			trans.trans();
			f = trans.f;
		} else
			; // 唯一解的情况，待解决
	}
	public void homogenBasic() { // 求齐次线性方程组的基础解系
		homogen();
	}
	/* 跟求通解差不多，基础解系没有最后一条常数的，去掉常数那一列就好了 */
	public void nonhomogenBasic() { // 求非齐次线性方程组的基础解系
		nonhomogen();
		double t[][] = new double[f.length][f[0].length - 1];
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[0].length; j++)
				t[i][j] = f[i][j];
		}
		f = t;
	}
	public boolean isOrthorhombic() { // 未检验 //判断方阵是否是正交阵
		double item = 0; // 需要支持输入分数和根号，待解决
		for (int j = 0; j < f[0].length; j++) {
			for (int i = 0; i < f.length; i++) {
				item += f[i][j] * f[i][j];
			}
			if (item != 1)
				return false;
		}
		item = 0;
		for (int j = 0; j < f[0].length; j++) {
			for (int j2 = j + 1; j2 < f[0].length; j2++) {
				for (int i = 0; i < f.length; i++)
					item += f[i][j] * f[i][j2];
				if (item != 0)
					return false;
			}
		}
		return true;
	}
	/*
	 * B能被A线性表示的充要条件是R(A)=R(A,B)。所以先求A的秩，再新建f矩阵为A与B合在一起的矩阵，再求
	 * f矩阵的秩，判断是否相等即可。但中间注意矩阵转化是不能直接用赋值号，需用循环语句一个一个赋值
	 */
	public boolean canRepresent(Matrix r) { // 判断向量组B能否由向量组A线性表示
		int rank_A = getRank(); // 会改变f的值，所以不能放在后面
		f = new double[b.length][b[0].length + r.b[0].length];
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (j < b[0].length)
					f[i][j] = b[i][j];
				else
					f[i][j] = r.b[i][j - b[0].length];
			}
		}
		Matrix c = new Matrix();
		double jz[][] = new double[f.length][f[0].length];
		for (int i = 0; i < jz.length; i++) {
			for (int j = 0; j < jz[0].length; j++)
				jz[i][j] = f[i][j];
		}
		c.b = jz;
		int rank_B = c.getRank();
		if (rank_A != rank_B)
			return false;
		else
			return true;
	}
	public boolean isEqual(Matrix r) { // 判断向量组A与向量组B是否等价
		if (canRepresent(r) && r.canRepresent(this))
			return true;
		else
			return false;
	}
	/* 是否线性相关的充要条件是向量组的秩小于列数 */
	public boolean isRelevant() { // 判断向量组是否线性相关
		int rank = getRank();
		if (rank < b[0].length)
			return true;
		else
			return false;
	}
	public void maxIrrelevant() { // 求向量组的最大无关组
		int rank = getRank();
		int c[] = new int[f[0].length];
		for (int i = 0; i < c.length; i++)
			c[i] = 0;
		for (int i = 0; i < f.length; i++) { // 确定哪些列要
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					c[j] = 1;
					break;
				}
			}
		}
		f = new double[b.length][rank];
		int count = 0;
		for (int j = 0; j < b[0].length; j++) { // 抽取需要的列储存进f数组
			if (c[j] == 0)
				continue;
			for (int i = 0; i < b.length; i++) {
				f[i][count] = b[i][j];
			}
			count++;
		}
	}

	private double[][] copyArray(double[][] arr) {
		double[][] ret;
		ret = new double[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; ++i)
			for (int j = 0; j < arr[0].length; ++j)
				ret[i][j] = arr[i][j];
		return ret;
	}
}