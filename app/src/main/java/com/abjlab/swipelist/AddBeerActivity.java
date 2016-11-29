package com.abjlab.swipelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddBeerActivity extends AppCompatActivity {

    EditText tfNombre;
    EditText tfDesc;
    Button btnAdd;
    RadioGroup rbGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        tfNombre = (EditText) findViewById(R.id.tfNombre);
        tfDesc = (EditText) findViewById(R.id.tfDesc);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        rbGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultado = new Intent(AddBeerActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("nombre", tfNombre.getText().toString());
                b.putString("desc", tfNombre.getText().toString());
                int icono;
                switch (rbGroup.getCheckedRadioButtonId()){
                    case R.id.rbBeer:
                        icono = R.drawable.beer;
                        break;
                    case R.id.rbRun:
                        icono = R.drawable.food;
                        break;
                    default:
                        icono = R.drawable.run;
                }
                b.putInt("icono", icono);
                resultado.putExtras(b);
                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
