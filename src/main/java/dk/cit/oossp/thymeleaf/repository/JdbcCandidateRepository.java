package dk.cit.oossp.thymeleaf.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dk.cit.oossp.thymeleaf.domain.Candidate;
import dk.cit.oossp.thymeleaf.rowmapper.CandidateRowMapper;

@Repository
public class JdbcCandidateRepository implements CandidateRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCandidateRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Candidate get(String firstName, String lastName) {
		String sql = 
				  "SELECT * "
				+ "FROM candidates "
				+ "WHERE First_name = ? "
				+ "AND Second_name = ?";
		Candidate candidate = 
				jdbcTemplate.queryForObject
				(
						sql, 
						new Object[] { firstName, lastName },
						new CandidateRowMapper()
				);
		return candidate;
	}

	public List<Map<String, Object>> getFromVotesTable(String firstName, String lastName) {
		String sql = 
				  "SELECT * "
				+ "FROM votes "
				+ "WHERE First_name = ? "
				+ "AND Second_name = ?";
		List<Map<String,Object>> result = 
				jdbcTemplate.queryForList
				(
						sql,
						new Object[] {firstName, lastName}
				);
		return result;
	}
	
	@Override
	public void savePoints(String firstName, String secondName, int score, String constituency) {
		if(getFromVotesTable(firstName, secondName).size() < 1)
			addPoints(firstName, secondName, score, constituency);
		else
			updatePoints(firstName, secondName, score);
	}
	
	public void addPoints(String firstName, String secondName, int score, String constituency) {
		System.out.println("add");
		String sql = 
				"INSERT INTO votes "
				+ "(First_name, Second_name, Score, Constituency) "
				+ "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update
		(
				sql, 
				new Object[] {firstName, secondName, score, constituency }
		);
	}

	public void updatePoints(String firstName, String secondName, int score) {
		System.out.println("update");
		String sql = 
				  	"UPDATE votes "
				  + "SET Score = Score + ? "
				  + "WHERE First_name = ? AND Second_name = ?";
		jdbcTemplate.update
		(
				sql, 
				new Object[] {score, firstName, secondName}
		);
	}

	@Override
	public List<Candidate> findAll() {
		String sql = 
				  "SELECT * "
				+ "FROM candidates";
		return jdbcTemplate.query(sql, new CandidateRowMapper());
	}

	@Override
	public List<String> getConstituencies() {
		List<String> constituencies = new ArrayList<String>();
		String sql = 
				  "SELECT DISTINCT constituency "
				+ "FROM candidates "
				+ "ORDER BY constituency";
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : resultSet) {
			constituencies.add((String)row.get("constituency"));
		}
		return constituencies;
	}

	@Override
	public List<Candidate> getCandidatesByConstituency(String constituency) {
		String sql = 
				  "SELECT * "
				+ "FROM candidates "
				+ "WHERE Constituency = ? "
				+ "ORDER BY Party, Second_name";
		List<Candidate> candidateList = 
				jdbcTemplate.query
				(
						sql, 
						new Object[] { constituency },
						new CandidateRowMapper()
				);
		return candidateList;
	}

	@Override
	public int getConstituencyCandidateCount(String constituency) {
		String sql = 
				  "SELECT COUNT(*) AS count "
				+ "FROM candidates "
				+ "WHERE Constituency = ?";
		Long candidateCount = null;
		List<Map<String,Object>> result = 
				jdbcTemplate.queryForList
				(
						sql,
						new Object[] {constituency}
				);
		for (Map<String, Object> row : result) {
			candidateCount = ((Long) row.get("count"));
		}
		return candidateCount.intValue();
	}
	
	@Override
	public ArrayList<String> getResults(String constituency) {
		ArrayList<String> elected = new ArrayList<String>();
		String sql = 
				  "SELECT * "
				+ "FROM votes "
				+ "WHERE constituency = ? "
				+ "ORDER BY Score DESC";
		List<Map<String,Object>> resultSet = 
				jdbcTemplate.queryForList
				(
						sql,
						new Object[] {constituency}
				);
		for (Map<String, Object> row : resultSet) {
			if(elected.size() == 5)
				break;
			elected.add(((String)row.get("First_name") + " " + (String)row.get("Second_name") + " - " + (Integer)row.get("score")));
		}
		return elected;
	}
}
