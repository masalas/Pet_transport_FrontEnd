package com.example.raphael.pettransport.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.Pet;
import com.example.raphael.pettransport.service.PetService;

public class ActivityCadastroPet extends Activity {

    private PetService petService;

    public ActivityCadastroPet() {
        this.petService = new PetService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastropet);
        findViewById(R.id.btnCadastrarPetNovo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pet pet = new Pet();
                pet.setNome(String.valueOf(((TextView) findViewById(R.id.textNomeUsuario)).getText()));
                pet.setRaca(String.valueOf(((TextView) findViewById(R.id.textDataNascimento)).getText()));
                try {
                    pet.setIdade(Integer.valueOf(String.valueOf(((TextView) findViewById(R.id.textCpf)).getText())));
                } catch (Exception e) {
                    // Gambiarra, a gente vo por aqui
                }
                pet.setObservacoes(String.valueOf(((TextView) findViewById(R.id.textUsername)).getText()));
                petService.salvarPet(pet);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityCadastroPet.this);
                alerta.setTitle("Aviso");
                alerta.setIcon(R.mipmap.ic_launcher);
                alerta.setMessage("Pet Cadastrado com sucesso!");
                alerta.setCancelable(false);
                alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }
                );
                AlertDialog alertDialog = alerta.create();
                alertDialog.show();

            }
        });
    }

}
