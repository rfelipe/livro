package br.com.ftec.revisao.felipe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;



public class MainActivity extends AppCompatActivity {
    int i;

    CheckBox checkBox;
    Boolean teste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        checkBox=(CheckBox)findViewById(R.id.checkBox);
        Button cadastro = (Button) findViewById(R.id.bcadastra);
        Button pesquisa = (Button) findViewById(R.id.bpesquisa);


        pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, consulta.class);
                    startActivity(intent);
               } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                           MainActivity.this).create();
                    alertDialog.setMessage("aceite os termos para proseguir");
                    alertDialog.show();
              }

            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                Intent intent = new Intent(MainActivity.this, cadastro.class);
                startActivity(intent);}

             else {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        MainActivity.this).create();
                alertDialog.setMessage("aceite os termos para proseguir");
                alertDialog.show();
            }

            }
        });
    }
}
