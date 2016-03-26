package dk.cit.oossp.thymeleaf.service;

import java.util.ArrayList;
import java.util.List;

import dk.cit.oossp.thymeleaf.domain.Candidate;

public interface CandidateService {
	
	Candidate get(String firstName, String lastName);
	
	void savePoints(String firstName, String secondName, int score, String constituency);
	
	List<Candidate> findAll();
	
	List<String> getConstituencies();

	List<Candidate> getCandidatesByConstituency(String constituency);
	
	int getConstituencyCandidateCount(String constituency);
	
	public ArrayList<String> getResults(String constituency);
}
