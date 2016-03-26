package dk.cit.oossp.thymeleaf.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;

import dk.cit.oossp.thymeleaf.domain.Candidate;
import dk.cit.oossp.thymeleaf.service.CandidateService;
import dk.cit.oossp.thymeleaf.service.SpringContextBridgeImpl;

public class DataBean {

	private CandidateService candidateService = SpringContextBridgeImpl.services().getCandidateService(); 
	private List<String> constituencies;
	private static List<Candidate> candidateList;
	private static List<Candidate> votedList;
	private static String selectedConstituency;
	@NotNull(message="Please choose a candidate")
	private static String selectedCandidate;
	private static int candidateCount;
	private static int voteCount;
	
	public List<String> getConstituencies() {
		constituencies = candidateService.getConstituencies();
		return constituencies;
	}

	public List<Candidate> getCandidatesByConstituency(String selectedConstituency) {
//		if (!selectedConstituency.equals("no constituency selected")) {
			setCandidates(candidateService.getCandidatesByConstituency(selectedConstituency));
			if (candidateCount == 0) {
				candidateCount = candidateService.getConstituencyCandidateCount(selectedConstituency);
				votedList = new ArrayList<Candidate>();
			}
//		}
		return getCandidates();
	}
	
	public void pop() {
		for(Iterator< Candidate > it = candidateList.iterator(); it.hasNext();)
	    {
			Candidate current = it.next();
			String str = current.getFirstName() + " " + current.getSecondName();
			if( str.equals(selectedCandidate))
			{
				votedList.add(current);
				it.remove();
			}
	    }
	}
	
	public int[] getPoints(DataBean dataBean) {
		int[] points = new int[votedList.size()];
		for (int i = 0; i < points.length; i++) {
			points[i] = candidateCount - i;
		}
		System.out.println(Arrays.toString(points));
		return points;
	}

	public String getSelectedConstituency() {
		return selectedConstituency;
	}

	public void setSelectedConstituency(String selectedConstituency) {
		DataBean.selectedConstituency = selectedConstituency;
	}

	public String getSelectedCandidate() {
		return selectedCandidate;
	}

	public void setSelectedCandidate(String selectedCandidate) {
		DataBean.selectedCandidate = selectedCandidate;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		DataBean.voteCount = voteCount;
	}
	
	public int getCandidateCount() {
		return candidateCount;
	}
	
	public void setCandidateCount(int candidateCount) {
		DataBean.candidateCount = candidateCount;
	}

	public void setCandidates(List<Candidate> candidates) {
		DataBean.candidateList = candidates;
	}
	
	
	public List<Candidate> getCandidates() {
		return candidateList;
	}

	public List<Candidate> getVotedList() {
		return votedList;
	}

	public void setVotedList(List<Candidate> votedList) {
		DataBean.votedList = votedList;
	}
}
