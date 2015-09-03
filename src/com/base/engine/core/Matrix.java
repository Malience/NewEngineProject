package com.base.engine.core;


public class Matrix {
	private float[][] m;
	private int size;
	private int rows;
	private int cols;
	
	public Matrix()
	{
		m = new float[4][4];
		//setIdentity();
		size = calcSize();
	}
	
	public Matrix(int size)
	{
		m = new float[size][size];
		this.size = calcSize();
	}
	
	public Matrix(int rows, int cols)
	{
		m = new float[rows][cols];
		size = calcSize();
	}
	
	public Matrix(float[] value)
	{
		float[][] f = new float[][]{value};
		m = f;
		size = calcSize();
	}
	
	public Matrix(float[][] values)
	{
		float[][] f = new float[values.length] [values[0].length];
		for(int i = 0; i < values.length; i++)
		{
			for(int j = 0; j < values[0].length; j++)
			{
				f[i][j] = values[i][j];
			}
		}
		m = f;
		size = calcSize();
	}
	
	public Matrix(Vector v)
	{
		float[][] f = new float[][]{v.get()};
		m = f;
		transposeThis();
		size = calcSize();
	}
	
	public Matrix(Vector[] v)
	{
		float[][] f = new float[v.length][v[0].getSize()];
		for(int i = 0; i < v.length; i++)
		{
			f[i] = v[i].get();
		}
		
		m = f;
		transposeThis();
		size = calcSize();
	}
	
	public Matrix(Matrix m)
	{
		float[][] f = new float[m.rows] [m.cols];
		for(int i = 0; i < m.rows; i++)
		{
			for(int j = 0; j < m.cols; j++)
			{
				f[i][j] = m.getValue(i,j);
			}
		}
		this.m = f;
		size = calcSize();
	}
	
	public Matrix setIdentity()
	{
		if(!isSquare())
			return null;
		set(new float[][]{
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
		});
		
		return this;
	}
	
	public Matrix setTranslation(float x, float y, float z)
	{
		if(!isSquare())
			return null;
		set(new float[][]{
			{1, 0, 0, x},
			{0, 1, 0, y},
			{0, 0, 1, z},
			{0, 0, 0, 1}
		});
		
		return this;
	}
	
	public Matrix setRotation(float x, float y, float z)
	{
		if(!isSquare())
			return null;
		
		Matrix rx = new Matrix(4);
		Matrix ry = new Matrix(4);
		Matrix rz = new Matrix(4);
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		rx.set(new float[][]{
				{1, 			0, 			0, 			0},
				{0, 			(float)Math.cos(x), 			-(float)Math.sin(x), 			0},
				{0, 			(float)Math.sin(x), 			(float)Math.cos(x), 			0},
				{0, 			0, 			0, 			1}
		});
		
		ry.set(new float[][]{
				{(float)Math.cos(y),			 	0, 			-(float)Math.sin(y), 			0},
				{0, 			1, 			0, 			0},
				{(float)Math.sin(y), 			0, 				(float)Math.cos(y), 			0},
				{0, 			0, 			0, 			1}
			});
		
		rz.set(new float[][]{
				{(float)Math.cos(z), 	-(float)Math.sin(z), 			0, 			0},
				{(float)Math.sin(z), 	(float)Math.cos(z), 			0, 			0},
				{0, 			0, 			1, 			0},
				{0, 			0, 			0, 			1}
			});
		
		
		
		this.set(rz.mul(ry.mul(rx)).get());
		return this;
	}
	
	public Matrix setScale(float x, float y, float z)
	{
		if(!isSquare())
			return null;
		set(new float[][]{
			{x, 0, 0, 0},
			{0, y, 0, 0},
			{0, 0, z, 0},
			{0, 0, 0, 1}
		});
		
		return this;
	}
	
	public Matrix setPerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		set(new float[][]{
				{1.0f / (tanHalfFOV * aspectRatio), 	0,								0, 0},
				{0, 						1.0f / tanHalfFOV, 				0, 0},
				{0, 						0, (-zNear - zFar)/zRange, 		2 * zFar * zNear / zRange},
				{0, 						0, 								1, 0}
			});
		
