package com.example.mvvm;

public class SimuladorIMC {

    public static class Solicitud {
        public double altura;
        public double peso;

        public Solicitud(double altura, double peso) {
            this.altura = altura;
            this.peso = peso;
        }
    }

    interface Callback {
        void cuandoEsteCalculadoElIMC(double imc);
        void cuandoHayaErrorDeAlturaInferiorAlMinimo(double alturaMinima);
        void cuandoHayaErrorDePesoInferiorAlMinimo(double pesoMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();

        double alturaMinima = 0;
        int pesoMinimo = 0;

        try {
            Thread.sleep(2500);  // long run operation
            alturaMinima = 0;
            pesoMinimo = 0;
        } catch (InterruptedException e) {}

        boolean error = false;
        if (solicitud.altura < alturaMinima) {
            callback.cuandoHayaErrorDeAlturaInferiorAlMinimo(alturaMinima);
            error = true;
        }

        if (solicitud.peso < pesoMinimo) {
            callback.cuandoHayaErrorDePesoInferiorAlMinimo(pesoMinimo);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadoElIMC(solicitud.peso / Math.pow((solicitud.altura/100), 2));
        }
        callback.cuandoFinaliceElCalculo();
    }
}






















