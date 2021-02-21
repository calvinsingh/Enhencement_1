
/**
 * Class Name: ChartAdapter
 * Used by ChartActivity drawing and displaying statistical charts on the screen
 */

package com.example.crym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatDataHandler> {

	Context chat_context;
	int chat_resource;
	List<ChatDataHandler> dataChatHandlers;

	ChatAdapter(Context chat_context, int chat_resource, List<ChatDataHandler> chatlist) {
		super(chat_context, chat_resource, chatlist);
		this.chat_context = chat_context;
		this.chat_resource = chat_resource;
		this.dataChatHandlers = chatlist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(chat_context);
		@SuppressLint("ViewHandler") View view = layoutInflater.inflate(chat_resource, null, false);

		ChatDataHandler dataList = dataChatHandlers.get(position);
		TextView chat_user_time = view.findViewById(R.id.chat_user_time);
		TextView chat_content = view.findViewById(R.id.chat_content);

		chat_user_time.setText(dataList.getCreated_at());
		Glide.with(chat_context).load(dataList.getCreated_at()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		chat_content.setText(dataList.getComment());
		Glide.with(chat_context).load(dataList.getComment()).centerCrop()
				.error(R.drawable.no_image).placeholder(R.drawable.ic_loading);

		return view;
	}
}



