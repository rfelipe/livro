package br.com.ftec.prova.felipe.economics;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cadastro extends AppCompatActivity {
    EditText snome,spessoa,sdata,sconta,svalor;
    BancoDeDados crud;
    String codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button cadastro = (Button) findViewById(R.id.cad);
        Button pesquisa = (Button) findViewById(R.id.alt);
        Button deletar = (Button) findViewById(R.id.del);
        snome=(EditText) findViewById(R.id.idnome);
        spessoa=(EditText) findViewById(R.id.idpessoa);
        sdata=(EditText) findViewById(R.id.iddata);
        sconta=(EditText) findViewById(R.id.idconta);
        svalor=(EditText) findViewById(R.id.idvalor);
        crud = new BancoDeDados(getBaseContext());



        codigo = this.getIntent().getStringExtra("codigo");
        if (codigo == null) {
            //está abrindo a tela direto do menu principal para cadastro

            //ajusta botões
            cadastro.setEnabled(true);
            pesquisa.setEnabled(false);
            deletar.setEnabled(false);

        } else {
            //está abrindo a tela depois da lista, então vem com um código

            //executa o método que devolve od registros de um CODIGO
            Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));
            snome.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Nome)));
            spessoa.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Pessoa)));
            sdata.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Data)));
            sconta.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Conta)));
            svalor.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.Valor)));

            //ajusta botões
            cadastro.setEnabled(false);
            pesquisa.setEnabled(true);
            deletar.setEnabled(true);
        }


        //tratamento do botão CADASTRAR
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNome = snome.getText().toString();
                String sPessoa = spessoa.getText().toString();
                String sData = sdata.getText().toString();
                String sConta = sconta.getText().toString();
                String sValor = svalor.getText().toString();

                if ((sNome.length() > 0) && (sPessoa.length() > 0)&& (sData.length() > 0)&& (sConta.length() > 0)&& (sValor.length() > 0)) {
                    //executa método para inserir no banco
                    boolean resultado = crud.inserirRegistro(sNome, sPessoa, sData, sConta, sValor);

                    if (resultado) {
                        //funcionou
                        Toast.makeText(getApplicationContext(), "Registro inserido com sucesso", Toast.LENGTH_LONG).show();
                        //limpar campos
                        snome.setText("");
                        spessoa.setText("");
                        sdata.setText("");
                        sconta.setText("");
                        svalor.setText("");
                        sNome=null;
                        sPessoa=null;
                        sData=null;
                        sConta=null;
                        sValor=null;


                    } else {
                        //ocorreu algum erro
                        Toast.makeText(getApplicationContext(), "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            cadastro.this).create();
                    alertDialog.setMessage("Todos os campos devem ser preenchidos!");
                    alertDialog.show();
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
                    Intent intent = new Intent(cadastro.this, consulta.class);
                    startActivity(intent);
                    finish();
                } else {
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao deletar registro", Toast.LENGTH_LONG).show();
                }


            }
        });

        pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtem valores dos EditText
                String sNome = snome.getText().toString();
                String sPessoa = spessoa.getText().toString();
                String sData = sdata.getText().toString();
                String sConta = sconta.getText().toString();
                String sValor = svalor.getText().toString();


                //executa método de alteração
                int count = crud.alterarRegistro(Integer.parseInt(codigo), sNome, sPessoa, sData, sConta, sValor);

                if (count > 0) {
                    Toast.makeText(getApplicationContext(), "Registro alterado com sucesso", Toast.LENGTH_LONG).show();

                    //encerra esta activity e volta para a lista
                    Intent intent = new Intent(cadastro.this, consulta.class);
                    startActivity(intent);
                    finish();
                } else {
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao alterar registro", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}