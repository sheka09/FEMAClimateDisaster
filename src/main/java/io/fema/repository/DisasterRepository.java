package io.fema.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.fema.entity.Disaster;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster, Long> {

	List<Disaster> findByIncidentType(String incidentType);

	List<Disaster> findByincidentBeginDate(LocalDate date);

	@Query("select count(distinct d.disasterNumber) from disaster d where d.state=:state and d.incidentType=:incidentType and d.incidentBeginDate>=:incidentBeginDate and d.incidentBeginDate<=:incidentEndDate")
	int countDisasterPerPeriod(String incidentType, String state, LocalDate incidentBeginDate,
			LocalDate incidentEndDate);

}
