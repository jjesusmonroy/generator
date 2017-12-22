/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

/**
 *
 * @author pasto
 */
public class Methods {

    public Methods() {
    }
    
    public boolean needColumnNames(String query){
        return !query.substring(7,8).equals("*");
    }
    public String[] columnNames(String query){
        String c = query.substring(7);
        String []d = c.split(" from");
        String e = d[0];
        String []f = e.split(",");
        return f;
    }
    public int getMax(String [][] obtener){
        if(obtener.length==0){return 1;}
        int x=Integer.parseInt(obtener[0][0]);
        for(String a[]:obtener){
            for(String b:a){
                x=x<Integer.parseInt(b)?Integer.parseInt(b):x;
            }
        }
        return x+1;      
    }
    
}
