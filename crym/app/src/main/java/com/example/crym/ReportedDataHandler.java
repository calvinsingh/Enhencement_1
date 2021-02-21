

/**
 * Class Name: ReportedDataHandler
 * Used for drawing and displaying statistical charts on the screen
 */

package com.example.crym;

public class ReportedDataHandler {
	String id, title, county, description, estate, created_at, updated_at;

	ReportedDataHandler(String id, String title, String county, String description, String estate, String created_at, String updated_at){
		this.id = id;
		this.title = title;
		this.county = county;
		this.description = description;
		this.estate = estate;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getCounty() {
		return county;
	}

	public String getDescription() {
		return description;
	}

	public String getEstate() {
		return estate;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}
}
