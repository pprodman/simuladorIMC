package com.example.mvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvm.databinding.FragmentMiImcBinding;

public class IMCFragment extends Fragment {

    private FragmentMiImcBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return (binding = FragmentMiImcBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el ViewModel
        final IMCViewModel IMCViewModel = new ViewModelProvider(this).get(IMCViewModel.class);

        // Calcular IMC al hacer clic en el botón
        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double altura = Double.parseDouble(binding.altura.getText().toString());
                double peso = Double.parseDouble(binding.peso.getText().toString());

                IMCViewModel.calcular(altura, peso);
            }
        });

        // Observar los cambios en el IMC del ViewModel
        IMCViewModel.imc.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double imc) {
                binding.imc.setText(String.format("%.2f",imc));

                if (imc < 18.5) {
                    binding.resultado.setText("Peso inferior al normal");
                } else if (imc < 25) {
                    binding.resultado.setText("Peso Normal");
                } else if (imc < 30) {
                    binding.resultado.setText("Peso superior al normal");
                }
            }
        });

        // Observar los cambios en los errores de altura  del ViewModel
        IMCViewModel.errorAltura.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double alturaMinima) {
                if (alturaMinima != null) {
                    binding.altura.setError("La altura no puede ser inferor a " + alturaMinima + " cm.");
                } else {
                    binding.altura.setError(null);
                }
            }
        });

        // Observar los cambios en los errores de peso del ViewModel
        IMCViewModel.errorPeso.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double pesoMinimo) {
                if (pesoMinimo != null) {
                    binding.peso.setError("El peso no puede ser inferior a " + pesoMinimo + " kg.");
                } else {
                    binding.peso.setError(null);
                }
            }
        });

        // Observar los cambios en el estado de calculando del ViewModel
        IMCViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.imc.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.imc.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}