package com.example.raphael.pettransport.service;

import com.example.raphael.pettransport.api.ApiConnection;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.Pet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.raphael.pettransport.models.Contexto.isNotEmpty;

public class PetService {

    private ApiConnection apiConnection;

    public PetService() {
        this.apiConnection = new ApiConnection();
    }

    public Pet obterPet(Long id) {
        List<Pet> pets = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        try {
            String json = this.apiConnection.sendGet("/pets", params);
            Type listType = new TypeToken<ArrayList<Pet>>(){}.getType();
            pets = new Gson().fromJson(json, listType);
            new Gson().fromJson(json, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets.isEmpty() == false ? pets.get(0) : null;
    }

    public List<Pet> obterPets() {
        List<Pet> pets = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        try {
            String json = this.apiConnection.sendGet("/pets", params);
            Type listType = new TypeToken<ArrayList<Pet>>(){}.getType();
            pets = new Gson().fromJson(json, listType);
            new Gson().fromJson(json, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets;
    }

    public List<Pet> obterPetsUsuario() {
        List<Pet> pets = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cliente", String.valueOf(Contexto.pessoaLogada.id()));
        try {
            String json = this.apiConnection.sendGet("/pets", params);
            Type listType = new TypeToken<ArrayList<Pet>>(){}.getType();
            pets = new Gson().fromJson(json, listType);
            new Gson().fromJson(json, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pets;
    }

    public void salvarPet(Pet pet) {
        Map<String, String> params = new HashMap<>();
        if (pet.getId()!=null) {
            params.put("id", String.valueOf(pet.getId()));
        }
        if (isNotEmpty(pet.getNome())) {
            params.put("nome", pet.getNome());
        }
        if (isNotEmpty(pet.getIdade())) {
            params.put("idade", String.valueOf(pet.getIdade()));
        }
        if (isNotEmpty(pet.getRaca())) {
            params.put("raca", pet.getRaca());
        }
        if (isNotEmpty(pet.getObservacoes())) {
            params.put("observacoes", pet.getObservacoes());
        }
        params.put("cliente", String.valueOf(Contexto.pessoaLogada.id()));
        try {
            this.apiConnection.sendPost("/pets", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
