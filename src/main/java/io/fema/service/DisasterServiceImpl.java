package io.fema.service;


import java.io.IOException;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.fema.entity.Disaster;
import io.fema.entity.DisasterSummaries;
import io.fema.error.DisasterNotFoundException;
import io.fema.repository.DisasterRepository;
import io.fema.utility.Utility;


@Service
public class DisasterServiceImpl implements DisasterService {

	private String url = "https://www.fema.gov/api/open/v2/DisasterDeclarationsSummaries?$select=disasterNumber,incidentBeginDate,incidentEndDate,state,incidentType";

	private DisasterRepository disasterRepository;

	@Autowired
	public DisasterServiceImpl(DisasterRepository disasterRepository) {
		this.disasterRepository = disasterRepository;
	}

	/*
	 * retrieves data from FEMA disaster API and saves it to DisasterRepository
	 */
	@Override
	public List<Disaster> retrieveDisasterData() throws IOException, InterruptedException {

		List<Disaster> disasterList = null;
		int skip = 0;
		int top = 1000;

		int paging = 0;
		boolean hasRecord = true;
		while (hasRecord) {
			HttpResponse<String> response = Utility.jsonParser(url + "&$metadata=off&$skip=" + skip + "&$top=" + top);
			System.out.println("let see ");
			disasterList = Utility.jsonDeserializer(response.body(), DisasterSummaries.class);
			if (disasterList.isEmpty()) {
				hasRecord = false;
			}
			System.out.println("list is: " + disasterList);
			disasterRepository.saveAll(disasterList);
			paging++;
			skip = paging * top;

		}
		return disasterList;
	}

	@Override
	public List<Disaster> filterByDates(LocalDate date) throws DisasterNotFoundException {
		// TODO Auto-generated method stub
		return disasterRepository.findByincidentBeginDate(date);
	}

	@Override
	public Map<String, Integer> filterByDisasterTypeAndDate(String incidentType, LocalDate incidentBeginDate,
			LocalDate incidentEndDate) throws DisasterNotFoundException {
		List<String> usStates = new ArrayList<String>();

		Map<String, Integer> incidentFrequency = new HashMap<String, Integer>();
		Collections.addAll(usStates, "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL",
				"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NH", "NJ", "NM",
				"NV", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA",
				"WV", "WI", "WY");
		for (String state : usStates) {
			int frequency = disasterFrequency(incidentType, state, incidentBeginDate, incidentEndDate);
			if (frequency > 0) {
				incidentFrequency.put(state, frequency);
			}
		}
		System.out.println("max value is: " + Collections.max(incidentFrequency.values()));
		System.out.println("min value is: " + Collections.min(incidentFrequency.values()));
		return incidentFrequency;
	}

	@Override
	public int disasterFrequency(String incidentType, String state, LocalDate incidentBeginDate,
			LocalDate incidentEndDate) {
		return disasterRepository.countDisasterPerPeriod(incidentType, state, incidentBeginDate, incidentEndDate);
	}

}

