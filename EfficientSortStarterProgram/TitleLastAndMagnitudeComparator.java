
/**
 * Write a description of TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
    public int compare(QuakeEntry q1, QuakeEntry q2){    
        String title1 = q1.getInfo();
        String[] s = title1.split(" ");
        int length1 = s.length - 1;
        String finalWord1 = s[length1];
        
        String title2 = q2.getInfo();
        String[] s2 = title2.split(" ");
        int length2 = s2.length - 1;
        String finalWord2 = s2[length2];        
        
        if(finalWord1.compareTo(finalWord2) < 0){
            return -1;
        }
        if(finalWord1.compareTo(finalWord2) > 0){
            return 1;
        }        
        if(finalWord1.compareTo(finalWord2) == 0) {
            if(q1.getMagnitude() < q2.getMagnitude()){
                return -1;
            }
            if(q1.getMagnitude() > q2.getMagnitude()){
                return 1;
            }            
        }        

        return 0;
    }
}
