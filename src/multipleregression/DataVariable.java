/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author anton
 */
public class DataVariable implements Comparable {

    private final int id;
    private final List<Double> data;

    public DataVariable(final int id) {
        this.id = id;
        this.data = null;
    }
    
    public DataVariable(final int id, final List<Double> data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DataVariable) {
            DataVariable other = (DataVariable)o;
            if (this.id == other.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        return hash;
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the data
     */
    public List<Double> getData() {
        return Collections.unmodifiableList(data);
    }
    
    public double[] getDataArray() {
        double[] dataArray = new double[this.data.size()];
        for (int i = 0; i < dataArray.length; i++) {
            dataArray[i] = data.get(i);
        }
        return dataArray;
    }
    
    @Override
    public String toString() {
        return "X" + this.id;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof DataVariable) {
            DataVariable other = (DataVariable) o;
            if (this.getId() == other.getId())
                return 0;
            if (this.getId() > other.getId())
                return 1;
        }
        return -1;
    }

    /**
     * @return the parameter
     */
}
