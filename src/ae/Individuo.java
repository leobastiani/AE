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
public class Individuo extends IndividuoBase {

    Individuo(int nGenes, int N_RESPONSES) {
        super(nGenes, N_RESPONSES);
    }

    @Override
    double _score() {
        return 0;
    }
    
}
