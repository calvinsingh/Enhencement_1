/**
 * Class Name: ReportedAdapter
 *
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

public class ReportedAdapter  extends ArrayAdapter<ReportedDataHandler> {

	Context reported_context;
	int reported_resource;
	List<ReportedDataHandler> reportedDataHandlers;

	ReportedAdapter(Context reported_context, int reported_resource, List<ReportedDataHandler> list){
		super(reported_context, reported_resource, list);
		this.reported_context = reported_context;
		this.reported_resource = reported_resource;
		this.reportedDataHandlers = list;
	}

	public View getView(int position, View convertView, ViewGroup parent){

		LayoutInflater layoutInflater = LayoutInflater.from(reported_context);
		@SuppressLint("ViewHandler") View view = layoutInflater.inflate(reported_resource, null, false);

		ReportedDataHandler dataList = reportedDataHandlers.get(position);
		TextView textViewTitle = view.findViewById(R.id.tv_crime_title);
		TextView textViewLocation = view.findViewById(R.id.tv_crime_location);
		TextView textViewDescription = view.findViewById(R.id.tv_crime_description);
		TextView textViewCases = view.findViewById(R.id.tv_crime_cases);

		textViewTitle.setText(dataList.getTitle());
		Glide.with(reported_context).load(dataList.getTitle()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		textViewLocation.setText(dataList.getCounty());
		Glide.with(reported_context).load(dataList.getCounty()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		textViewDescription.setText(dataList.getDescription());
		Glide.with(reported_context).load(dataList.getDescription()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		textViewCases.setText(dataList.getEstate());
		Glide.with(reported_context).load(dataList.getEstate()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		return view;
	}
}
