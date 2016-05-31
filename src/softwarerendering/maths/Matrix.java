package softwarerendering.maths;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class Matrix {
	
	private float[][] values;
	private int rows, cols;
	private boolean isNull;
	
	public static void main(String[] args)
	{
		Set<Vector> basis = new HashSet<Vector>();
		basis.add(new Vector(1, 1, 1));
		basis.add(new Vector(1, 0, 1));
		
		Vector v = new Vector(1, 1, 4);
		Vector p = projectVectorToSubSpace(v, basis);
		System.out.println(p);
		
		float r3o3 = (float) Math.sqrt(3) / 3f;
		float r6o6 = (float) Math.sqrt(6) / 6f;
		
		Set<Vector> basis2 = new HashSet<Vector>();
		Vector u1 = new Vector(r3o3, -r3o3, r3o3);
		Vector v1 = new Vector(-r3o3, r6o6, 2*r6o6);
		
		basis2.add(u1);
		basis2.add(v1);
		Vector w = new Vector(3, -3, 0);
		
		Vector pw = projectVectorToSubSpace(w, basis2);
		System.out.println(pw);
		System.out.println(w.sub(pw).dotProduct(u1));
	}
	
	public static Vector projectVectorToSubSpace(Vector v, Set<Vector> basisSet)
	{	
		Matrix M = matrixFromBasisSet(basisSet);
		Matrix Mt = M.getTranspose();
		
		Matrix P = M.mul(Mt.mul(M).getInverse()).mul(Mt).mul(v);
		// This applies Proj_V(v) = M[(Mt.M)^-1]Mt.v
		
		Vector p = new Vector(P.getValues()[0]);
		
		return p;
	}
	
	public static Matrix matrixFromBasisSet(Set<Vector> basisSet)
	{
		int n = 0;
		for (Vector b : basisSet)
		{
			n = b.getDimension();
			break;
		}
		
		int k = basisSet.size();
		float[][] vals = new float[k][n];
		
		int i = 0;
		for (Vector b : basisSet)
		{
			vals[i] = b.getValues();
			i++;
		}
		
		Matrix M = new Matrix(k, n, vals);
		return M;
	}
	
	public Matrix(int rows, int cols)
	{
		values = new float[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}
	
	public Matrix(int size)
	{
		rows = size;
		cols = size;
		values = new float[size][size];
	}
	
	public Matrix(int rows, int cols, float[][] values)
	{
		this.rows = rows;
		this.cols = cols;
		this.values = new float[rows][cols];
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				this.values[i][j] = values[i][j];
			
		
	}
	
	public static Matrix initRotationalMatrix2d(double theta)
	{
		Matrix matrix = new Matrix(2);
		matrix.set(0, 0,  (float) Math.cos(theta));
		matrix.set(1, 0,  (float) Math.sin(theta));
		matrix.set(0, 1, -(float) Math.sin(theta));
		matrix.set(1, 1,  (float) Math.cos(theta));
		
		return matrix;
	}
	
	public float get(int i, int j)
	{
		return values[i][j];
	}
	
	public void set(int i, int j, float value)
	{
		values[i][j] = value;
	}
	
	public void fill(float value)
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				set(i, j, value);
	}
	
	public float getTrace()
	{
		float trace = 0;
		
		if (rows != cols)
			return 0;
		
		for (int i = 0; i < rows; i++)
			trace += values[i][i];
		
		return trace;
	}
	
//	public float[] getCharacteristicCoefs()
//	{
//		float[] coefs = new float[width];
//		
//		if (width != height)
//			return coefs;
//		
//		Matrix sub = this;
//		
//		for (int i = 0; i < width; i++) {
//			sub = sub.getSubMatrix(0, 0);
//			coefs[i] = (float) Math.pow(-1, i) * sub.getTrace(); 
//		}
//		
//		return coefs;
//	}
	
	public static Matrix getIndentity(int size)
	{
		
		float[][] values = new float[size][size];
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(i == j){
					values[i][j] = 1;
				}else{
					values[i][j] = 0;
				}
			}
		}
		return new Matrix(size, size, values);
	}
	
	public void setNull(boolean isNull)
	{	
		this.isNull = isNull;
	}
	
	public Matrix getInverse()
	{
		Matrix inverse;
		
		if (rows > 2) {
			float[][] values = new float[rows][rows];
			int sign = 0;
			int k = 0;
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < rows; j++) {
					if (k % 2 == 0)
						sign = 1;
					else
						sign = -1;

					values[i][j] = sign * getSubMatrix(i, j).getDeterminant();
					k++;

				}
				if (rows % 2 == 0)
					k++;
			}
			
			inverse = new Matrix(rows, rows, values).getTranspose().div(getDeterminant());
			
		} 
		else 
		{
			inverse = div(getDeterminant());
			float[][] flip = new float[2][2];
			flip[1][0] = -inverse.getValues()[1][0];
			flip[0][1] = -inverse.getValues()[0][1];
			flip[0][0] = inverse.getValues()[1][1];
			flip[1][1] = inverse.getValues()[0][0];
			
			inverse = new Matrix(2, 2, flip);
		
		}
		return inverse;
	}
	
	public Matrix getTranspose()
	{
		float[][] values = new float[cols][rows];
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++)
				values[j][i] = this.values[i][j];
		}
		
		return new Matrix(cols, rows, values);
	}
	
	public float getDeterminant()
	{
		float det = 0;
			
		if(rows > 2 && rows == cols){
			
			for(int i = 0; i < rows; i++){
				
				int sign;
				if(i % 2 == 0) sign = 1;
				else sign = -1;
				
				det += sign * get(i, 0) * getSubMatrix(i, 0).getDeterminant();
			}
		} 
		else {
			det = get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
		}
		return det;
	}
	
	public Matrix getSubMatrix(int i, int j)
	{
		float[][] values = new float[rows - 1][rows - 1];
		int w = 0, h = 0;
		
		for(int a = 0; a < rows; a++){
			if (a != i){
				for(int b = 0; b < cols; b++){
					if (b != j) {
						values[w][h] = this.values[a][b];
						h++;
					}
				}
				h = 0;
				w++;
			}
		}
		
		return new Matrix(rows - 1, rows - 1, values);
	}
	
	public Vector transform(Vector v)
	{
		Vector transformedVector = new Vector(v.getValues().length);
		return transformedVector;
	}
	
	public Matrix add(Matrix b)
	{
		Matrix result = new Matrix(rows, cols);
		
		if(rows == b.getWidth() && cols == b.getHeight()){
			
			float[][] values = new float[rows][cols];
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					values[i][j] = get(i, j) + b.get(i, j);
				}
			}
			
			result.setValues(values);
		}else{
			result.setNull(true);
		}
		
		return result;
	}

	public Matrix mul(Matrix b)
	{
		Matrix result = new Matrix(b.getWidth(), cols);
		
		if(rows == b.getHeight()){

			float[][] values = new float[b.getWidth()][cols];
			
			for (int w = 0; w < b.getWidth(); w++){
				for(int h = 0; h < cols; h++){
					
					float newVal = 0;
					
					for(int i = 0; i < rows; i++){
						newVal += get(i, h) * b.get(w, i);
					}
					values[w][h] = newVal;
				}
			}

			result.setValues(values);
		}else{
			result.setNull(true);;
		}
		
		return result;
	}
	
	public Matrix mul(Vector v)
	{
		Matrix m = new Matrix(
				1, v.getValues().length, 
				new float[][] {v.getValues()}
			);
		
		return mul(m);
	}
	
	public Matrix add(float k)
	{
			
		float[][] values = new float[rows][cols];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				values[i][j] = get(i, j) + k;
			}
		}
		
		return new Matrix(rows, cols, values);
	}
	
	public Matrix mul(float k)
	{
		
		float[][] values = new float[rows][cols];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				values[i][j] = get(i, j) * k;
			}
		}
		
		return new Matrix(rows, cols, values);
	}	
	
	public Matrix div(float k)
	{
		
		float[][] values = new float[rows][cols];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				values[i][j] = get(i, j) / k;
			}
		}
		
		return new Matrix(rows, cols, values);
	}
	
	public String toString()
	{
		
		String text = "";
		
		if (isNull)
			return "This is a null matrix";
		
		DecimalFormat format = new DecimalFormat("###.##");
		
		for (int i = 0; i < cols; i++){
			text += "(\t";
			for (int j = 0; j < rows; j++){
//				text += Math.round(values[j][i] * 100000)/100000f + "\t";
				text += format.format(values[j][i]) + "\t";
			}
			text += ")\n";
		}
		return text;
	}
	
	public float[][] getValues()
	{
		return values;
	}
	
	public void setValues(float[][] values)
	{
		this.values = values;
	}
	
	public int getWidth() 
	{
		return rows;
	}

	public int getHeight() 
	{
		return cols;
	}
	
}