package br.com.ftec.prova.felipe.economics;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox=(CheckBox)findViewById(R.id.checkBox);
        ImageButton bmais = (ImageButton) findViewById(R.id.bmais);
        ImageButton bpesquisa = (ImageButton) findViewById(R.id.bpesquisa);

        bmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, cadastro.class);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            MainActivity.this).create();
                    alertDialog.setMessage("Aceite os termos para proseguir");
                    alertDialog.show();
                }

            }
        });
        bpesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, consulta.class);
                    startActivity(intent);
            }
        });


    }
}
