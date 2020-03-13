package org.lakedetection;

import java.util.Arrays;

public class ROPs {

	/* Die Klasse enthält die Rasteroperationen, die verwendet werden, um die Bilder zu bearbeiten. 
	 * 
	 * Zunächst werden die beiden Bänder verknüpft miteinander (connect(a,b)).
	 * 
	 * Daraufhin wird das Bild geglättet, um Störpixel zu eliminieren (smoothing(c)).
	 * 
	 * Zuletzt werden Wasserflächen Schwarz eingefärbt und alles andere Weiß.
	 * HIERBEI MUSS DER SCWELLWERT NOCH ANGEPASST WERDEN!!!
	 * */

	

	
	// Hilfsfkt. zum Anzeigen einer Matrix 15x15
	
	public void show(float[][] a) {

		for (int i=0; i<a.length; i++)
		{
			System.out.print("[");
			for (int j=0; j<a[i].length; j++)
			{
				System.out.print(a[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}
	

	
	// Fkt. die die Fl�che gl�ttet -- Test war erfolgreich
	 
	public float[][] smoothing(float[][] a){
		float[][] b = new float[a.length][a[0].length];
		
		for(int i=1; i<(a.length)-1; i++) {
			for(int j=1; j<(a[i].length)-1; j++) {
				float x = ( a[i-1][j-1] + a[i-1][j] + a[i-1][j+1] + a[i][j-1] + a[i][j] + a[i][j+1] + a[i+1][j-1] + a[i+1][j] + a[i+1][j+1]) / 9;
				b[i][j] = x; 
			}
		}
		return b;
	}
	

	
	///////////////////////
	/* || Wasserfl�chen erkennen ||
	 * 
	 * Mit der Schwellwertfunktion arbeiten 
	 * und alle Wasserfl�chen schwarz einf�rben.
	 * */
	
	public float[][] makeBlack(float[][] a) {

		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[i].length; j++) {
				if(a[i][j] >= 150) { // Schwellwert muss noch gesetzt werden
					a[i][j] = 0;
				}
				else a[i][j] = 255; // max. ist mittelwert der maxima von vh und vv (4142,5) __ jetzt doch ge�ndert auf 255
			}
		}
		return a;
	}
	
	// Test mithilfe einer beliebigen 5x5-Matrix war erfolgreich
	
	
	 /**
	  * Function takes an image and filtering it with an array
	  * @param img float[][]
	  * @return float[][] Input image filterd
	  */
	public float[][] medianFilter(float[][] img){
		
		
		float[][] newimg = new float[img.length][img[0].length];
		
		
		System.out.println(Arrays.deepToString(img));
		for(int i = 1; i < img.length - 1; i++) {
			for(int j = 1; j < img[i].length - 1; j++) {
				float[] medianarray = {
						img[i - 1][j - 1], img[i - 1][j], img[i - 1][j + 1], 
						img[i][j - 1], img[i][j], img[i][j + 1],
						img[i + 1][j - 1], img[i + 1][j], img[i + 1][j + 1]};
				
				
				Arrays.sort(medianarray);
				float median = medianarray[4];
				
				/*
				 System.out.println(
						" Feld i:  " + i + ", Feld j: " + j + 
						" , Median Array: " + Arrays.toString(medianarray) +
						" , Median: " + median);
				*/
				newimg[i][j] = median;
				//System.out.println(Arrays.deepToString(newimg));
			}
		}
		
		return newimg;
	}
	
	/**
	 * Adding Original rim Pixel data to a new 2d array.
	 * The Image will keep it size and the inner pixel might be used
	 * for a filter, rim Pixels wont be edited in all of our filter
	 * @param img original image
	 * @param rim size of the filter 
	 * @return 2darray with edges filled with original image Pixelvalues
	 */
	public float[][] rimFiller(float[][] img, int rim){
		float[][] newimg = new float[img.length][img[1].length];
		for(int i = 0; i < img.length; i++) {
			if(i < 0 + rim || i > img.length - 1 - rim) {
				for(int j = 0; j < img[i].length; j++) {
					newimg[i][j] = img[i][j];
				}
			} else {
				for(int j = 0; j < rim; j++) {
					newimg[i][j] = img[i][j];	
					newimg[i][newimg[i].length - 1 - j] = img[i][img[i].length - 1 - j ];
				}
				newimg[i][0] = img[i][0];
				newimg[i][newimg[i].length - 1] = img[i][img[i].length - 1];
			}
		}
		return newimg;
	}
	/* Gauss-Filter mit 7x7-Matrix 
	 * */
	public float[][] gaussFilter(float[][] a){
		
		float[][] b = new float[a.length][a[0].length];
		
		for(int i=3; i<(a.length)-3; i++) {
			for(int j=3; j<(a[i].length)-3; j++) {
				float x = (( (a[i-3][j-3] + a[i-3][j+3] + a[i+3][j-3] + a[i+3][j+3]) 
									* 1)  // Gewicht = 1
						
						+ ( (  	   a[i-3][j-2] + a[i-3][j-1] + a[i-3][j  ] + a[i-3][j+1] + a[i-3][j+2]  +  a[i-2][j-2]  
								+  a[i+3][j-2] + a[i+3][j-1] + a[i+3][j  ] + a[i+3][j+1] + a[i+3][j+2]  +  a[i+2][j+2]  
								+  a[i-2][j-3] + a[i-1][j-3] + a[i  ][j-3] + a[i+1][j-3] + a[i+2][j-3]  +  a[i+2][j-2]  
								+  a[i-2][j+3] + a[i-1][j+3] + a[i  ][j+3] + a[i+1][j+3] + a[i+2][j+3]  +  a[i-2][j+2] ) //
									* 2) // Gewicht = 2
						+ ( (a[i-2][j-1] + a[i-2][j  ] + a[i-2][j+1]  
								+  a[i+2][j-1] + a[i+2][j  ] + a[i+2][j+1]  
								+  a[i-1][j-2] + a[i  ][j-2] + a[i+1][j-2]  
								+  a[i-1][j+2] + a[i  ][j+2] + a[i+1][j+2]) 
									* 3) // Gewicht = 3
						+ ( (a[i-1][j-1] + a[i-1][j  ] + a[i-1][j+1] 
								+ a[i  ][j-1] + a[i  ][j] + a[i  ][j+1] 
								+ a[i+1][j-1] + a[i+1][j] + a[i+1][j+1]) 
									* 5)     // Gewicht = 5
						) / 128;	  
						
				b[i][j] = x; 
			}
		}
		return b;
		
		// Test ergibt, dass der Filter korrekt arbeitet
	
	}
	
	
	
	
}


