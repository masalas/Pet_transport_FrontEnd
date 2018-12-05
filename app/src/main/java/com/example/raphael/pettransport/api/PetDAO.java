package com.example.raphael.pettransport.api;

import com.example.raphael.pettransport.models.Pet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PetDAO extends ApiConnection{

    public List<Pet> getPets() throws Exception {
        JSONArray json  = this.toJSON(this.sendGet(URL_BASE+"/pets"));
        return jsonToPets(json);
    }

    public List<Pet> getPetsByRaio(int raio) throws Exception {
        JSONArray json  = this.toJSON(this.sendGet(URL_BASE+"/pets/"+raio));
        return jsonToPets(json);
    }

    private List<Pet> jsonToPets(JSONArray json) throws JSONException {
        List<Pet> pets = new ArrayList<>();
        for(int i=0; i<json.length(); i++) {
            JSONObject content = (JSONObject) json.get(i);
            Pet pet = new Pet();
            pet.setNome(content.getString("pet"));
            pet.setIdade(content.getInt("idade"));
            pet.setRaca(content.getString("raca"));
            /*pet.setLat(content.getDouble("lat"));
            pet.setLon(content.getDouble("long"));*/
            pets.add(pet);
        }
        return pets;
    }

}
