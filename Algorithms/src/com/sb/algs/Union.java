package com.sb.algs;

import java.util.Arrays;

public class Union {
    
    private final int[] array;
    
    public Union(int n) {
        this.array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
    }
    
    public void union(int p, int q) {
        connect(p, q);
    }
    
    public void connect(int p, int q) {
        array[p] = find(q);
    }
    
    public int find(int p) {
        int q = array[p];
        while (q != p) {
            p = q;
            q = array[p];
        }
        return q;
    }
    
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    
    @Override
    public String toString() {
        return "Union: " + Arrays.toString(array); 
    }

    public static void main(String[] args) {
        Union u = new Union(10);

        u.connect(2, 7);
        System.out.println(u);

        u.connect(3, 4);
        System.out.println(u);

        assert u.connected(3, 7) == false;

        u.connect(3, 7);
        System.out.println(u);

        assert u.connected(3, 7) == true;

        assert u.find(7) == 7;
        assert u.connected(1, 7) == false;
        assert u.connected(2, 7) == true;
    }
}
