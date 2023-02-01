package io.fema.entity;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityScan
@Entity(name = "disaster")
public class Disaster {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@JsonProperty("disasterNumber")
	private Long disasterNumber;

	@JsonProperty("incidentBeginDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate incidentBeginDate;

	@JsonProperty("incidentEndDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate incidentEndDate;

	@JsonProperty("state")
	private String state;
	@JsonProperty("incidentType")
	private String incidentType;

	void setIncidentBeginDate(String startDate) {
		if (startDate != null) {
			this.incidentBeginDate = LocalDate.parse(startDate.subSequence(0, 10));
		}

	}

	void setIncidentEndDate(String endDate) {
		if (endDate != null) {

			this.incidentEndDate = LocalDate.parse(endDate.subSequence(0, 10));
		}

	}

}
