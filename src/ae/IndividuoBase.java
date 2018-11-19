/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 *
 * @author Leonardo
 */
public abstract class IndividuoBase implements Comparable {

    final Gene[] genes;
    final GeneGroup[] geneGroups;
    double lastScore;
    boolean canRetLastScore = false;
    int correct = 0;
    
    double getGraph() {
        return correct * 100 + lastScore;
    }
    
    IndividuoBase(int nGenes, int maxResponse) {
        geneGroups = new GeneGroup[maxResponse];
        for (int i = 0; i < geneGroups.length; i++) {
            geneGroups[i] = new GeneGroup(i);
        }
        genes = new Gene[nGenes];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = new Gene(3);
        }
        genes[0].response = 0;
        genes[1].response = 1;
        genes[2].response = 2;
        genes[3].response = 0;
        fillGeneGroups();
    }
    
    abstract double _score();

    double score() {
        if (canRetLastScore) {
            return lastScore;
        }
        reset();
        lastScore = _score();
        return lastScore;
    }

    public GeneGroup []reduce() {
        // pega o grupo de maior
        // de maior state
        for (GeneGroup group : geneGroups) {
            group.score();
        }
        GeneGroup []geneGroups = Arrays.copyOf(this.geneGroups, this.geneGroups.length);
        Arrays.sort(geneGroups);
        return geneGroups;
    }

    void transe(Individuo individuo) {
        // quer dizer que o índividuo não tem lastScore
        individuo.canRetLastScore = false;
        for (int i = 0; i < geneGroups.length; i++) {
            geneGroups[i].transe(individuo.geneGroups[i]);
        }
    }

    void reset() {
        for (Gene gene : genes) {
            gene.reset();
        }
        correct = 0;
    }

    @Override
    public String toString() {
        String ret = "Individuo<";
        for (int i = 0; i < genes.length; i++) {
            if (i > 0) {
                ret += ", ";
            }
            ret += genes[i].toString();
        }
        ret += ">";
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        Individuo i = (Individuo) o;
        if (i.lastScore == lastScore) {
            if (i.correct == correct) {
                return 0;
            }
            return correct < i.correct ? 1 : -1;
        }
        return lastScore < i.lastScore ? 1 : -1;
    }

    private void fillGeneGroups() {
        for (GeneGroup geneGroup : geneGroups) {
            geneGroup.clear();
        }
        for (int i = 0; i < genes.length; i++) {
            geneGroups[genes[i].response].add(genes[i]);
        }
    }

    int play(Individuo individuo) {
        return score() > individuo.score() ? 0 : 1;
    }
}
