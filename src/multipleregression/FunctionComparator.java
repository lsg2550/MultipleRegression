/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author srv_veralab
 */
public enum FunctionComparator implements Comparator<OLSFunction> {

    SSR_SORT {
        @Override
        public int compare(OLSFunction o1, OLSFunction o2) {
            return Double.valueOf(o1.getSSR()).compareTo(o2.getSSR());
        }
    },
    
    VARIABLE_SORT {
        @Override
        public int compare(OLSFunction o1, OLSFunction o2) {
            if (o1.getDependentDataVariable().getId() == o2.getDependentDataVariable().getId()) {
                List<DataVariable> x = new ArrayList<>(o1.getOrderedSetOfDependentDataVariables());
                List<DataVariable> y = new ArrayList<>(o2.getOrderedSetOfDependentDataVariables());
                int length = Math.min(x.size(), y.size());
                for (int i = 0; i < length; i++) {
                    if (x.get(i).getId() > y.get(i).getId())
                        return 1;
                    if (x.get(i).getId() < y.get(i).getId())
                        return -1;
                }
                if (x.size() == y.size())
                    return 0;
                if (x.size() > y.size())
                    return 1;
            }
            if (o1.getDependentDataVariable().getId() > o2.getDependentDataVariable().getId()) {
                return 1;
            }
            return -1;
        }
    };
}
