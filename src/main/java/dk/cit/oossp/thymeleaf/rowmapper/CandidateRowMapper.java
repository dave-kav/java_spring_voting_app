package dk.cit.oossp.thymeleaf.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dk.cit.oossp.thymeleaf.domain.Candidate;

public class CandidateRowMapper implements RowMapper<Candidate> {

	@Override
	public Candidate mapRow(ResultSet rs, int index) throws SQLException {
		Candidate candidate = new Candidate();
		
		candidate.setFirstName(rs.getString("First_name"));
		candidate.setSecondName(rs.getString("Second_name"));
		candidate.setParty(rs.getString("Party"));
		candidate.setConstituency(rs.getString("Constituency"));
		return candidate;
	}

}
