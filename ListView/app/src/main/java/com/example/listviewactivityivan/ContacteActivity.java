package com.example.listviewactivityivan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContacteActivity extends AppCompatActivity {
    EditText edNom, edCognom, edTelf, edCorreu, edDire, edNaix;
    Button btnAcept, btnCanc, btnDel;
    String mode;
    Contacte original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacte);

        edNom = (EditText) findViewById(R.id.edNom);
        edCognom = (EditText) findViewById(R.id.edCognom);
        edTelf = (EditText) findViewById(R.id.edTelf);
        edCorreu = (EditText) findViewById(R.id.edCorreu);
        edDire = (EditText) findViewById(R.id.edDire);
        edNaix = (EditText) findViewById(R.id.edNaix);
        btnAcept = (Button) findViewById(R.id.btnAcept);
        btnCanc = (Button) findViewById(R.id.btnCanc);
        btnDel = (Button) findViewById(R.id.btnDel);
        TextView txtTitol = (TextView) findViewById(R.id.txtTitol);

        //aqui pillamos el intento
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "new";
        original = (Contacte) getIntent().getSerializableExtra("contacte");

        if (original != null) {
            edNom.setText(original.getNom());
            edCognom.setText(original.getCognoms());
            edTelf.setText(original.getTelefon());
            edCorreu.setText(original.getEmail());
            edDire.setText(original.getAdreca());
            edNaix.setText(original.getDataNaixement());
        }

        // Dependiendo si es nuevo o no
        if (mode.equals("view")) {
            txtTitol.setText(R.string.titol_detall);
            edNom.setEnabled(false);
            edCognom.setEnabled(false);
            edTelf.setEnabled(false);
            edCorreu.setEnabled(false);
            edDire.setEnabled(false);
            edNaix.setEnabled(false);
            btnCanc.setVisibility(View.GONE);
            btnAcept.setText(R.string.btn_tancar);
        } else if (mode.equals("edit")) {
            txtTitol.setText(R.string.titol_editar);
        } else {
            txtTitol.setText(R.string.titol_nou);
            btnDel.setVisibility(View.GONE);
        }

        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptar();
            }
        });

        btnCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEsborrar();
            }
        });
    }

    private void acceptar() {
        if (mode.equals("view")) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        String nom = edNom.getText().toString().trim();
        if (nom.isEmpty()) {
            edNom.setError(getString(R.string.error_nom_buit));
            return;
        }
        String cognoms = edCognom.getText().toString().trim();
        String telefon = edTelf.getText().toString().trim();
        String correu = edCorreu.getText().toString().trim();
        String adreca = edDire.getText().toString().trim();
        String naixement = edNaix.getText().toString().trim();

        Contacte resultat;
        if (original != null) {
            resultat = new Contacte(original.getId(), nom, cognoms, adreca, telefon, correu, naixement);
        } else {
            resultat = new Contacte(nom, cognoms, adreca, telefon, correu, naixement);
        }

        Intent data = new Intent();
        data.putExtra("contacte", resultat);
        setResult(RESULT_OK, data);
        finish();
    }

    private void confirmarEsborrar() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirmar_esborrar_msg, original.getNomComplet()))
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        data.putExtra("id", original.getId());
                        setResult(RESULT_FIRST_USER, data);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

}
