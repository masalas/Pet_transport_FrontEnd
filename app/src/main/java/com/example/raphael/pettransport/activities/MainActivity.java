package com.example.raphael.pettransport.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raphael.pettransport.R;
import com.example.raphael.pettransport.models.ChamadoJson;
import com.example.raphael.pettransport.models.Contexto;
import com.example.raphael.pettransport.models.PessoaJson;
import com.example.raphael.pettransport.models.TipoPessoa;
import com.example.raphael.pettransport.service.ChamadoService;
import com.example.raphael.pettransport.service.UsuarioService;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private UsuarioService usuarioService;


    public MainActivity() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button botaoBuscar = findViewById(R.id.botaobuscar);
        final Button botaoSelecao = findViewById(R.id.botaoSelecao);
        final Button botaoCadastrarPet =findViewById(R.id.btnCadastrarPet);
        final Button botaoChamados =findViewById(R.id.btnChamados);
        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaPets();
            }
        });
        botaoSelecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaSelecao();
            }

        });
        botaoCadastrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastroPets();
            }
        });
        botaoChamados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaChamados();
            }
        });
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                TextView username = findViewById(R.id.usernameText);
                TextView password = findViewById(R.id.passwordText);
                PessoaJson login = usuarioService.login(String.valueOf(username.getText()), String.valueOf(password.getText()));
                if (login != null) {
                    Contexto.pessoaLogada = login;
                    Button botaoVisivel = botaoBuscar;
                    if (login.tipoPessoa().equals(TipoPessoa.CLIENTE)) {
                        botaoVisivel = botaoSelecao;
                        botaoCadastrarPet.setVisibility(View.VISIBLE);
                    }
                    botaoVisivel.setVisibility(View.VISIBLE);
                    botaoChamados.setVisibility(View.VISIBLE);


                    ConstraintLayout loginContainer = findViewById(R.id.loginContainer);
                    loginContainer.setVisibility(View.INVISIBLE);
                }else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Aviso");
                    alerta.setIcon(R.mipmap.ic_launcher);
                    alerta.setMessage("USUARIO NÃO EXISTENTE OU SENHA INCORRETA!");
                    alerta.setCancelable(false);
                    alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }
                    );
                    AlertDialog alertDialog = alerta.create();
                    alertDialog.show();


                }
            }

        });

    }

    private void abrirTelaChamados() {
        Intent intent = new Intent(this, ActivityChamados.class);
        startActivity(intent);
    }

    public void abrirTelaCadastroPets() {
        Intent intent = new Intent(this, ActivityCadastroPet.class);
        startActivity(intent);
    }

    public void abrirTelaSelecao() {
        Intent intent = new Intent(this, SelecaoActivity.class);
        startActivity(intent);
    }

    public void abrirTelaPets() {
        Intent intent = new Intent(this, ActivityMapa.class);
        startActivity(intent);
    }

    public void abrirTelaCadastroUsuario(View view) {
        Intent intent = new Intent(this, ActivityCadastroUsuario.class);
        startActivity(intent);
    }
}
