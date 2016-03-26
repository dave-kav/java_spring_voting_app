package dk.cit.oossp.thymeleaf.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dk.cit.oossp.thymeleaf.domain.Candidate;

public interface CandidateRepository {
	
	Candidate get(String firstName, String lastName);
	
	public List<Map<String, Object>> getFromVotesTable(String firstName, String lastName);
	
	void savePoints(String firstName, String secondName, int score, String constituency);

	List<Candidate> findAll();
	
	List<String> getConstituencies();
	
	List<Candidate> getCandidatesByConstituency(String constituency);
	
	int getConstituencyCandidateCount(String constituency);

	public ArrayList<String> getResults(String constituency);
}