		return this;
	}
	
	public Matrix setOrthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;
		
		if(!isSquare())
			return null;
		set(new float[][]{
			{2.0f/width, 		0, 				0, 			-(right + left)/width},
			{0, 			2.0f/height, 		0, 			-(top + bottom)/height},
			{0, 			0, 				-2.0f/depth, 	-(far - near)/depth},
			{0, 			0, 				0, 			1}
		});
		
		return this;
	}
	
	public Matrix setRotation(Vector forward, Vector up)
	{
		Vector f = forward.normal();
		
		Vector r = up.normal().cross(f);
		
		Vector u = f.cross(r);
		
		return setRotation(f, u, r);
	}
	
	public Matrix setRotation(Vector forward, Vector up, Vector right)
	{
		Vector f = forward;
		
		Vector r = right;
		
		Vector u = up;
		
		set(new float[][]{
				{r.getValue(0), r.getValue(1), r.getValue(2), 0},
				{u.getValue(0), u.getValue(1), u.getValue(2), 0},
				{f.getValue(0), f.getValue(1), f.getValue(2), 0},
				{0, 0, 0, 1}
			});
		
		return this;
	}
	
	public Vector transform(Vector v)
	{
		float[] f = new float[v.size];
		for(int i = 0; i < v.size; i++)
		{
			for(int j = 0; j < v.size; j++)
			{
				f[i] += v.getValue(j) * this.getValue(i, j);
			}
			f[i] += this.getValue(i, cols - 1);
		}
		return new Vector(f);
	}
	
	public Matrix transpose()
	{
		float[][] f = new float[cols] [rows];
		for(int i = 0; i < cols; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				f[i][j] = getValue(j,i);
			}
		}
		
		return new Matrix(f);
	}
	
	public void transposeThis()
	{
		float[][] f = new float[cols] [rows];
		for(int i = 0; i < cols; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				f[i][j] = getValue(j,i);
			}
		}
		
		m = f;
	}
	
	public Matrix mul(Matrix m)
	{
		if(cols != m.rows)
			return null;
		
		float[][] f = new float[this.rows][m.cols];
		Vector[] m1 = this.breakIntoRowVectors();
		Vector[] m2 = m.breakIntoColVectors();
		
		for(int i = 0; i < cols; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				f[j][i] = m1[j].dot(m2[i]);
			}
		}
		
		return new Matrix(f);
	}
	
	
	public void set(float[][] values)
	{
		float[][] f = new float[values.length] [values[0].length];
		for(int i = 0; i < values.length; i++)
		{
			for(int j = 0; j < values[0].length; j++)
			{
				f[i][j] = values[i][j];
			}
		}
		m = f;
		size = calcSize();
	}
	
	public Vector[] breakIntoRowVectors()
	{
		Vector[] ans = new Vector[rows];
		for(int i = 0; i < rows; i++)
		{
			ans[i] = getRowVector(i);
		}
		
		return ans;
	}
	
	public Vector[] breakIntoColVectors()
	{
		Vector[] ans = new Vector[cols];
		for(int i = 0; i < cols; i++)
		{
			ans[i] = getColVector(i);
		}
		
		return ans;
	}
	
	public Vector getRowVector(int row)
	{
		float[] f = new float[rows];
		for(int i = 0; i < rows; i++)
		{
			f[i] = getValue(row, i);
		}
		return new Vector(f);
	}
	
	public Vector getColVector(int col)
	{
		float[] f = new float[cols];
		for(int i = 0; i < cols; i++)
		{
			f[i] = getValue(i, col);
		}
		return new Vector(f);
	}
	
	public float[][] get()
	{
		float[][] f = new float[rows] [cols];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				f[i][j] = getValue(i, j);
			}
		}
		return f;
	}
	
	public float get(int x, int y)
	{
		return m[x][y];
	}
	
	public boolean isSquare()
	{
		return rows == Math.sqrt(rows*cols);
	}
	
	public void setValue(int i, int j, float value)
	{
		m[i][j] = value;
	}
	
	public float getValue(int i, int j)
	{
		return m[i][j];
	}
	
	public int calcSize()
	{
		rows = getRows();
		cols = getCols();
		return rows * cols;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getRows()
	{
		return m.length;
	}
	
	public int getCols()
	{
		return m[0].length;
	}
	
	public void print()
	{
		for(int i = 0; i < m.length; i++)
		{
			String line = new String(i + ": ");
			for(int j = 0; j < m[0].length; j++)
			{
				line += " " + m[i][j];
			}
			System.out.println(line);
		}
		System.out.println();
	}
	
	public boolean equals(Matrix4f mat)
	{
		for(int i = 0; i < m.length; i++)
		{
			for(int j = 0; j < m[0].length; j++)
			{
				if(m[i][j] != mat.get(i, j))
					return false;
			}
		}
		return true;
	}
}
