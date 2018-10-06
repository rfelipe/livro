package com.example.azoi.cadastrocarros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String campo,vproduto;
    EditText produto;
    RadioButton r1,r2,r3,r4,r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cadastro = (Button) findViewById(R.id.cadastro);
        Button consulta = (Button) findViewById(R.id.consulta);
        Button vai= (Button) findViewById(R.id.vai);
        produto=(EditText) findViewById(R.id.produto);
         r1=(RadioButton)findViewById(R.id.r1);
         r2=(RadioButton)findViewById(R.id.r2);
         r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);
        r5=(RadioButton)findViewById(R.id.r5);



        cadastro.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, cadastra.class);
                 startActivity(intent);

             }
         });

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, consulta.class);
                startActivity(intent);
            }
        });

        vai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (r1.isChecked()){
                    campo = "modelo";
                }

                if (r2.isChecked()){
                    campo = "ano";
                }
                if (r3.isChecked()){
                    campo = "placa";
                }
                if (r4.isChecked()){
                    campo = "km";
                }

                if (r5.isChecked()){
                    campo = "valor";
                }
                Intent intent = new Intent(v.getContext(), select.class);
                Bundle params = new Bundle();
                String tproduto = produto.getText().toString();
                params.putString("tproduto", tproduto);
                String tcampo = campo;
                params.putString("tcampo", tcampo);
                intent.putExtras(params);
                startActivity(intent);
            }
        });
    }
}
