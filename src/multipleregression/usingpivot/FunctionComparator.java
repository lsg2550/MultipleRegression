package multipleregression.usingpivot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A comparator to sort functions according to SSR or Variable ID
 *
 * @author Anton Kovalyov
 */
enum FunctionComparator implements Comparator<OLSFunction> {

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
                    if (x.get(i).getId() > y.get(i).getId()) {
                        return 1;
                    }
                    if (x.get(i).getId() < y.get(i).getId()) {
                        return -1;
                    }
                }

                if (x.size() == y.size()) {
                    return 0;
                }

                if (x.size() > y.size()) {
                    return 1;
                }
            }

            if (o1.getDependentDataVariable().getId() > o2.getDependentDataVariable().getId()) {
                return 1;
            }

            return -1;
        }
    };
}
