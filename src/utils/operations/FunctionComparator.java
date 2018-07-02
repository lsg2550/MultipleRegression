package utils.operations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A comparator to sort functions according to SSR or Variable ID
 *
 * @author Anton Kovalyov
 */
public enum FunctionComparator implements Comparator<OLS> {

    SSR_SORT {
        @Override
        public int compare(OLS o1, OLS o2) {
            return Double.valueOf(o1.getSSR()).compareTo(o2.getSSR());
        }
    },
    VARIABLE_SORT {
        @Override
        public int compare(OLS o1, OLS o2) {
            if (o1.getFunction().getDependentVariable().getId() == o2.getFunction().getDependentVariable().getId()) {
                List<DataVariable> x = new ArrayList<>(o1.getFunction().getOrderedSetOfDependentDataVariables());
                List<DataVariable> y = new ArrayList<>(o2.getFunction().getOrderedSetOfDependentDataVariables());
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

            if (o1.getFunction().getDependentVariable().getId() > o2.getFunction().getDependentVariable().getId()) {
                return 1;
            }

            return -1;
        }
    };
}
