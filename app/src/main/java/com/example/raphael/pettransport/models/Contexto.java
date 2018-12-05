package com.example.raphael.pettransport.models;

import java.util.List;

public class Contexto {
    public static PessoaJson pessoaLogada;
    public static ChamadoJson chamado;
    public static List<ChamadoJson> chamados;

    public static boolean isNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean isNotEmpty(Integer s) {
        return s != null && s!=0;
    }
}
