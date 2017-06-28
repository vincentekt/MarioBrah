package com.vladislav.mariobros.MLFW;

import com.vladislav.mariobros.Tools.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.exp;
import static java.lang.Math.random;

/**
 * Created by Victor on 19-Mar-17.
 */
public class NN {
    private int nHid;
    private Map<Pair<Integer, Integer>, List<Double>> wt;
    public NN(Map<Integer, Integer> laySpec, int nIn, int nOut) {
        nHid = laySpec.keySet().size();
        wt = new HashMap<Pair<Integer, Integer>, List<Double>>();

        initWt(nIn, laySpec.get(0), 0);
        initWt(laySpec.get(nHid - 1), nOut, nHid);
        for(int i = 0; i < nHid - 1; i++){
            initWt(laySpec.get(i), laySpec.get(i + 1), i + 1);
        }
    }

    private void initWt(int nPred_this, int nPred_next, int layer_idx){
        for(int i = 0; i < nPred_this; i++){
            List<Double> tmpList = new ArrayList<Double>();
            for(int j=0; j < nPred_next; j++){
                tmpList.add(random());
            }
            this.wt.put(new Pair<Integer, Integer>(layer_idx, i), tmpList);
        }
    }

    private List<Double> getScore(){
        return new ArrayList<Double>();
    }

    private double sigmoidFn(float val){
        return 1/(1 + exp(-val));
    }
}
