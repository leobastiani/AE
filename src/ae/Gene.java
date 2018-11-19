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
public class Gene implements Comparable {
    
    double initialState = Rand.surprise();
    private double state = initialState;
    double resetHigh = Rand.surprise();
    double resetLow = Rand.surprise();
    
    Eq in;
    Eq []inIndex;
    int response;

    public Gene(int maxIndex) {
        in = new Eq(Rand.surprise(), Rand.surprise(), 1);
        inIndex = new Eq[maxIndex];
        response = AE.randResponse();
        for (int i = 0; i < inIndex.length; i++) {
            inIndex[i] = new Eq(Rand.surprise(), Rand.surprise(), 1);
        }
    }
    
    void consume(int value, int index) {
        state += in.value(value) * inIndex[index].value(index);
    }

    void transe(Gene gene) {
        in.transe(gene.in);
        for (int i = 0; i < inIndex.length; i++) {
            inIndex[i].transe(gene.inIndex[i]);
        }
        gene.initialState = AE.transeDouble(initialState, gene.initialState);
        gene.resetHigh = AE.transeDouble(resetHigh, gene.resetHigh);
        gene.resetLow = AE.transeDouble(resetLow, gene.resetLow);
    }

    void reset() {
        state = initialState;
    }

    @Override
    public String toString() {
        String ret = "Gene<";
        ret += in.toString();
        ret += ">";
        return ret;
    }

    public double getState() {
        double ret = state;
        if (state > resetHigh || state < resetLow) {
            reset();
        }
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        Gene g = (Gene) o;
        if (state == g.state) {
            return 0;
        }
        return state < g.state ? 1 : -1;
    }
}
