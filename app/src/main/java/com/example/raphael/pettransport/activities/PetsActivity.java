package com.example.raphael.pettransport.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.controller.ControllerPet;
import com.example.raphael.pettransport.impl.ControllerPetImpl;
import com.example.raphael.pettransport.models.Pet;
import com.example.raphael.pettransport.service.PetService;

import java.util.ArrayList;
import java.util.List;

public class PetsActivity extends Activity {

    Button button;
    EditText raio;
    ListView listViewPets;
    ArrayAdapter<String> adaptador;
    List<Pet> pets;
    PetService controllerPet;

    public PetsActivity() {
        controllerPet = new PetService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarpets);

        this.button = findViewById(R.id.procurarbotao);
        this.raio = findViewById(R.id.raiotexto);
        this.listViewPets = findViewById(R.id.listViewPets);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPets(v);
            }
        });
    }

    public void buscarPets(View view) {
        String raiotxt = this.raio.getText().toString();

        pets = new ArrayList<>();

        try {
            if (raiotxt.isEmpty()){
                pets = controllerPet.obterPets();
            }
            else{
                //pets = controllerPet.getPetsByRaio(Integer.valueOf(raiotxt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.petsToString(pets));

        listViewPets.setAdapter(adaptador);

        listViewPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = (String) listViewPets.getItemAtPosition(i);
                name = name.substring(0,name.indexOf('-'));
                if(pets != null){
                    for(int o = 0; o < pets.size();o++){

                        if(name.trim().equalsIgnoreCase(pets.get(i).getNome())){
                            Intent intent = new Intent(PetsActivity.this,ActivityMapa.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nomepet",pets.get(i).getNome());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            o = pets.size();
                        }
                    }
                }else{
                    Toast.makeText(PetsActivity.this,"pets é nulo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<String> petsToString(List<Pet> pets){
        List<String> petStrings  = new ArrayList<>();
        for (Pet pet : pets) {
            petStrings.add(pet.toString());
        }
        return petStrings;
    }

}



