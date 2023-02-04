package io.fema.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import io.fema.entity.Disaster;
import io.fema.error.DisasterNotFoundException;
import io.fema.service.DisasterService;

@RestController
public class DisasterController {

	@Autowired
	private DisasterService disasterService;

	@GetMapping("/retrieve")
	public ResponseEntity<List<Disaster>> getDisasterList() throws IOException, InterruptedException {
		return new ResponseEntity<List<Disaster>>(disasterService.retrieveDisasterData(), HttpStatus.OK);
	}

	@GetMapping("/incidents/{incident}/{startDate}/{endDate}")
	public ResponseEntity<?> findDisasterByType(@PathVariable("incident") String incidentType,

			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate incidentBeginDate,

			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate incidentEndDate)
			throws DisasterNotFoundException {
		try {
			return new ResponseEntity<Map<String, Integer>>(
					disasterService.filterByDisasterTypeAndDate(incidentType, incidentBeginDate, incidentEndDate),
					HttpStatus.OK);
		} catch (DisasterNotFoundException disasterNotFoundException) {
			return new ResponseEntity<>(disasterNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/dates/{date}")
	public ResponseEntity<?> findDisasterByDate(
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
			throws DisasterNotFoundException {
		try {
			return new ResponseEntity<List<Disaster>>(disasterService.filterByDates(date), HttpStatus.OK);
		} catch (DisasterNotFoundException disasterNotFoundException) {
			return new ResponseEntity<>(disasterNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}