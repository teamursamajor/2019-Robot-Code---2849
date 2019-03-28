package frc.minimap;

// 650.22 +- 3; 319.72 +- 3

public interface FieldMeasurements {
	double width = 650.22;//inches +- 3
	double height = 319.72;;//inches +-3
	
	double area = height*width;
	
	/**
	 * X/Y Coord of
	 */
	double[] platformBL = {0,0};
	double[] platformBM = {0,0};
	double[] platformBR = {0,0};
	double[] platformTL = {0,0};
	double[] platformTR = {0,0};
	//Points
}
