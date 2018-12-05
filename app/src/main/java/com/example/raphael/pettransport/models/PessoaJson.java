package com.example.raphael.pettransport.models;

import java.io.Serializable;

public class PessoaJson implements Serializable {

    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String username;
    private String password;
    private TipoPessoa tipoPessoa;

    public Long id() {
        return id;
    }

    public PessoaJson id(Long id) {
        this.id = id;
        return this;
    }

    public String nome() {
        return nome;
    }

    public PessoaJson nome(String nome) {
        this.nome = nome;
        return this;
    }

    public String cpf() {
        return cpf;
    }

    public PessoaJson cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String dataNascimento() {
        return dataNascimento;
    }

    public PessoaJson dataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public String username() {
        return username;
    }

    public PessoaJson username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public PessoaJson password(String password) {
        this.password = password;
        return this;
    }

    public TipoPessoa tipoPessoa() {
        return tipoPessoa;
    }

    public PessoaJson tipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        return this;
    }
}
