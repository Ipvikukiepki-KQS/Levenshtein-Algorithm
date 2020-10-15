/*
 * References
 * https://en.wikipedia.org/wiki/Levenshtein_distance
 * https://gist.github.com/astromechza/7048328
 * https://www.tutorialspoint.com/java/index.htm
 * David J. Eck, "Introduction to Programming Using Java," Hobart and William Smith Colleges, Version 5.0, December 2006
 * (Version 5.0.2, with minor corrections, November 2007)
 * Youtube Tutorials for a Quick Overview 
 * https://www.youtube.com/watch?v=We3YDTzNXEk&ab_channel=TusharRoy-CodingMadeSimple
 * https://www.youtube.com/watch?v=MiqoA-yF-0M&ab_channel=BackToBackSWE
 */
public class SimilarityMetric {
	
	String token1;
	String token2;
	/* 
	 * "Levenshtein Edit Distance calculation with an Optimization by
	 * allocation of one column at a time;"
	 * token1 is the Source String;
	 * token2 is the Target String;
	 * m,n represents the length of the Source and Target
	 * strings respectively;
	 * c0 represents the edit distances for previous column;
	 * c1 represents the allocation of edit distance for the 
	 * current column;
	 * Edit Distance - a parameterizable metric that has calculated 
	 * with specific set of edit operations;
	 * Uncomment the '//' to view the Print Statements for convenience of understanding;
	 */
	private int levenshtein(String token1, String token2, boolean caseSensitive) {
		System.out.printf("String1:%s, String2:%s",token1,token2);
		if(caseSensitive == false) {
			token1 = token1.toLowerCase();
			token2 = token2.toLowerCase();
		}
		String[] t1 = token1.split("");
		String[] t2 = token2.split("");
		
		/*checks whether the string of source is greater than target, or else swap for
		 *computation of Edit Distance Matrix elements with allocation of a single
		 *column at a time for optimization
		 */
		int m = token1.length();
		int n = token2.length();
		
		System.out.println("\nThe Length of token1:"+ m);
		System.out.println("\nThe Length of token2:"+ n);
		int[] c0 = new int[n+1];
		int[] c1 = new int[n+1];
		
		// Initialization of the distances of the previous column
		for (int j=0; j<n+1; j++) {
			c0[j] = j;
			//System.out.println("\nThe Previous Column distances:"+ c0[j]);
		}
		
		for (int i=0; i<m; i++) {
			c1[0] = i+1;
			for (int j=0; j<n; j++) {
				// Calculation of the costs for A[i+1][j+1]
				
				int cost_del = c0[j+1]+1;
				int cost_ins = c1[j]+1;
				int cost_subs;
				//System.out.printf("\nS2c t1:%s, S2c t2:%s",t1[i],t2[j]);
				//System.out.printf("\n Column:%s, Row:%s",i,j);
				if (t1[i].equals(t2[j])) {
					cost_subs = c0[j];
				}else {
					cost_subs = c0[j]+1;
				}
				//System.out.println("\nDeletion Cost: "+cost_del);
				//System.out.println("\nInsertion Cost: "+cost_ins);
				//System.out.println("\nSubstitution Cost: "+cost_subs);
				c1[j+1] = Math.min(cost_del, Math.min(cost_ins, cost_subs));
				
				//System.out.println("\n Edit Distance first column:"+ c1[j+1]);
			}
			for (int k=0; k<=n; k++) {
			//System.out.println("\n reinitialize previous column:"+ c1[k]);
			c0[k] = c1[k];
			//System.out.println("\n reinitialized previous column:"+ c0[k]);
			}
		}		
		return c0[n];
	}
	
	private int levenshtein_maxDist(String token1, String token2, boolean caseSensitive, int maxDist) {
		System.out.printf("\nString1:%s, String2:%s",token1,token2);
		if(caseSensitive == false) {
			token1 = token1.toLowerCase();
			token2 = token2.toLowerCase();
		}
				
		/*checks whether the string of source is greater than target, or else swap for
		 *computation of Edit Distance Matrix elements with allocation of a single
		 *column at a time for optimization
		 */
		if (token1.length()> token2.length()) {
			token1 = token2;
			token2 = token1;
		}
		
		int m = token1.length();
		int n = token2.length();
		
		String[] t1 = token1.split("");
		String[] t2 = token2.split("");
		//System.out.println("\nThe Length of token1:"+ m);
		//System.out.println("\nThe Length of token2:"+ n);
		int[] c0 = new int[n+1];
		int[] c1 = new int[n+1];
		
		// Initialization of the distances of the previous column
		for (int j=0; j<n+1; j++) {
			c0[j] = j;
			//System.out.println("\nThe Previous Column distances:"+ c0[j]);
		}
		
		for (int i=0; i<m; i++) {
			c1[0] = i+1;
			for (int j=0; j<n; j++) {
				// Calculation of the costs for A[i+1][j+1]
				/*for (int k=0; k<m; k++) {
				    System.out.println("\n Edit Distance previous column:"+ c0[k]);
				}*/
				int cost_del = c0[j+1]+1;
				int cost_ins = c1[j]+1;
				int cost_subs;
				/*
				 * Substitution : If the character in the Source and Target are equal, then
				 * the Matrix diagonal value of the previous column has taken into account 
				 * for calculating the edit distance
				 */
				//System.out.printf("\nS2c t1:%s, S2c t2:%s",t1[i],t2[j]);
				//System.out.printf("\n Column:%s, Row:%s",j,i);
				if (t1[i].equals(t2[j])) {
					cost_subs = c0[j];
				}else {
					cost_subs = c0[j]+1;
				}
				c1[j+1] = Math.min(cost_del, Math.min(cost_ins, cost_subs));
				if (c1[j+1] >= maxDist) {
					break;
				}
					
				//System.out.println("\n Edit Distance first column:"+ c1[i+1]);
			}
			for (int k=0; k<=n; k++) {
			    //System.out.println("\n Edit Distance complete column:"+ c1[i+1]);
				c0[k] = c1[k];
				//System.out.println("\n Edit Distance previous column:"+ c0[i]);
			}
		}
		//System.out.println("\n Levenshtein Edit distance:"+ c0[m-1]);		
		return maxDist+1;
	}
	
