/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae;
import java.util.Random;
/**
 *
 * @author Leonardo
 */
public class Rand {
    static Random generator = new Random(1);
    static int randint(int start, int end) {
        return generator.nextInt(end - start + 1) + start;
    }

    static double surprise() {
        int roleta = randint(1, 10);
        if (roleta <= 3) {
            return randdouble(-100, 100);
        }
        return randdouble(-2, 2);
    }
    
    static double surpriseNotZero() {
        while(true) {
            double ret = surprise();
            if (ret != 0) {
                return ret;
            }
        }
    }

    private static double randdouble(double start, double end) {
        return (generator.nextDouble() * (end - start)) + start;
    }

    static double random() {
        return randdouble(0, 1);
    }
    
}
