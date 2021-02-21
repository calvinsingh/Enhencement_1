

package com.example.crym;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddReportActivity  extends AppCompatActivity {
	EditText et_title, et_description, et_estate;
	Spinner spin_county;
	Button btnReportCrime;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_crime);
		setTitle("Voice Your Claim");

		et_title = findViewById(R.id.et_crime_title);
		spin_county = findViewById(R.id.counties_spinner);
		et_description = findViewById(R.id.et_crime_description);
		et_estate = findViewById(R.id.et_crime_location);
		btnReportCrime = findViewById(R.id.btn_add_crime);


		spin_county = (Spinner) findViewById(R.id.counties_spinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.kenya_counties, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spin_county.setAdapter(adapter);

		btnReportCrime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final String title, description, estate, county;
				title = String.valueOf(et_title.getText());
				description = String.valueOf(et_description.getText());
				estate = String.valueOf(et_estate.getText());
				county = spin_county.getSelectedItem().toString();


				if(title.equals("") && description.equals("") && estate.equals("") && county.equals("")) {
					Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();

				}else{

					Handler handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							//starting writing and reading with the URI
							String[] field = new String[4];
							field[0] = "title";
							field[1] = "county";
							field[2] = "crime_description";
							field[3] = "estate";

							//creating array for data
							String[] data = new String[4];
							data[0] = title;
							data[1] = county;
							data[2] = description;
							data[3] = estate;

							PutData putData = new PutData(
									"http://192.168.43.177:8012/android/crymdb/insert_report.php",
									"POST", field, data);

							if (putData.startPut()) {
								if (putData.onComplete()) {
									String result = putData.getResult();
									if(result.equals("Crime reported successfully")){
										Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
										Intent intent =new Intent(getApplicationContext(), MainActivity.class);
										startActivity(intent);
										finish();
									}else{
										Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
									}
								}
							}
						}
					});
				}
			}
		});
	}
}
