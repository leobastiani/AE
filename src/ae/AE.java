/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.text.html.HTML.Tag.INPUT;

/**
 *
 * @author Leonardo
 */
public class AE {

    private static int N_RESPONSES = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AE ae = new AE(16, 4);
        ae.run();
    }

    static boolean isSame(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    static double transeDouble(double a, double b) {
        int rand = Rand.randint(1, 4);
        double ret;
        if (rand == 1) {
            ret = a;
        }
        else if (rand == 2) {
            ret = b;
        }
        else {
            ret = (a + b) / (double) 2;
        }
        // mutação
        ret += ret * 0.1 * (0.5 - Rand.random()) * 2;
        return ret;
    }

    static int randResponse() {
        return Rand.randint(0, N_RESPONSES-1);
    }

    private final Individuo[] individuos;
    private long gen = 0;

    private AE(int nInd, int nGenes) {
        individuos = new Individuo[nInd];
        for (int i = 0; i < individuos.length; i++) {
            individuos[i] = new Individuo(nGenes, AE.N_RESPONSES);
        }
    }
    
    static public int indexOfSmallest(double []arr) {
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[res]) {
                res = i;
            }
        }
        return res;
    }

    private void run() {
        
        gen++;

        playAll(individuos.length);
        // nesse ponto
        // o individuo 0 ganhou de todos no mata-mata
        // e o individuo 1 ficou em segundo

        transeAll();

        if (gen % 500 == 0) {
            descartePiores();
        }
        //printState();
    }
    
    private void descartePiores() {
        int i = individuos.length - 1;
        // remove metade
        int nRemocoes = individuos.length / 2;
        for (int j = 0; j < nRemocoes; j++) {
            individuos[i] = new Individuo(individuos[i].genes.length, AE.N_RESPONSES);
            i--;
        }
    }

    private void printState() {
        System.out.print(individuos[0].lastScore);
        System.out.print(",");
        System.out.println(fitnessMedio());
    }

    private void playAll(int length) {
        // vamos supor o caso de 8 aprticipantes
        // nPlays: 4
        /**
         * ind[0] x ind[7]
         * ind[1] x ind[6]
         * ind[2] x ind[5]
         * ind[3] x ind[4]
         */
        // porque individuos pares transam com pares
        // e individuos ímpares transam com ímpares
        
        // número de partidas
        int nPlays = length / 2;
        for (int i = 0; i < nPlays; i++) {
            int player0 = i;
            int player1 = length-i-1;
            int winner = individuos[i].play(individuos[length-i-1]);
            if (winner == 1) {
                // o player 1 ganhou
                // devo colocar ele no lugar do player0
                Individuo tmp = individuos[player0];
                individuos[player0] = individuos[player1];
                individuos[player1] = tmp;
            }
        }
        if (length > 2) {
            // vai fechando o mata-mata
            playAll(nPlays);
        }
    }

    private void transeAll() {
        // transo o 0 com os pares
        for (int i = 2; i < individuos.length; i += 2) {
            individuos[0].transe(individuos[i]);
        }
        // transo o 1 com os ímpares
        for (int i = 3; i < individuos.length; i += 2) {
            individuos[1].transe(individuos[i]);
        }
    }

    private double fitnessMedio() {
        double sum = 0;
        for (Individuo i : individuos) {
            sum += i.lastScore;
        }
        return sum / (double) individuos.length;
    }
}
