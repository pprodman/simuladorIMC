package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IMCViewModel extends AndroidViewModel {

    Executor executor;

    SimuladorIMC simulador;

    MutableLiveData<Double> imc = new MutableLiveData<>();
    MutableLiveData<Double> errorAltura = new MutableLiveData<>();
    MutableLiveData<Double> errorPeso = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public IMCViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorIMC();
    }

    public void calcular(double altura, double peso) {

        final SimuladorIMC.Solicitud solicitud = new SimuladorIMC.Solicitud(altura, peso);

        executor.execute(new Runnable() {
            @Override
            public void run() {

                simulador.calcular(solicitud, new SimuladorIMC.Callback() {
                    @Override
                    public void cuandoEsteCalculadoElIMC(double imcResultante) {
                        errorAltura.postValue(null);
                        errorPeso.postValue(null);
                        imc.postValue(imcResultante);
                    }

                    @Override
                    public void cuandoHayaErrorDeAlturaInferiorAlMinimo(double alturaMinima) {
                        errorAltura.postValue(alturaMinima);
                    }

                    @Override
                    public void cuandoHayaErrorDePesoInferiorAlMinimo(double pesoMinimo) {
                        errorPeso.postValue(pesoMinimo);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            }
        });
    }
}