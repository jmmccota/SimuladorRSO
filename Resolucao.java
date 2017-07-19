/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorrso;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Resolucao {

    public static double resolucao(int tipo) {
        //1=x; 2=y  
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        double x = dim.getWidth();//largura  
        double y = dim.getHeight();//altura  
        if (tipo == 1) {
            return x;
        } else {
            return y;
        }
    }
}
