package br.com.ftec.julio.livros;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends Activity {

    EditText editTexTitulo;
    EditText editTextAutor;
    EditText editTextEditora;

    BancoDeDados crud;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button buttonCadastrar = (Button)findViewById(R.id.buttonCadastrar);
        Button buttonAlterar = (Button)findViewById(R.id.buttonAlterar);
        Button buttonDeletar = (Button)findViewById(R.id.buttonDeletar);
        editTexTitulo = (EditText) findViewById(R.id.editTexTitulo);
        editTextAutor = (EditText) findViewById((R.id.editTextAutor));
        editTextEditora = (EditText) findViewById(R.id.editTextEditora);

        crud = new BancoDeDados(getBaseContext());

        codigo = this.getIntent().getStringExtra("codigo");
        if (codigo == null){
            //está abrindo a tela direto do menu principal para cadastro

            //ajusta botões
            buttonCadastrar.setEnabled(true);
            buttonAlterar.setEnabled(false);
            buttonDeletar.setEnabled(false);

        }else {
            //está abrindo a tela depois da lista, então vem com um código

            //executa o método que devolve od registros de um CODIGO
            Cursor cursor = crud.carregarDadoById(Integer.parseInt(codigo));
            editTexTitulo.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.TITULO)));
            editTextAutor.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.AUTOR)));
            editTextEditora.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.EDITORA)));

            //ajusta botões
            buttonCadastrar.setEnabled(false);
            buttonAlterar.setEnabled(true);
            buttonDeletar.setEnabled(true);
        }


        //tratamento do botão CADASTRAR
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitulo = editTexTitulo.getText().toString();
                String sAutor = editTextAutor.getText().toString();
                String sEditora = editTextEditora.getText().toString();

                //executa método para inserir no banco
                boolean resultado = crud.inserirRegistro(sTitulo, sAutor, sEditora);

                if (resultado) {
                    //funcionou
                    Toast.makeText(getApplicationContext(), "Registro inserido com sucesso", Toast.LENGTH_LONG).show();
                    //limpar campos
                    editTexTitulo.setText("");
                    editTextAutor.setText("");
                    editTextEditora.setText("");
                }else{
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao inserir dados", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executa método para deletar o registro selecionado
                int count = crud.deletarRegistro(Integer.parseInt(codigo));
                if (count > 0) {
                    Toast.makeText(getApplicationContext(), "Registro deletado com sucesso", Toast.LENGTH_LONG).show();

                    //encerra esta activity e volta para a lista
                    Intent intent = new Intent(CadastroActivity.this, ConsultaActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //ocorreu algum erro
                    Toast.makeText(getApplicationContext(), "Erro ao deletar registro", Toast.LENGTH_LONG).show();
                }



            }
        });

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtem valores dos EditText
                String sTitulo = editTexTitulo.getText().toString();
                String sAutor = editTextAutor.getText().toString();
                String sEditora = editTextEditora.getText().toString();

                //executa método de alteração
                int count = crud.alterarRegistro(Integer.parseInt(codigo), sTitulo, sAutor, sEditora);

                if (count > 0) {
                    Toast.makeText(getApplicationContext(), "Registro alterado com sucesso", Toast.LENGTH_LONG).show();

                    //encerra esta activity e volta para a lista
                    Intent intent = new Intent(CadastroActivity.this, ConsultaActivity.class);
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