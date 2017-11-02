import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder msg = new StringBuilder(message);
        StringBuilder sb = new StringBuilder();
        
        for (int i= whichSlice; i <message.length(); i+= totalSlices){
            sb.append(msg.charAt(i));
        }        
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];   //Key is Flute = 5 letters
        String slicedString = "";
        CaesarCracker cc = new CaesarCracker();
        for (int i=0; i< klength; i++){
            //Getting the sliced string 
            slicedString = sliceString(encrypted, i , klength);
            key[i] = cc.getKey(slicedString);
        }        
        return key;
    }

    public void breakVigenere () {
        //Reading in the message to be decrypted
        FileResource file  = new FileResource();
        String msg = file.asString();
        HashMap<String, HashSet<String>> allDics = new HashMap<String, HashSet<String>>();
        //Reading in Dictionary
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource dic = new FileResource(f);
            String langName = f.getName();// getting language name
            HashSet<String> dictionaryForLang = readDictionary (dic); // gettiing lang words from dictionary
            allDics.put(langName, dictionaryForLang);
            System.out.println("Added the dictionary for " + langName);
        }
        
        breakForAllLangs(msg , allDics);
            //HashSet<String> dictionaryEnglish = readDictionary (dic);
        
            //String decryptedMsg = breakForLanguage(msg, dictionaryForLang);
        
            //////////////////////////////////////////////////
            /*
             * int keyLength = 4;
             * int[] key = tryKeyLength(msg, keyLength ,'e');
             * VigenereCipher vc = new VigenereCipher(key);
             * String decryptedMsg = vc.decrypt(msg);
               */
              ////////////////////////////////////////////////
             // System.out.println("Decrypted Msg is:");
              //System.out.println(decryptedMsg);       
        
    }
    
    public HashSet<String> readDictionary (FileResource fr){
        HashSet<String> hs = new HashSet<String>();
        for (String s: fr.lines()){
            String word = s.toLowerCase();
            hs.add(word);
        }
        return hs;
    }
    
    public int countWords( String message, HashSet<String> dictionary){
        int validWordsFound = 0;
        for(String word: message.split("\\W")){
            word = word.toLowerCase();
            if (dictionary.contains(word)){
                validWordsFound++;
            }
        }
        return validWordsFound;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        char mostCommon = mostCommonCharIn (dictionary);
        int wordsFound = 0;
        int maxWordsFound = 0;
        String finalMsg = "";
        int[] keyForFinalMsg = {0};
        int keyL = 0;
        // we are trying to find the length of the key here represented by i
        for (int i=1; i<=100; i++){
            int[] key = tryKeyLength(encrypted, i, mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String decryptedMsg = vc.decrypt(encrypted);
            wordsFound = countWords(decryptedMsg, dictionary);
            //finding the MAX number of words in language
            if( wordsFound  > maxWordsFound){
                maxWordsFound = wordsFound;
                finalMsg = decryptedMsg;
                keyForFinalMsg = key;
                keyL = i;
            }
        }
       System.out.println();        
       System.out.println(" Max Words Found : " + maxWordsFound);             
       System.out.println();                   
       System.out.println(" Key Length : " + keyL);       
       System.out.println();          
       System.out.println(" KEY IS : ");        
       for (int i=0; i < keyForFinalMsg.length;i++){        
           System.out.println(  keyForFinalMsg[i]);     
       }
       System.out.println();          
       return finalMsg;
    }
    
    public char mostCommonCharIn (HashSet<String> dictionary){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        int[] counters = new int[26];
        //finding the count of each letter in dictionary
          for(String s:   dictionary){
            for(int i=0; i < s.length(); i++){
                char currChar = s.charAt(i);
                int index = alpha.indexOf(Character.toLowerCase(currChar));
                if (index != -1){
                    counters[index] += 1;
                }
            }            
        }
        //finding the MAX index
        int maxIndex = 0;
        for(int i = 0; i < counters.length; i++){
            if (counters[i] > counters[maxIndex]){
                maxIndex = i;
            }
        }                
        System.out.println("Most Common char is  : " + alpha.charAt(maxIndex));         
        return alpha.charAt(maxIndex);
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String,HashSet<String>> languages){
        int maxWords = 0;
        String correctLanguageMsg = "";
        String finalLang = "";
        for(String s: languages.keySet()){
            String decrypted = breakForLanguage(encrypted, languages.get(s)); 
            int wordsFound = countWords( decrypted, languages.get(s));
            if (wordsFound > maxWords){
                maxWords = wordsFound;
                correctLanguageMsg = decrypted;
                finalLang = s;
            }
        }
        System.out.println();        
        System.out.println("Correct Language  is: "  + finalLang); 
        System.out.println();
        System.out.println("Words Found in Common: " + maxWords );         
        System.out.println();
        System.out.println("Correct Language Decryption is: ");        
        System.out.println();
        System.out.println(correctLanguageMsg);        
               
    }
}
