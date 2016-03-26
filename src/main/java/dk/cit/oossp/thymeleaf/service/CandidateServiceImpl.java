package dk.cit.oossp.thymeleaf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.cit.oossp.thymeleaf.domain.Candidate;
import dk.cit.oossp.thymeleaf.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {
	
	CandidateRepository candidateRepository;
	
	@Autowired
	public CandidateServiceImpl(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}
	
	@Override
	public Candidate get(String firstName, String lastName) {
		return candidateRepository.get(firstName, lastName);
	}
	
	public List<Map<String, Object>> getFromVotesTable(String firstName, String lastName) {
		return candidateRepository.getFromVotesTable(firstName, lastName);
	}

	@Override
	public void savePoints(String firstName, String secondName, int score, String constituency) {
		candidateRepository.savePoints(firstName, secondName, score, constituency);
	}
	
	@Override
	public List<Candidate> findAll() {
		return candidateRepository.findAll();
	}

	@Override
	public List<String> getConstituencies() {
		return candidateRepository.getConstituencies();
	}

	@Override
	public List<Candidate> getCandidatesByConstituency(String constituency) {
		return candidateRepository.getCandidatesByConstituency(constituency);
	}
	
	@Override
	public int getConstituencyCandidateCount(String constituency) {
		return candidateRepository.getConstituencyCandidateCount(constituency);
	}
	
	@Override
	public ArrayList<String> getResults(String constituency) {
		return candidateRepository.getResults(constituency);
		
	}
}
