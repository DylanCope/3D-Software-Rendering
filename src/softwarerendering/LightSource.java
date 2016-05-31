package softwarerendering;

import softwarerendering.maths.Vector;

public class LightSource 
{
	private Vector position;
	private float sourceIntensity;
	
	public interface IntensityFormula
	{
		public float calculate(float x);
	}
	
	private IntensityFormula formula;

	public LightSource(float intensity)
	{
		sourceIntensity = intensity;
		formula = new IntensityFormula()
		{
			@Override
			public float calculate(float x)
			{
				float f = 1 / (1 + x);
				return f * f;
			}
		};
	}
	
	public LightSource(float intensity, IntensityFormula formula)
	{
		sourceIntensity = intensity;
		this.formula = formula;
	}
	
	public void setSourceIntensity(float intensity)
	{
		sourceIntensity = intensity;
	}
	
	public float getSourceIntensity()
	{
		return sourceIntensity;
	}
	
	public void setFormula(IntensityFormula formula)
	{
		this.formula = formula;
	}
	
	public float intensityAt(Vector position)
	{
		float x = this.position.distanceTo(position);
		if (formula != null)
			return formula.calculate(x);
		return sourceIntensity;
	}
	
}
