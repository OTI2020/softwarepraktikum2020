package org.lakedetection;

public class SeeErkennen {

	
	
	/* || Bilder �bereinanderlegen ||
	 * 
	 * Es liegen 2 2D-Arrays vor. 
	 * Nun sollen beide Arrays durchlaufen werden 
	 * und die Werte an den jeweils gleichen Pos. miteinander addiert 
	 * und durch 2 dividiert werden. 
	 * */

	public static float[][] connect(float[][] b1, float[][] b2){
		
		float[][] a = new float[b1.length][b1[0].length];
		
		for(int i=0; i<b1.length; i++) {
			for(int j=0; j<b1[i].length; j++) { 
				a[i][j] = (b1[i][j] + b2[i][j]) / 2;
			}
		} 
		return a;
	}
	
	// Hilfsfkt. zum Anzeigen einer Matrix 6 x 5
	public static void show(float[][] a) {
		for(int i=0; i<6; i++) {
			System.out.print(a[i][0] + " ");
		}
		System.out.println();
		for(int i=0; i<6; i++) {
			System.out.print(a[i][1] + " ");
		}
		System.out.println();

		for(int i=0; i<6; i++) {
			System.out.print(a[i][2] + " ");
		}
		System.out.println();

		for(int i=0; i<6; i++) {
			System.out.print(a[i][3] + " ");
		}		
		System.out.println();
		for(int i=0; i<6; i++) {
			System.out.print(a[i][4] + " ");
		}
		System.out.println();
	

	}
	
	public static void showEff(float[][] a) {
		
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[0].length; j++) {
				System.out.print(a[i][j] + " ");
			}
		}
		
	}
	
	public static void showArray(float[][] a) {
		int counter1 = 0;
		int counter2 = 0;
		for(int i=0; i<a.length; i++) {
			counter1++;
			for(int j=0; j<a[i].length; j++) {
				System.out.print(a[i][j]);
				counter2++;
			}
		}
		System.out.println(counter1);
		System.out.println(counter2);
		System.out.println("");
		System.out.println("a.length: " + a.length);
		System.out.println("a[0].length: " + a[0].length);
	}
	
	
	///////////////////////
	/* || Wasserfl�chen erkennen ||
	 * 
	 * Mit der Schwellwertfunktion arbeiten 
	 * und alle Wasserfl�chen schwarz einf�rben.
	 * */
	
	public static float[][] makeBlack(float[][] a) {
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
	
	/* Fkt. die die Fl�che gl�ttet -- Test war erfolgreich
	 * */
	public static float[][] smoothing(float[][] a){
		float[][] b = new float[a.length][a[0].length];
		
		for(int i=1; i<(a.length)-1; i++) {
			for(int j=1; j<(a[i].length)-1; j++) {
				float x = ( a[i-1][j-1] + a[i-1][j] + a[i-1][j+1] + a[i][j-1] + a[i][j] + a[i][j+1] + a[i+1][j-1] + a[i+1][j] + a[i+1][j+1]) / 9;
				b[i][j] = x; 
			}
		}
		return b;
	}
}
