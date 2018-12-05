package com.example.raphael.pettransport.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.ChamadoJson;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.EstadoChamado;
import com.example.raphael.pettransport.models.Pet;
import com.example.raphael.pettransport.models.TipoPessoa;
import com.example.raphael.pettransport.service.ChamadoService;
import com.example.raphael.pettransport.service.PetService;

import java.util.List;

import static com.google.android.gms.common.util.ArrayUtils.newArrayList;

public class ActivityChamados extends Activity {

    private PetService petService;
    private ChamadoService chamadoService;

    private ListView listViewChamados;
    private Integer chamadoSelecionado;

    private AdapterView.OnItemClickListener listViewClickListenet = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adpterView, View view, int position, long id) {
            chamadoSelecionado = position;
            for (int i = 0; i < listViewChamados.getChildCount(); i++) {
                if (position == i) {
                    listViewChamados.getChildAt(i).setBackgroundColor(Color.rgb(54, 76, 48));
                } else {
                    listViewChamados.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    };

    public ActivityChamados() {
        this.petService = new PetService();
        this.chamadoService = new ChamadoService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamados);

        this.listViewChamados = findViewById(R.id.listChamados);
        this.listViewChamados.setOnItemClickListener(this.listViewClickListenet);

        final List<ChamadoJson> chamados = this.chamadoService.obterChamadosPessoais();
        this.listViewChamados.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.chamadosToString(chamados)));
        final Context gambiarra = this;

        if (Contexto.pessoaLogada.tipoPessoa().equals(TipoPessoa.MOTORISTA)) {
            ((Button) findViewById(R.id.btnAcaoChamado)).setText("Entregue");
            findViewById(R.id.btnAcaoChamado).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chamadoSelecionado!=null) {
                        ChamadoJson chamadoJson = chamados.get(chamadoSelecionado);
                        chamadoJson.estadoChamado(EstadoChamado.ENTREGUE);
                        chamadoService.salvarChamado(chamadoJson);
                        final List<ChamadoJson> chamados = chamadoService.obterChamadosPessoais();
                        listViewChamados.setAdapter(new ArrayAdapter<String>(gambiarra, android.R.layout.simple_list_item_1, chamadosToString(chamados)));
                    }
                }
            });
        } else {
            ((Button) findViewById(R.id.btnAcaoChamado)).setText("Cancelar");
            findViewById(R.id.btnAcaoChamado).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chamadoSelecionado!=null) {
                        ChamadoJson chamadoJson = chamados.get(chamadoSelecionado);
                        chamadoJson.estadoChamado(EstadoChamado.CANCELADO);
                        chamadoService.salvarChamado(chamadoJson);
                        final List<ChamadoJson> chamados = chamadoService.obterChamadosPessoais();
                        listViewChamados.setAdapter(new ArrayAdapter<String>(gambiarra, android.R.layout.simple_list_item_1, chamadosToString(chamados)));
                    }
                }
            });
        }




    }


    private List<String> chamadosToString(List<ChamadoJson> chamados) {
        List<String> chamadosS = newArrayList();
        for (ChamadoJson chamado : chamados) {
            Pet pet = this.petService.obterPet(chamado.pet());
            chamado.estadoChamado();
            chamadosS.add(pet.getNome()+"\n"+chamado.estadoChamado()+"\n"+chamado.tiposServicos());
        }
        return chamadosS;
    }
}
