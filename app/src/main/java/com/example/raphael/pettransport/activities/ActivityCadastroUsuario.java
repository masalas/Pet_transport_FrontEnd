package com.example.raphael.pettransport.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.PessoaJson;
import com.example.raphael.pettransport.models.TipoPessoa;
import com.example.raphael.pettransport.service.UsuarioService;

public class ActivityCadastroUsuario extends Activity {

    private UsuarioService usuarioService;

    public ActivityCadastroUsuario() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrousuario);
        findViewById(R.id.btnCadastrarPessoaNovo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PessoaJson pessoaJson = new PessoaJson()
                        .nome(String.valueOf(((TextView) findViewById(R.id.textNomeUsuario)).getText()))
                        .dataNascimento(String.valueOf(((TextView) findViewById(R.id.textDataNascimento)).getText()))
                        .cpf(String.valueOf(((TextView) findViewById(R.id.textCpf)).getText()))
                        .username(String.valueOf(((TextView) findViewById(R.id.textUsername)).getText()))
                        .password(String.valueOf(((TextView) findViewById(R.id.textPassword)).getText()));
                if (((CheckBox) findViewById(R.id.checkBoxCliente)).isChecked()){
                    pessoaJson.tipoPessoa(TipoPessoa.CLIENTE);
                } else {
                    pessoaJson.tipoPessoa(TipoPessoa.MOTORISTA);
                }
                usuarioService.salvarPessoa(pessoaJson);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityCadastroUsuario.this);
                alerta.setTitle("Aviso");
                alerta.setIcon(R.mipmap.ic_launcher);
                alerta.setMessage("Cadastrado com Sucesso!");
                alerta.setCancelable(false);
                alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }
                );
                AlertDialog alertDialog = alerta.create();
                alertDialog.show();
            }
        });
    }
}


