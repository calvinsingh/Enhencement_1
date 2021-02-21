
/**
 * Class Name: StatisticsActivity
 *
 */

package com.example.crym;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
	ListView listStatView;
	SwipeRefreshLayout swipeRefreshLayout;

	List<StatisticsDataHandler> statisticsDataHandlers;
	StatisticsAdapter statisticsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_home);
		setTitle("County Crime Statistics");
		listStatView = findViewById(R.id.listStat);
		swipeRefreshLayout = findViewById(R.id.swipe);
		statisticsDataHandlers = new ArrayList<>();

		//load data
		loadData( "first");

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData("Refresh");
			}
		});
	}


	private void loadData(String type){
		//refresh
		swipeRefreshLayout.setRefreshing(true);

		//fetching json objects from the php file getAppointments.php
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				"http://192.168.43.177:8012/android/crymdb/get_county.php",
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
		String county_name, crime_count;

		if(type.equals("Refresh")){
			statisticsDataHandlers.clear();
			statisticsAdapter.notifyDataSetChanged();
		}
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				county_name = jsonObject.get("name").toString();
				crime_count = jsonObject.get("crime_count").toString();
				statisticsDataHandlers.add(new StatisticsDataHandler(county_name, crime_count));
			}
			statisticsAdapter = new StatisticsAdapter(getApplicationContext(), R.layout.stat_item,
					statisticsDataHandlers);
			listStatView.setAdapter(statisticsAdapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
