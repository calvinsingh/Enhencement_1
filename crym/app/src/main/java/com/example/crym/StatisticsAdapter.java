/**
 * Class Name: StatisticsAdapter
 * Used for drawing and displaying statistical charts on the screen
 */


package com.example.crym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StatisticsAdapter extends ArrayAdapter<StatisticsDataHandler> {
	Context stat_context;
	int stat_resource;
	List<StatisticsDataHandler> statisticsDataHandlers;


	StatisticsAdapter(Context stat_context, int stat_resource, List<StatisticsDataHandler> list_stat){
		super(stat_context, stat_resource, list_stat);
		this.stat_context = stat_context;
		this.stat_resource = stat_resource;
		this.statisticsDataHandlers = list_stat;
	}
	//select each view
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater layoutInflater = LayoutInflater.from(stat_context);
		@SuppressLint("ViewHandler") View view = layoutInflater.inflate(stat_resource, null, false);

		StatisticsDataHandler dataList = statisticsDataHandlers.get(position);
		TextView county_name = view.findViewById(R.id.tv_county);
		TextView number_crime = view.findViewById(R.id.tv_number_crime);

		county_name.setText(dataList.getCounty_name());
		Glide.with(stat_context).load(dataList.getCounty_name()).centerCrop()
				.error(R.drawable.ic_close).placeholder(R.drawable.ic_search);

		number_crime.setText(dataList.getCrime_count());
		Glide.with(stat_context).load(dataList.getCrime_count()).centerCrop()
				.error(R.drawable.ic_close).placeholder(R.drawable.ic_search);

		return view;
	}
}
