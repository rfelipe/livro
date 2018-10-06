package com.example.azoi.cadastrocarros;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class consulta extends AppCompatActivity {

    private ListView lista;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        BancoDeDados crud = new BancoDeDados(getBaseContext());
        cursor = crud.carregarDados();

        String[] nomeCampos = new String[] {BancoDeDados.ID, BancoDeDados.Modelo};
        int[] idViews = new int[] {R.id.idLivro, R.id.nomeLivro};


        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.linha_da_lista,cursor,nomeCampos,idViews, 0);
        lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        if (cursor.getCount() == 0){
            TextView textViewMensagem = (TextView) findViewById(R.id.textViewMensagem);
            textViewMensagem.setText("Nenhum registro cadastrado");
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //obtem o ID da posição e passa por parâmetro para a tela de cadastro exibir os dados desse registro
                cursor.moveToPosition(position);
                String codigo = cursor.getString(cursor.getColumnIndexOrThrow(BancoDeDados.ID));
                Intent intent = new Intent(consulta.this, cadastra.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                finish();
            }
        });
    }
}