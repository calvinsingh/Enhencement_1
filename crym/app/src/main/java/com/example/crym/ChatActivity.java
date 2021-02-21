/**
 * Class Name: Chart Activity
 * Used for drawing and displaying statistical charts on the screen
 */

package com.example.crym;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class ChatActivity extends AppCompatActivity {

	ListView listChatView;
	List<ChatDataHandler> chatDataHandlerList;
	SwipeRefreshLayout swipeRefreshLayout;
	ChatAdapter chatAdapter;
	Button btn_send;
	EditText et_comment;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		setTitle("Share Your Views");

		listChatView = findViewById(R.id.list_chat);
		chatDataHandlerList = new ArrayList<>();
		swipeRefreshLayout = findViewById(R.id.swipe);
		//widgets
		btn_send = findViewById(R.id.btn_send);
		et_comment = findViewById(R.id.comment);

		//load data
		loadData("first");
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData("Refresh");
			}
		});
		btn_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//string value of variablews
				final String str_comment = et_comment.getText().toString();

				//check if empty
				if(str_comment.equals("")) {
					Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
					//End Read data from URL
				}else{
					//Starting Read data from URL
					Handler handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							//starting writing and reading with the URI
							String[] field = new String[1];
							field[0] = "comment";

							//creating array for data
							String[] data = new String[1];
							data[0] = str_comment;

							PutData putData = new PutData("http://192.168.43.177:8012/android/crymdb/insert_chat.php",
									"POST", field, data);

							if (putData.startPut()) {
								if (putData.onComplete()) {
									String result = putData.getResult();
									if(result.equals("Chat inserted")){
										Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
										Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
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

	private void loadData(String type){
		//refresh
		swipeRefreshLayout.setRefreshing(true);

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				"http://192.168.43.177:8012/android/crymdb/get_chat.php",
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
		String id, created_at, comment, updated_at;

		if(type.equals("Refresh")){
			chatDataHandlerList.clear();
			chatAdapter.notifyDataSetChanged();
		}
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i=0; i<jsonArray.length(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				id = jsonObject.get("id").toString();
				created_at = jsonObject.get("created_at").toString();
				comment = jsonObject.get("comment").toString();
				updated_at = jsonObject.get("updated_at").toString();
				chatDataHandlerList.add(new ChatDataHandler(id,  created_at,  comment,  updated_at));
			}
			chatAdapter = new ChatAdapter(getApplicationContext(), R.layout.chat_item,
					chatDataHandlerList);
			listChatView.setAdapter(chatAdapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//menu, toolbar
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.crime_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.nav_home){
			Intent homeIntent = new Intent(this, MainActivity.class);
			startActivity(homeIntent);
		}else if(id == R.id.nav_statistic){
			Intent statIntent = new Intent(getApplicationContext(), StatisticActivity.class);
			startActivity(statIntent);
		}
		return true;
	}
}
