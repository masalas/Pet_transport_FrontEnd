package com.example.raphael.pettransport.impl;

import com.example.raphael.pettransport.api.PetDAO;
import com.example.raphael.pettransport.controller.ControllerPet;
import com.example.raphael.pettransport.models.Pet;

import java.util.List;

public class ControllerPetImpl implements ControllerPet {

    private PetDAO dao;

    public ControllerPetImpl(){
        dao = new PetDAO();
    }
    @Override
    public void addPet(Pet pet) {

    }

    @Override
    public void deletePet(Pet pet) {

    }

    @Override
    public void atualizarPet(Pet pet) {

    }

    @Override
    public List<Pet> getPetsByRaio(int raio) throws Exception {
        return dao.getPetsByRaio(raio);
    }

    @Override
    public List<Pet> getPets() throws Exception {
        return dao.getPets();
    }

    @Override
    public List<Pet> getPetById(int id) {
        return null;
    }
}
