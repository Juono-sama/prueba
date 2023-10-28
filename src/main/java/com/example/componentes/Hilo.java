package com.example.componentes;

import javafx.scene.control.ProgressBar;

import java.util.Random;

public class Hilo extends Thread{
private ProgressBar pgbCorredor;
    public Hilo(String nombre, ProgressBar pgb){
        //super(nombre);
        this.setName(nombre);
        this.pgbCorredor = pgb;
    }

    @Override
    public void run() {
        super.run();
        try {
            double progreso = 0;
            while(progreso <= 1) {
                for (int i = 0; i <= 10; i++) {
                    this.sleep((long) (Math.random() * 1500));
                    progreso += Math.random() / 10;
                    pgbCorredor.setProgress(progreso);
                    System.out.println("Corredor  " + this.getName() + " llego al KM " + i);
                }
            }
                System.out.println("Corredor "+this.getName() + " llego a la meta");
            }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
