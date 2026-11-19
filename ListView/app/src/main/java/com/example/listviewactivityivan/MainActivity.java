package com.example.listviewactivityivan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	static ArrayList<Contacte> contactes = new ArrayList<Contacte>();

	static {
		contactes.add(new Contacte("Ivan", "Cecilia Padilla", "C/ PeterLand", "676767676", "e.iceciliap@ies-sabadell.cat", "25/01/2005"));
		contactes.add(new Contacte("Tung", "Tung Sahur", "C/ Mi casita ", "123456789", "tungitung@gmail.com", "24/06/1676"));
		contactes.add(new Contacte("Tralalero", "Tralala", "C/ si ", "611000111", "tralalero@gmail.com", "13/02/1802"));
		contactes.add(new Contacte("Bombardiro", "Crocodilo", "hola 7", "622111222", "bombardiro@gmail.com", "01/04/1944"));
		contactes.add(new Contacte("Ballerina", "Cappuccina", "nooo", "633222333", "ballerina@gmail.com", "30/09/1999"));
		contactes.add(new Contacte("Lirili", "Larila", "Profe ponme un 10", "644333444", "lirili@gmail.com", "08/08/1555"));
		contactes.add(new Contacte("Brr Brr", "Patapim", "gracias", "655444555", "patapim@gmail.com", "17/11/1888"));
		contactes.add(new Contacte("Chimpanzini", "Bananini", "yupii", "666555666", "chimpanzini@gmail.com", "05/06/2001"));
		contactes.add(new Contacte("Trippi", "Troppi", "C/ wasa 2", "677666777", "trippi@gmail.com", "22/12/1777"));
		contactes.add(new Contacte("Frigo", "Camelo", "C/ mi cocina 15", "688777888", "frigo@gmail.com", "29/02/2000"));
	}
	AdaptadorContactes adaptador;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adaptador = new AdaptadorContactes(this);

		ListView lstOpciones = (ListView) findViewById(R.id.lstOpcions);
		lstOpciones.setAdapter(adaptador);

		lstOpciones.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				obrirContacte("view", contactes.get(arg2));
			}
		});

		findViewById(R.id.btnAfg).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				obrirContacte("new", null);
			}
		});
	}

	private void obrirContacte(String mode, Contacte c) {
		Intent intent = new Intent(this, ContacteActivity.class);
		intent.putExtra("mode", mode);
		if (c != null) {
			intent.putExtra("contacte", c);
		}
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != 1 || data == null) return;

		if (resultCode == RESULT_OK) {
			Contacte c = (Contacte) data.getSerializableExtra("contacte");
			guardar(c);
		} else if (resultCode == RESULT_FIRST_USER) {
			esborrar(data.getIntExtra("id", -1));
		}
	}

	private void guardar(Contacte nou) {
		boolean trobat = false;
		for (int i = 0; i < contactes.size(); i++) {
			if (contactes.get(i).getId() == nou.getId()) {
				contactes.set(i, nou);
				trobat = true;
				break;
			}
		}
		if (!trobat) {
			contactes.add(nou);
		}
		adaptador.notifyDataSetChanged();
	}

	private void esborrar(int id) {
		for (int i = 0; i < contactes.size(); i++) {
			if (contactes.get(i).getId() == id) {
				contactes.remove(i);
				break;
			}
		}
		adaptador.notifyDataSetChanged();
	}

	private void confirmarEsborrar(final Contacte c) {
		new AlertDialog.Builder(this)
				.setMessage(getString(R.string.confirmar_esborrar_msg, c.getNomComplet()))
				.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						esborrar(c.getId());
					}
				})
				.setNegativeButton(R.string.no, null)
				.show();
	}


	class AdaptadorContactes extends ArrayAdapter<Contacte> {

		Activity context;

		public AdaptadorContactes(Activity context) {
			super(context, R.layout.listitem_contacte, contactes);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item = convertView;
			if (item == null) {
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.listitem_contacte, parent, false);
			}

			final Contacte c = contactes.get(position);

			TextView lblNom = (TextView) item.findViewById(R.id.TxtNom);
			TextView lblTelf = (TextView) item.findViewById(R.id.TxtTelf);
			TextView lblMail = (TextView) item.findViewById(R.id.txtMail);

			lblNom.setText(c.getNomComplet());
			lblTelf.setText(c.getTelefon());
			lblMail.setText(c.getEmail());


			item.findViewById(R.id.butEdit).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					obrirContacte("edit", c);
				}
			});



			return item;
		}
	}
    
    
    
}
