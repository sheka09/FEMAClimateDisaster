package io.fema.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.fema.entity.Disaster;
import io.fema.error.DisasterNotFoundException;

import java.time.LocalDate;

@Service
public interface DisasterService {

	public List<Disaster> retrieveDisasterData() throws IOException, InterruptedException;

	public List<Disaster> filterByDates(LocalDate date) throws DisasterNotFoundException;

	public Map<String, Integer> filterByDisasterTypeAndDate(String incidentType, LocalDate incidentBeginDate,
			LocalDate incidentEndDate) throws DisasterNotFoundException;

	public int disasterFrequency(String incidentType, String state, LocalDate incidentBeginDate,
			LocalDate incidentEndDate);

}
