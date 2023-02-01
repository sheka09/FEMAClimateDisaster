package io.fema.utility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import io.fema.entity.Disaster;
import io.fema.entity.DisasterSummaries;

public class Utility {

	public static HttpResponse<String> jsonParser(String url) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(url))
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		System.out.println(response.body());

		return response;
	}

	public static List<Disaster> jsonDeserializer(String response, Class<DisasterSummaries> myClass) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			return mapper.readValue(response, myClass).getDisasterDeclarationsSummaries();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * calculates the number of calls we will need to get all of the data (using the
	 * maximum of 1000)
	 */
	public static int countTotalRecords(String url) throws IOException, InterruptedException {

		HttpResponse<String> res = jsonParser(url);
		int numCalls = JsonPath.parse(res.body()).read("$.metadata.count", Integer.class);
		return numCalls;
	}

}
