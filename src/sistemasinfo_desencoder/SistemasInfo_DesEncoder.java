/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasinfo_desencoder;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author dariovillalta
 */
public class SistemasInfo_DesEncoder {

    /**
     * @param args the command line arguments
     */
    static ArrayList <String> wordBank = new ArrayList();
    static ArrayList <String> realWords = new ArrayList();
    static ArrayList <String> oraciones = new ArrayList();
    static ArrayList <Integer> numberWords = new ArrayList();
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static void main(String[] args) {
        loadWordBank();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la cadena a descifrar:");
        String cadena = sc.nextLine();
        for (int i = 0; i < 24; i++) {
            cadena  = addOffset(cadena);
        }
        ArrayList <Integer> highest = new ArrayList();
        int mayor = 0;
        for (int i = 0; i < numberWords.size(); i++) {
            if(numberWords.get(i) >= mayor)
                mayor = numberWords.get(i);
        }
        for (int i = 0; i < numberWords.size(); i++) {
            if(numberWords.get(i) >= mayor)
                highest.add(i);
        }
        for (int i = 0; i < highest.size(); i++) {
            System.out.println(ANSI_BLUE_BACKGROUND+"ORACION: "+ oraciones.get(highest.get(i)));
        }
    }
    
    public static String addOffset(String cadena){
        String[] partCadena = cadena.split(" ");
        int contador = 0;
        String nuevaCadena = "";
        realWords.clear();
        while(contador < partCadena.length){
            String nuevaPal = "";
            for (int i = 0; i < partCadena[contador].length(); i++) {
                char caracter;
                if((int)partCadena[contador].charAt(i) == 122)
                    caracter = (char)97;
                else if((int)partCadena[contador].charAt(i) == 90)
                    caracter = (char)65;
                else
                    caracter = (char)(((int)partCadena[contador].charAt(i))+1);
                nuevaPal+=caracter;
            }
            contador++;
            nuevaCadena+=nuevaPal+" ";
        }
        System.out.println(nuevaCadena);
        oraciones.add(nuevaCadena);
        buscarPalabra(nuevaCadena.split(" "));
        numberWords.add(0);
        for (int i = 0; i < realWords.size(); i++) {
            numberWords.set(numberWords.size()-1, numberWords.get(numberWords.size()-1) + 1);
            System.out.print(ANSI_RED_BACKGROUND+"PALABRA: "+ANSI_WHITE);
            System.out.println(realWords.get(i)+ANSI_WHITE);
        }
        return nuevaCadena;
    }
    
    public static void loadWordBank(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("./src/sistemasinfo_desencoder/wordBank.txt"));
            String line = br.readLine();

            while (line != null) {
                wordBank.add(line);
                line = br.readLine();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void buscarPalabra(String[] cadena){
        for (int i = 0; i < wordBank.size(); i++) {
            for (int j = 0; j < cadena.length; j++) {
                if(cadena[j].toLowerCase().equals(wordBank.get(i).toLowerCase()))
                    realWords.add(wordBank.get(i));
            }
        }
    }
    
}
