/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae;

/**
 *
 * @author Leonardo
 */
public class Eq {

    static Eq retZero() {
        return new Eq(0, 0, 1);
    }

    static Eq retSame() {
        return new Eq(0, 1, 1);
    }

    static Eq retUm() {
        return new Eq(1, 0, 1);
    }

    static Eq retMenosUm() {
        return new Eq(-1, 0, 1);
    }

    static Eq retNegativo() {
        return new Eq(0, -1, 1);
    }

    static Eq surprise() {
        return new Eq(Rand.surprise(), Rand.surprise(), 1);
    }

    double sum;
    double factor;
    double expoente;

    public Eq(double sum, double factor, double expoente) {
        this.sum = sum;
        this.factor = factor;
        this.expoente = Math.abs(expoente);
    }

    double value(double x) {
        return factor * x + sum;
    }

    @Override
    public String toString() {
        return String.format("(%.2g, %.2g, %.2g)", sum, factor, expoente);
    }

    void transe(Eq eq) {
        eq.sum = AE.transeDouble(eq.sum, sum);
        eq.factor = AE.transeDouble(eq.factor, factor);
        eq.expoente = AE.transeDouble(eq.expoente, expoente);
    }
}
