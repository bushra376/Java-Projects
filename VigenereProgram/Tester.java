
import edu.duke.*;
import java.io.*;
import java.util.*;

public class Tester {
    public void testCaesarCipher(){
        CaesarCipher cc = new CaesarCipher(5);
        FileResource fr = new FileResource();
        String s = fr.asString();   //READS THE ENTIRE FILE AS STRING
        
        String decryptedMsg = cc.decrypt(s);        
        System.out.println("Decrypted Msg is:");
        System.out.println(decryptedMsg);
    }
        
    public void testVigenereCipher(){
        int[] key = {17,14,12,4};
        VigenereCipher vg = new VigenereCipher(key);
        FileResource fr = new FileResource();
        String s = fr.asString();   //READS THE ENTIRE FILE AS STRING        

        //String decryptedMsg = vg.decrypt(s);                
        String encryptedMsg = vg.encrypt(s);        
        System.out.println("Encrypted Msg is:");
        System.out.println(encryptedMsg);  
        
        String decryptedMsg = vg.decrypt(encryptedMsg);
        System.out.println("Decrypted Msg is:");
        System.out.println(decryptedMsg);           
    }
    
    public void testVigenereBreaker(){
       VigenereBreaker vb = new VigenereBreaker();
        
        /*
        String slicedMessage = vb.sliceString("abcdefghijklm", 0, 3);
        System.out.println("Sliced Alphabet String is: ");
        System.out.println(slicedMessage);  
        */
       
       /*
       FileResource fr = new FileResource();
       String s = fr.asString();   //READS THE ENTIRE FILE AS STRING  
       int[] key = vb.tryKeyLength( s, 38 , 'e');
       
        System.out.println("Key is:");
       for (int i=0; i < key.length;i++){
           System.out.println(key[i]);        
       }
       */
       /*
       VigenereCipher vc = new VigenereCipher(key);
       String decryptedMsg = vc.decrypt(s);
       
       FileResource dic = new FileResource();
       HashSet<String> dictionary = vb.readDictionary (dic);
       int wordsFound = vb.countWords(decryptedMsg, dictionary);
       
       System.out.println("Words Found : " + wordsFound);             
       */
      
      vb.breakVigenere();

    }
}
