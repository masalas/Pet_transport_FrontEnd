package com.example.raphael.pettransport.controller;

import com.example.raphael.pettransport.models.Pet;

import java.util.List;

public interface ControllerPet {
    void addPet(Pet pet);

    void deletePet(Pet pet);

    void atualizarPet(Pet pet);

    List<Pet> getPetsByRaio(int raio) throws Exception;

    List<Pet> getPets() throws Exception;

    List<Pet> getPetById(int id);
}
