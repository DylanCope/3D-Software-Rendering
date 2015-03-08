package softwarerendering.maths;

public class Matrix {
	
	private float[][] values;
	private int width, height;
	private boolean isNull;
	
	public Matrix(int width, int height)
	{
		values = new float[width][height];
		this.width = width;
		this.height = height;
	}
	
	public Matrix(int size)
	{
		width = size;
		height = size;
		values = new float[size][size];
	}
	
	public Matrix(int width, int height, float[][] values)
	{
		this.width = width;
		this.height = height;
		this.values = new float[width][height];
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				this.values[i][j] = values[i][j];
		
	}
	
	public static Matrix get2DRotationalMatrix(double theta)
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
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				set(i, j, value);
	}
	
	public float getTrace()
	{
		float trace = 0;
		
		if (width != height)
			return 0;
		
		for (int i = 0; i < width; i++)
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
		
		if (width > 2) {
			float[][] values = new float[width][width];
			int sign = 0;
			int k = 0;
			
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					if (k % 2 == 0)
						sign = 1;
					else
						sign = -1;

					values[i][j] = sign * getSubMatrix(i, j).getDeterminant();
					k++;

				}
				if (width % 2 == 0)
					k++;
			}
			
			inverse = new Matrix(width, width, values).getTranspose().div(getDeterminant());
			
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
		float[][] values = new float[height][width];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)
				values[j][i] = this.values[i][j];
		}
		
		return new Matrix(height, width, values);
	}
	
	public float getDeterminant()
	{
		float det = 0;
			
		if(width > 2 && width == height){
			
			for(int i = 0; i < width; i++){
				
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
		float[][] values = new float[width - 1][width - 1];
		int w = 0, h = 0;
		
		for(int a = 0; a < width; a++){
			if (a != i){
				for(int b = 0; b < height; b++){
					if (b != j) {
						values[w][h] = this.values[a][b];
						h++;
					}
				}
				h = 0;
				w++;
			}
		}
		
		return new Matrix(width - 1, width - 1, values);
	}
	
	public Matrix add(Matrix b)
	{
		Matrix result = new Matrix(width, height);
		
		if(width == b.getWidth() && height == b.getHeight()){
			
			float[][] values = new float[width][height];
			
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
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
		Matrix result = new Matrix(b.getWidth(), height);
		
		if(width == b.getHeight()){

			float[][] values = new float[b.getWidth()][height];
			
			for (int w = 0; w < b.getWidth(); w++){
				for(int h = 0; h < height; h++){
					
					float newVal = 0;
					
					for(int i = 0; i < width; i++){
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
	
	public Matrix add(float k)
	{
			
		float[][] values = new float[width][height];
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				values[i][j] = get(i, j) + k;
			}
		}
		
		return new Matrix(width, height, values);
	}
	
	public Matrix mul(float k)
	{
		
		float[][] values = new float[width][height];
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				values[i][j] = get(i, j) * k;
			}
		}
		
		return new Matrix(width, height, values);
	}	
	
	public Matrix div(float k)
	{
		
		float[][] values = new float[width][height];
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				values[i][j] = get(i, j) / k;
			}
		}
		
		return new Matrix(width, height, values);
	}
	
	public String toString()
	{
		
		String text = "";
		
		if (isNull)
			return "This is a null matrix";
		
		for (int i = 0; i < height; i++){
			text += "(\t";
			for (int j = 0; j < width; j++){
				text += Math.round(values[j][i] * 100000)/100000f + "\t";
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
		return width;
	}

	public int getHeight() 
	{
		return height;
	}
	
}