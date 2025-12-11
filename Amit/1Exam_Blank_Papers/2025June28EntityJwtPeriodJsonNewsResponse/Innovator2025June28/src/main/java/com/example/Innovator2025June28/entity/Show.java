package com.example.Innovator2025June28.entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int showId;
	
	private String title;
	
	private String description;
	
	private String showTime;
	
	private int duration;
	
	private String host;
	
	private String category;
	
	private int popularityRating;
	
	//Actual foreign key field (used in request and response)
	private int station_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="station_Id", insertable=false, updatable = false)
	@JsonIgnore
	private Station station;

	public Show() {
		super();
	}

	public Show(String title, String description, String showTime, int duration, String host, String category,
			int popularityRating, int station_id) {
		super();
		this.title = title;
		this.description = description;
		this.showTime = showTime;
		this.duration = duration;
		this.host = host;
		this.category = category;
		this.popularityRating = popularityRating;
		this.station_id = station_id;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPopularityRating() {
		return popularityRating;
	}

	public void setPopularityRating(int popularityRating) {
		this.popularityRating = popularityRating;
	}

	public int getStation_id() {
		return station_id;
	}

	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Show [showId=" + showId + ", title=" + title + ", description=" + description + ", showTime=" + showTime
				+ ", duration=" + duration + ", host=" + host + ", category=" + category + ", popularityRating="
				+ popularityRating + ", station_id=" + station_id + ", station=" + station + "]";
	}
	
	
	
}
