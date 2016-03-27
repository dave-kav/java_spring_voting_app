package dk.cit.oossp.thymeleaf.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import dk.cit.oossp.thymeleaf.domain.Candidate;
import dk.cit.oossp.thymeleaf.service.CandidateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

	@Autowired
	CandidateService candidateService;
	
	@RequestMapping(value={"/", "/home"}, method=RequestMethod.GET)
	public String home(Model model, Locale locale) {
		model.addAttribute("dataBean", new DataBean());
		return "home";
	}

	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String constituencies(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		return "constituencies";
	}

	@RequestMapping(value="/candidates", method=RequestMethod.POST)
	public String next(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		//System.out.println(action);
		String constituency = dataBean.getSelectedConstituency();
		//System.out.println(constituency);
		List<Candidate> candidateList = dataBean.getCandidates();
		if (candidateList == null)
			candidateList = dataBean.getCandidatesByConstituency(constituency);
		model.addAttribute("candidateList", candidateList);
		System.out.println("voteCount: " + dataBean.getVoteCount());
		System.out.println("candidateCount: " + dataBean.getCandidateCount());

		//first time
		if (dataBean.getSelectedCandidate() == null) {
			if (dataBean.getVoteCount() <= dataBean.getCandidateCount()) {
				dataBean.setVoteCount(dataBean.getVoteCount() + 1);
				if (dataBean.getSelectedCandidate() != null){
					dataBean.pop();
				}
				return "candidates";
			}
		}
		if (!dataBean.getSelectedCandidate().equals("No further preference")) {
			if (dataBean.getVoteCount() <= dataBean.getCandidateCount()) {
				dataBean.setVoteCount(dataBean.getVoteCount() + 1);
				if (dataBean.getSelectedCandidate() != null){
					dataBean.pop();
				}
				return "candidates";
			}
			
			else {
				for (Candidate c: dataBean.getVotedList())
					System.out.println(c.getFirstName() + " " + c.getSecondName());
				dataBean.setCandidates(null);
				dataBean.setSelectedCandidate(null);
				int index = 0;
				int[] points = dataBean.getPoints(dataBean);
				for (Candidate c: dataBean.getVotedList()) {
					candidateService.savePoints(c.getFirstName(), c.getSecondName(), points[index], dataBean.getSelectedConstituency());
				}
				dataBean.setCandidateCount(0);
				dataBean.setVotedList(null);
				dataBean.setVoteCount(0);
				return "complete";
			}
		}
		
		else if ((dataBean.getSelectedCandidate().equals("No further preference") && dataBean.getVotedList().isEmpty())) {
			return "pickOne";
		}
		
		else {
			dataBean.setCandidates(null);
			dataBean.setSelectedCandidate(null);
			int index = 0;
			int[] points = dataBean.getPoints(dataBean);
			for (Candidate c: dataBean.getVotedList()) {
				candidateService.savePoints(c.getFirstName(), c.getSecondName(), points[index], dataBean.getSelectedConstituency());
				index++;
			}
			dataBean.setCandidateCount(0);
			dataBean.setVotedList(null);
			dataBean.setVoteCount(0);
			return "complete";
		}
	}
	
	@RequestMapping(value="/candidates", method=RequestMethod.GET)
	public String getResults(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		return "candidates";
	}
	
	@RequestMapping(value="/results", method=RequestMethod.GET)
	public String results(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		return "results";
	}
	
	@RequestMapping(value="/displayResults", method=RequestMethod.POST)
	public String displayResults(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		ArrayList<String> elected = candidateService.getResults(dataBean.getSelectedConstituency());
		model.addAttribute("elected", elected);
		model.addAttribute("constituency", dataBean.getSelectedConstituency());
		return "displayresults";
	}
	
	@RequestMapping(value="/displayResults", method=RequestMethod.GET)
	public String getDisplayResults(@ModelAttribute DataBean dataBean, Model model, Locale locale) {
		return "displayresults";
	}
}
