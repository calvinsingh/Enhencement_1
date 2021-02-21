/**
 * Class Name: StatisticsDataHandler
 * Used for drawing and displaying statistical charts on the screen
 */


package com.example.crym;

public class StatisticsDataHandler {
	String county_name, crime_count;

	StatisticsDataHandler(String county_name, String crime_count){
		this.county_name = county_name;
		this.crime_count = crime_count;
	}


	/**
	 *
	 * @return
	 */
	public String getCrime_count() {
		return crime_count;
	}


	/**
	 *
	 * @return
	 */
	public String getCounty_name() {
		return county_name;
	}
}
