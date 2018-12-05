package com.example.raphael.pettransport.service;

import android.content.Context;

import com.example.raphael.pettransport.api.ApiConnection;
import com.example.raphael.pettransport.models.ChamadoJson;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.TipoPessoa;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChamadoService {

    private ApiConnection apiConnection;

    public ChamadoService() {
        this.apiConnection = new ApiConnection();
    }

    public List<ChamadoJson> obterChamadosPessoais() {
        List<ChamadoJson> chamados = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        if (Contexto.pessoaLogada.tipoPessoa().equals(TipoPessoa.MOTORISTA)){
            params.put("motorista", String.valueOf(Contexto.pessoaLogada.id()));
        } else {
            params.put("cliente", String.valueOf(Contexto.pessoaLogada.id()));
        }
        try {
            String json = this.apiConnection.sendGet("/chamados", params);
            Type listType = new TypeToken<ArrayList<ChamadoJson>>(){}.getType();
            chamados = new Gson().fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chamados;
    }


    public List<ChamadoJson> obterChamadosPendentes() {
        List<ChamadoJson> chamados = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("estadoChamado", "PENDENTE");
        try {
            String json = this.apiConnection.sendGet("/chamados", params);
            Type listType = new TypeToken<ArrayList<ChamadoJson>>(){}.getType();
            chamados = new Gson().fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chamados;
    }

    public void salvarChamado(ChamadoJson chamadoJson) {
        Map<String, String> params = new HashMap<>();
        params.put("chamado", new Gson().toJson(chamadoJson));

        try {
            this.apiConnection.sendPost("/chamados", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
