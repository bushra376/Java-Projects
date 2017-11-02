import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    public void totalBirths (FileResource fr){
        int totalBirths = 0;
        int totalGirls = 0;
        int totalGirlsNames = 0;
        int totalBoys = 0;
        int totalBoysNames = 0;
        for(CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("M")){
                totalBoys += numBorn;
                totalBoysNames++;
            }else{
                totalGirls += numBorn;
                totalGirlsNames++;
            }            
        }
        System.out.println("Total Births = " + totalBirths);
        System.out.println("Total Girls = " + totalGirls);        
        System.out.println("Total Boys = " + totalBoys);        
        System.out.println("Total Girls Names = " + totalGirlsNames);        
        System.out.println("Total Boys Names = " + totalBoysNames);             
        System.out.println("Total Names in File = " + (totalGirlsNames + totalBoysNames));
        
    }
    
    public void testTotalBirths (){
        FileResource fr = new FileResource();
        totalBirths(fr);        
    }
    
    public int getRank(int year, String name, String gender){
        //String fname = "data/yob" + year + ".csv";
        //String fname = "testing\\yob" + year + "short.csv";       
        int rank = 0;
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(gender.equals("M") ){
                if(rec.get(1).equals("M")) { //IF file has reached Boys 
                    rank++;
                    if (rec.get(0).equals(name)) { //IF file found name
                        return rank;
                    }
                }
            }
            else {
                if(rec.get(1).equals("F")) { //IF file has reached Girls 
                    rank++;
                    if (rec.get(0).equals(name)) { //IF file found name
                        return rank;
                    }
                }
            }                           
        }
        return -1;
    }
    
    public void testGetRank(){
        //int ans = getRank(1971,"Frank" ,"F");
       // System.out.println("Rank is: " + ans);
    }
    
    public String getName (int year, int rank, String gender){
        //String fname = "data/yob" + year + ".csv";        
        //String fname = "testing\\yob" + year + "short.CSV";   
        int count = 0;
        //FileResource fr =  new FileResource("testing\\yob" + year + "short.csv");        
        FileResource fr = new FileResource();        
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(gender.equals("F")){
                if(rec.get(1).equals("F")){ //IF file reached girls                    
                    count++;
                    if (count == rank){
                        return rec.get(0);                    
                    }
                }
            }
            else{
                if(rec.get(1).equals("M")){ //IF file reached boys
                    count++;
                    if (count == rank){
                        return rec.get(0);                    
                    }
                }
            }            
        }
        return "NO NAME";        
    }
    
    public void testGetName(){
        String name = getName(1982,450, "M");
        System.out.println("Name at this Rank is: " + name);
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
       // int rankInYearBorn = getRank(year, name, gender); //getRank(int year, String name, String gender)
       // String newName = getName(newYear, rankInYearBorn, gender);    //getName (int year, int rank, String gender)
       // System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
    }
    
    public void testWhatIsNameInYear(){        
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public int yearOfHighestRank(String name, String gender){
        int yearOfHighestRank = 0;
        int currRank = 0;
        int highestRank = 0;
        String fileName = null;
        int currYear = 0;        
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            fileName = f.getName();
            currYear = Integer.parseInt(fileName.substring(3,7));
            
            currRank = getRank(currYear, name, gender); // Get Rank for currYear
            System.out.println("Current Rank is: " + currRank +" for file: " + fileName);            

            if(highestRank == 0){
                highestRank = currRank;
                yearOfHighestRank = currYear;                
            }
            else{
                if(highestRank > currRank){
                    highestRank = currRank;
                    yearOfHighestRank = currYear;
                    System.out.println("Highest Rank is: " + highestRank + " and Year of highest Rank is: " + yearOfHighestRank);
                }
            }            
        }
        return yearOfHighestRank;
    }
    
    public void testYearOfHighestRank(){
        int year = yearOfHighestRank("Mich","M");
        System.out.println("Year of highest rank is: " + year);
    }
    
    public double getAverageRank(String name, String gender){
        String fileName = null;
        int currYear = 0;
        int currRank = 0;
        double sumRank = 0;
        double count = 0;
        double avg = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            fileName = f.getName();
            currYear = Integer.parseInt(fileName.substring(3,7));
            
            currRank = getRank(currYear, name, gender); // Get Rank for currYear            
            if(currRank != -1){
                count++;
                sumRank += currRank;
            }
        }
        System.out.println("Sum of Ranks is: " + sumRank);
        System.out.println("Sum of count is: " + count);        
        avg = sumRank/count;
        System.out.println("Avg is: " + avg);        
        return avg;
    }
    
    public void testGetAverageRank(){
        double avg = getAverageRank("Robert","M");
        System.out.println("The average rank is: " + avg);                
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int highestRank = getRank(year, name, gender); // Get Rank for currYear   
        int dummyHighestRank = highestRank;
        int sumBirth = 0;
        //FileResource fr = new FileResource("testing\\yob" + year + "short.csv");    
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
            if (gender.equals("M")){
                if(rec.get(1).equals("M")){
                    if(dummyHighestRank !=1){
                        dummyHighestRank--;
                        sumBirth += Integer.parseInt(rec.get(2));
                    }
                }
            }
            else{
                if(rec.get(1).equals("F")){
                    if(dummyHighestRank !=1){
                        dummyHighestRank--;
                        sumBirth += Integer.parseInt(rec.get(2));                        
                    }
                }                
            }
        }
        return sumBirth;
    }
    
    public void testgetTotalBirthsRankedHigher(){
        int births = getTotalBirthsRankedHigher(1990, "Drew", "M");
        System.out.println("Total Births Higher Than This is: " + births);
    }
    
}
