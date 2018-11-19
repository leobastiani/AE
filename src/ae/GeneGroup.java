/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae;

import java.util.ArrayList;

/**
 *
 * @author Leonardo
 */
public class GeneGroup implements Comparable {
    // a resposta que esse grupo de gene reproduz
    int response;
    ArrayList<Gene> genes = new ArrayList<>();
    Eq out = Eq.surprise();
    private double lastScore = 0;

    public GeneGroup(int response) {
        this.response = response;
    }

    void clear() {
        genes.clear();
    }

    void add(Gene gene) {
        genes.add(gene);
    }
    
    double state() {
        double ret = 0;
        for (Gene gene : genes) {
            ret += gene.getState();
        }
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        GeneGroup group = (GeneGroup) o;
        if (lastScore == group.lastScore) {
            return 0;
        }
        return lastScore < group.lastScore ? 1 : -1;
    }

    double score() {
        lastScore = out.value(state());
        return lastScore;
    }

    void transe(GeneGroup geneGroup) {
        // transa os genes
        for (int i = 0; i < genes.size(); i++) {
            genes.get(i).transe(geneGroup.genes.get(i));
        }
        // transa a equação
        out.transe(geneGroup.out);
    }
}
