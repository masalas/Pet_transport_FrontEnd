package com.example.raphael.pettransport.service;

import com.example.raphael.pettransport.api.ApiConnection;
import com.example.raphael.pettransport.models.PessoaJson;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UsuarioService {

    private ApiConnection apiConnection;

    public UsuarioService() {
        this.apiConnection = new ApiConnection();
    }

    public PessoaJson login(String username, String password) {
        PessoaJson pessoa = this.login(username, password, "CLIENTE");
        if (pessoa == null) {
            pessoa  = this.login(username, password, "MOTORISTA");
        }
        return pessoa;
    }

    public PessoaJson login(String username, String password, String tipo) {
        Map<String, String> parametros = new HashMap();
        parametros.put("username", username);
        parametros.put("password", password);
        parametros.put("tipo", tipo);
        try {
            String json = this.apiConnection.sendGet("/pessoas", parametros);
            return new Gson().fromJson(json, PessoaJson.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void salvarPessoa(PessoaJson pessoaJson) {
        Map<String, String> params = new HashMap<>();
        params.put("pessoa", new Gson().toJson(pessoaJson));
        try {
            this.apiConnection.sendPost("/pessoas", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
