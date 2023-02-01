package io.fema.entity;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
@EntityScan
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "DisasterDeclarationsSummaries")
public class DisasterSummaries {

	@JsonProperty("DisasterDeclarationsSummaries")
	private List<Disaster> disasters;

	public List<Disaster> getDisasterDeclarationsSummaries() {
		return disasters;
	}

}