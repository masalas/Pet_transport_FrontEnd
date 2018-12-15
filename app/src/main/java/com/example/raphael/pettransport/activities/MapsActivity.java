package com.example.raphael.pettransport.activities;

import android.os.Bundle;
import android.app.Fragment;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.ChamadoJson;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.EstadoChamado;
import com.example.raphael.pettransport.models.Pet;
import com.example.raphael.pettransport.service.ChamadoService;
import com.example.raphael.pettransport.service.PetService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import static com.example.raphael.pettransport.models.Contexto.isNotEmpty;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ChamadoService chamadoService;
    private PetService petService;

    private Map<LatLng, ChamadoJson> chamados = new HashMap<>();

    public MapsActivity() {
        this.chamadoService = new ChamadoService();
        this.petService = new PetService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.chamados = new HashMap<>();
        LatLng chamadoCentro = null;
        for (ChamadoJson chamadoJson : this.chamadoService.obterChamadosPendentes()) {
            LatLng latLng = new LatLng(chamadoJson.latitude(), chamadoJson.longitude());
            if (chamadoCentro == null) {
                chamadoCentro = latLng;
            }
            Pet pet = this.petService.obterPet(chamadoJson.pet());
            this.chamados.put(latLng, chamadoJson);
            StringBuilder stringBuilder = new StringBuilder();
            if (pet.getIdade() != null) {
                stringBuilder.append("Idade: ").append(pet.getIdade()).append("\n");
            }
            if (isNotEmpty(pet.getRaca())) {
                stringBuilder.append("Raça: ").append(pet.getRaca()).append("\n");
            } //Come cu come xereca

            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(pet.getNome())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pet))
                    .snippet(stringBuilder.toString()));

        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ChamadoJson chamadoJson = chamados.get(marker.getPosition());
                chamadoJson.estadoChamado(EstadoChamado.A_CAMINHO);
                chamadoJson.motorista(Contexto.pessoaLogada.id());
                chamadoService.salvarChamado(chamadoJson);
                marker.remove();
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(chamadoCentro));
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this.getContext()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chamadoCentro, 16.0f));
    }


}
