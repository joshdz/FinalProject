/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortuna;

import java.util.Random;

/**
 *
 * @author jjlui
 */
public class Prueba {
    public static void main(String[] args) {
        Random r = new Random();
        r.nextInt(50);
        int i = r.nextInt(50);
        System.out.println(i);
    }
}
