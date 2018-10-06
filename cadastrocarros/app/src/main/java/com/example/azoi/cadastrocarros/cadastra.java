package com.example.azoi.cadastrocarros;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cadastra extends Activity {

    EditText modelo;
    EditText ano;
    EditText placa;
    EditText km;
    EditText valor;

    String mmodelo;

    BancoDeDados crud;
    String codigo;
    //EditText modelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra);

        Button cadastra = (Button) findViewById(R.id.cadastro);
        Button altera = (Button) findViewById(R.id.altera);
        Button deletar = (Button) findViewById(R.id.deletar);
        modelo = (EditText) findViewById(R.id.modelo);
        ano = (EditText) findViewById(R.id.ano);
        placa = (EditText) findViewById(R.id.placa);
        km = (EditText) findViewById(R.id.km);
        valor = (EditText) findViewById(R.id.valor);

         crud = new BancoDeDados(getBaseContext());



        codigo = this.getIntent().getStringExtra("codigo");
        if (codigo == null) {
            //está abrindo a tela direto do menu principal para cadastro

            //ajusta botões
            cadastra.setEnabled(true);
            altera.setEnabled(false);
            deletar.setEnabled(false);

        } else {
            //está abrindo a tela depois da lista, então vem com um código

            //executa o método que devolve od registros de um CODIGO
            Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));
            modelo.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Modelo)));
            ano.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Ano)));
            placa.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Placa)));
            km.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Km)));
            valor.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Valor)));

            //ajusta botões
            cadastra.setEnabled(true);
            altera.setEnabled(true);
            deletar.setEnabled(true);
        }


        //tratamento do botão CADASTRAR
        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sModelo = modelo.getText().toString();
                String sAno = ano.getText().toString();
                String sPlaca = placa.getText().toString();
                String sKm = km.getText().toString();
                String sValor = valor.getText().toString();

                //executa método para inserir no banco
                boolean resultado = crud.inserirRegistro(sModelo, sAno, sPlaca, sKm, sValor);

                if (resultado) {
                    //funcionou
                    Toast.makeText(getApplicationContext(), "Registro inserido com sucesso", Toast.LENGTH_LONG).show();
                    //limpar campos
                    modelo.setText("");
                    ano.setText("");
                    placa.setText("");
                    km.setText("");
                    valor.setText("");
                } else {
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                }
            }
        });

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executa método para deletar o registro selecionado
                int count = crud.deletarRegistro(Integer.parseInt(codigo));
                if (count > 0) {
                    Toast.makeText(getApplicationContext(), "Registro deletado com sucesso", Toast.LENGTH_LONG).show();

                    //encerra esta activity e volta para a lista
                   Intent intent = new Intent(cadastra.this, consulta.class);
                    startActivity(intent);
                    finish();
                } else {
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao deletar registro", Toast.LENGTH_LONG).show();
                }


            }
        });

        altera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //obtem valores dos EditText
                String sModelo = modelo.getText().toString();
                String sAno = ano.getText().toString();
                String sPlaca = placa.getText().toString();
                String sKm = km.getText().toString();
                String sValor = valor.getText().toString();

                //executa método de alteração
                int count = crud.alterarRegistro(Integer.parseInt(codigo),sModelo, sAno, sPlaca, sKm, sValor);

                if (count > 0) {
                    Toast.makeText(getApplicationContext(), "Registro alterado com sucesso", Toast.LENGTH_LONG).show();

                    //encerra esta activity e volta para a lista
                    Intent intent = new Intent(cadastra.this, consulta.class);
                    startActivity(intent);
                    finish();
                }else{
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao alterar registro", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}



