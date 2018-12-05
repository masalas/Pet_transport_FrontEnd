package com.example.raphael.pettransport.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.ChamadoJson;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.EstadoChamado;
import com.example.raphael.pettransport.models.Pet;
import com.example.raphael.pettransport.models.TipoServico;
import com.example.raphael.pettransport.service.ChamadoService;
import com.example.raphael.pettransport.service.PetService;

import java.util.ArrayList;
import java.util.List;

import static com.example.raphael.pettransport.service.KoreMessages.showMessage;

public class SelecaoActivity extends Activity {

    private PetService petService;
    private ChamadoService chamadoService;

    private ListView listViewPets;
    private Integer petSelecionado;

    LocationManager locationManager;

    private AdapterView.OnItemClickListener listViewClickListenet = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adpterView, View view, int position, long id) {
            petSelecionado = position;
            for (int i = 0; i < listViewPets.getChildCount(); i++) {
                if (position == i) {
                    listViewPets.getChildAt(i).setBackgroundColor(Color.rgb(54, 76, 48));
                } else {
                    listViewPets.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    };

    public SelecaoActivity() {
        this.petService = new PetService();
        this.chamadoService = new ChamadoService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_selecao);

        final List<Pet> pets = this.petService.obterPetsUsuario();

        this.listViewPets = findViewById(R.id.listPets);
        this.listViewPets.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.petsToString(pets)));
        this.listViewPets.setOnItemClickListener(this.listViewClickListenet);

        Button botaoSelecao = findViewById(R.id.btnSelecaoServico);
        botaoSelecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TipoServico> tipoServicos = new ArrayList<>();
                if (((CheckBox) findViewById(R.id.cbBanho)).isChecked()) {
                    tipoServicos.add(TipoServico.BANHO);
                }
                if (((CheckBox) findViewById(R.id.cbBanhoMedicinal)).isChecked()) {
                    tipoServicos.add(TipoServico.BANHO_MEDICINAL);
                }
                if (((CheckBox) findViewById(R.id.cbTosaHigienica)).isChecked()) {
                    tipoServicos.add(TipoServico.TOSA_HIGIENICA);
                }
                if (((CheckBox) findViewById(R.id.cbTosaTotal)).isChecked()) {
                    tipoServicos.add(TipoServico.TOSA_TOTAL);
                }
                if (petSelecionado != null) {
                    Contexto.chamado = new ChamadoJson();
                    Contexto.chamado.tiposServicos(tipoServicos);
                    Contexto.chamado.pet(Long.valueOf(pets.get(petSelecionado).getId()));
                    Contexto.chamado.estadoChamado(EstadoChamado.PENDENTE);
                    find_Location(getApplicationContext(), "gps");
                    if (Contexto.chamado.latitude() == null) {
                        find_Location(getApplicationContext());
                    }
                    if (Contexto.chamado.latitude() != null) {
                        chamadoService.salvarChamado(Contexto.chamado);
                        finish();
                        //showMessage(getApplicationContext(), "Info", "Seu pedido já foi emitido, aguarde até um candango lhe buscar");
                    }
                }

            }
        });
    }

    private List<String> petsToString(List<Pet> pets) {
        List<String> petStrings = new ArrayList<>();
        for (Pet pet : pets) {
            petStrings.add(pet.toString());
        }
        return petStrings;
    }

    public void find_Location(Context con) {
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) con.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            this.findLocationWithProvider(provider);
        }
    }

    public void find_Location(Context con, String provider) {
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) con.getSystemService(location_context);
        if (locationManager.isProviderEnabled(provider)) {
            return;
        }
        this.findLocationWithProvider(provider);
    }

    private void findLocationWithProvider(String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 0, 0,
                new LocationListener() {
                    public void onLocationChanged(Location location) {}
                    public void onProviderDisabled(String provider) {}
                    public void onProviderEnabled(String provider) {}
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                });
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            Contexto.chamado.latitude(location.getLatitude());
            Contexto.chamado.longitude(location.getLongitude());
        }
    }
}
