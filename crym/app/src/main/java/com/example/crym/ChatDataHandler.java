

/**
 * Class Name: ChartDataHandler
 * has data members for the charts
 */

package com.example.crym;
public class ChatDataHandler {
	String id, created_at, comment, update_at;

	ChatDataHandler(String id, String created_at, String comment, String updated_at){
		this.id = id;
		this.created_at = created_at;
		this.comment = comment;
		this.update_at = updated_at;
	}

	public String getId() {
		return id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getComment() {
		return comment;
	}

	public String getUpdate_at() {
		return update_at;
	}
}
