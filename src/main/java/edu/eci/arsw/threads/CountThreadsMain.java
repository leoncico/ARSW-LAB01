/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import java.lang.Thread;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain extends Thread {
   public void run(){  
    System.out.println("thread is running...");  
    } 
    public static void main(String a[]){
        MyThread thread = new MyThread();
        thread.start();
    }
    
}