	private float evalPerformance(float levenshteinDist, float reference) {
		/*
		 * Character Error Rate =  (Substitutions + Deletions + Insertions)/Number of Characters in the Reference [N]
		 * N - Substitutions + Deletions + Number of correct Characters
		 */
		float charerrorrate = levenshteinDist / reference;
		float wordaccuracy = (1 - charerrorrate) * 100;
		return wordaccuracy;
	}

	public static void main(String[] args) {
		
		SimilarityMetric simmet = new SimilarityMetric();
		boolean caseSensitive = true;
		int maxDist = 1;
		String string1 = "Haus";
		String string2 = "Maus";
		String string3 = "Mausi";
		String string4 = "Häuser";
		String string5 = "Kartoffelsalat";
		String string6 = "Runkelrüben";
		String string7 = "Muas,";
		String string8 = "Mausi,";
		String string9 = "Häuser,";
		String string10 = "Runkelrüben,";
		
		//String str4 = string4.replace("Häuser", "Hyuser");
		
		//Results of calculated Levenshtein Distance for each sample
		int sample1 = simmet.levenshtein(string1, string2, caseSensitive);
		int sample2 = simmet.levenshtein(string1, string3, caseSensitive);
		int sample3 = simmet.levenshtein(string1, string4, caseSensitive);
		int sample4 = simmet.levenshtein(string5, string6, caseSensitive);
		int sample1_maxDist = simmet.levenshtein_maxDist(string1, string7, caseSensitive, maxDist);
		int sample2_maxDist = simmet.levenshtein_maxDist(string1, string8, caseSensitive, maxDist);
		int sample3_maxDist = simmet.levenshtein_maxDist(string1, string9, caseSensitive, maxDist);
		int sample4_maxDist = simmet.levenshtein_maxDist(string5, string10, caseSensitive, maxDist);
		System.out.println("\nLevenshtein Distance without maxDist:"+ sample1);
		System.out.println("\nLevenshtein Distance without maxDist:"+ sample2);
		System.out.println("\nLevenshtein Distance without maxDist:"+ sample3);
		System.out.println("\nLevenshtein Distance without maxDist:"+ sample4);
		System.out.println("\nLevenshtein Distance with maxDist:"+ sample1_maxDist);
		System.out.println("\nLevenshtein Distance with maxDist:"+ sample2_maxDist);
		System.out.println("\nLevenshtein Distance with maxDist:"+ sample3_maxDist);
		System.out.println("\nLevenshtein Distance with maxDist:"+ sample4_maxDist);
		
		//Performance Results for calculated Levenshtein Distance of each sample
		float performance1 = simmet.evalPerformance(sample1, string1.length());
		float performance2 = simmet.evalPerformance(sample2, string1.length());
		float performance3 = simmet.evalPerformance(sample3, string1.length());
		float performance4 = simmet.evalPerformance(sample4, string5.length());
		float performance1_maxDist = simmet.evalPerformance(sample1_maxDist, string1.length());
		float performance2_maxDist = simmet.evalPerformance(sample2_maxDist, string1.length());
		float performance3_maxDist = simmet.evalPerformance(sample3_maxDist, string1.length());
		float performance4_maxDist = simmet.evalPerformance(sample4_maxDist, string5.length());
		System.out.println("\nPerformance of Sample1 (without maxDist):"+ performance1);
		System.out.println("\nPerformance of Sample2 (without maxDist):"+ performance2);
		System.out.println("\nPerformance of Sample3 (without maxDist):"+ performance3);
		System.out.println("\nPerformance of Sample4 (without maxDist):"+ performance4);
		System.out.println("\nPerformance of Sample1_maxDist (with maxDist):"+ performance1_maxDist);
		System.out.println("\nPerformance of Sample2_maxDist (with maxDist):"+ performance2_maxDist);
		System.out.println("\nPerformance of Sample3_maxDist (with maxDist):"+ performance3_maxDist);
		System.out.println("\nPerformance of Sample4_maxDist (with maxDist):"+ performance4_maxDist);
	}

}