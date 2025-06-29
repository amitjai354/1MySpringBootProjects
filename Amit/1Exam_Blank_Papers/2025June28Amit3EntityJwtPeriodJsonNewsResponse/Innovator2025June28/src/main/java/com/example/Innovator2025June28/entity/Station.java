package com.example.Innovator2025June28.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Station {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int station_Id;
	
	private String name;
	
	private String frequency;
	
	private String genre;
	
	private String language;
	
	private String country;
	
	private String streamingURL;
	
	private boolean isLive;
	
	private String startTime;
	
	private String endTime;
	
	private int operatorId;

	public Station() {
		super();
	}

	public Station(String name, String frequency, String genre, String language, String country, String streamingURL,
			boolean isLive, String startTime, String endTime, int operatorId) {
		super();
		this.name = name;
		this.frequency = frequency;
		this.genre = genre;
		this.language = language;
		this.country = country;
		this.streamingURL = streamingURL;
		this.isLive = isLive;
		this.startTime = startTime;
		this.endTime = endTime;
		this.operatorId = operatorId;
	}

	public int getStation_Id() {
		return station_Id;
	}

	public void setStation_Id(int station_Id) {
		this.station_Id = station_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreamingURL() {
		return streamingURL;
	}

	public void setStreamingURL(String streamingURL) {
		this.streamingURL = streamingURL;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		return "Station [station_Id=" + station_Id + ", name=" + name + ", frequency=" + frequency + ", genre=" + genre
				+ ", language=" + language + ", country=" + country + ", streamingURL=" + streamingURL + ", isLive="
				+ isLive + ", startTime=" + startTime + ", endTime=" + endTime + ", operatorId=" + operatorId + "]";
	}
	
	

}
