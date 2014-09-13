package graph;

public class Interval {
	private double min;
	private double max;
	private String name;
	
	public Interval(double min, double max){
		if (max <= min) 
			throw new IllegalArgumentException();
		this.min = min;
		this.max = max;
		this.name = String.valueOf(min)+"&"+String.valueOf(max);
	}
	
	public Interval(double min, double max, String name){
		if (max <= min) 
			throw new IllegalArgumentException();
		this.min = min;
		this.max = max;
		this.name = name;
	}

	public boolean contains(double x){
		return x > min && x < max;
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public void setMax(double max) {
		this.max = max;
	}
	
	public double min() {		
		return min;
	}

	public double max() {
		return max;
	}
	
	public String name() {
		return this.name;
	}

	public boolean intersect(Interval i){
		return !(max < i.min || min > i.max);
	}
}
