
/**
 * Class Name: Main Activity
 *
 */

package com.example.crym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	ListView listView;
	List<ReportedDataHandler> reportedDataHandlerList;
	SwipeRefreshLayout swipeRefreshLayout;
	ReportedAdapter reportedAdapter;
	FloatingActionButton btnReportActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("All Reported Crimes");

		//add report intent
		btnReportActivity = findViewById(R.id.btn_add_report);

		btnReportActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AddReportActivity.class);
				startActivity(intent);
			}
		});

		listView = findViewById(R.id.list);
		reportedDataHandlerList = new ArrayList<>();
		swipeRefreshLayout = findViewById(R.id.swipe);

		//load data
		loadData("first");
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData("Refresh");
			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				String title, location;
				title = reportedDataHandlerList.get(position).getTitle();
				location = reportedDataHandlerList.get(position).getCounty();
			}
		});
	}



	private void loadData(String type){
		//refresh
		swipeRefreshLayout.setRefreshing(true);

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				"http://192.168.43.177:8012/android/crymdb/get_reported.php",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						swipeRefreshLayout.setRefreshing(false);
						parseJSON(response, type);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(), error.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		});
		queue.add(stringRequest);
	}
	private void parseJSON(String res, String type){
		String id, Title, county, description, estate, Created, updated_at;
		if(type.equals("Refresh")){
			reportedDataHandlerList.clear();
			reportedAdapter.notifyDataSetChanged();
		}
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				id = jsonObject.get("id").toString();
				Title = jsonObject.get("title").toString();
				county = jsonObject.get("county").toString();
				description = jsonObject.get("description").toString();
				estate = jsonObject.get("estate").toString();
				Created = jsonObject.get("created_at").toString();
				updated_at = jsonObject.get("updated_at").toString();

				reportedDataHandlerList.add(new ReportedDataHandler(id, Title, county, description, estate, Created, updated_at));
			}
			reportedAdapter = new ReportedAdapter(getApplicationContext(), R.layout.crime_item,
					reportedDataHandlerList);
			listView.setAdapter(reportedAdapter);

		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();

		}
	}

	//menu, toolbar
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.nav_chat){
			Intent chatIntent = new Intent(this, ChatActivity.class);
			startActivity(chatIntent);
		}else if(id == R.id.nav_statistic){
			Intent statIntent = new Intent(getApplicationContext(), StatisticActivity.class);
			startActivity(statIntent);
		}
		return true;
	}
}