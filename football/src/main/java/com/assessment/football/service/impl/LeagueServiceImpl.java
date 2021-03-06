package com.assessment.football.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assessment.football.exception.CustomException;
import com.assessment.football.service.LeagueService;
import com.assessment.football.vo.Country;
import com.assessment.football.vo.League;
import com.assessment.football.vo.LeagueRequestVO;
import com.assessment.football.vo.Standing;
import com.assessment.football.vo.Team;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class LeagueServiceImpl implements LeagueService {

	@Value("${football.api.key}")
	private String apiKey;
	
	@Value("${football.api.base.url}")
	private String footballApiBaseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Map<String,String> getStatus(LeagueRequestVO leagueRequest) throws CustomException{
		validateLeagueRequest(leagueRequest);
		try {
		Country[] c= getCountries();
		Country cn = Arrays.stream(c).filter(item->item.getCountry_name().equals(leagueRequest.getCountryName())).findFirst().orElse(null);
		if(cn == null) {
			throw new CustomException("FE-400","Country Name is not valid");
		}
		League[] l = getLeagues(cn.getCountry_id());
		League lg = Arrays.stream(l).filter(item->item.getLeague_name().equals(leagueRequest.getLeagueName())).findFirst().orElse(null);
		if(lg == null) {
			throw new CustomException("FE-400","League Name is not valid");
		}
		Team[] t = getTeams(lg.getLeague_id());
		Team tm = Arrays.stream(t).filter(item->item.getTeam_name().equals(leagueRequest.getTeamName())).findFirst().orElse(null);
		if(tm == null) {
			throw new CustomException("FE-400","Team Name is not valid");
		}
		Standing[] s = getStandings(lg.getLeague_id());
		Standing st = Arrays.stream(s).filter(item->item.getTeam_name().equals(leagueRequest.getTeamName())).findFirst().orElse(null);
		return buildResponse(cn,lg,tm,st);
		}catch ( CustomException e) {
			throw e;
		}catch ( Exception e) {
			throw new CustomException("FE-401", "Something went wrong");
		}
	}
	
	private void validateLeagueRequest(LeagueRequestVO leagueRequest) throws CustomException{
		if(leagueRequest.getCountryName() == null || "".equals(leagueRequest.getCountryName())){
			throw new CustomException("FE-400","Country Name is not valid");
		}
		if(leagueRequest.getLeagueName() == null || "".equals(leagueRequest.getLeagueName())){
			throw new CustomException("FE-400","League Name is not valid");
		}
		if(leagueRequest.getTeamName() == null || "".equals(leagueRequest.getTeamName())){
			throw new CustomException("FE-400","Team Name is not valid");
		}
		
	}
	
	@HystrixCommand(fallbackMethod="commonFallback", commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000")
	})
	private Country[] getCountries() {
	     final String uri = footballApiBaseUrl+"get_countries&APIkey="+apiKey;
	  
	     Country[] c = restTemplate.getForObject(uri,Country[].class);
	     return c;  
	 }
	
	@HystrixCommand(fallbackMethod="commonFallback", commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000")
	})
	private League[] getLeagues(String countryId) {
	     final String uri = footballApiBaseUrl+"get_leagues&APIkey="+apiKey+"&country_id="+countryId;
	  
	     League[] l = restTemplate.getForObject(uri,League[].class);
	     return l;  
	 }

	@HystrixCommand(fallbackMethod="commonFallback", commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000")
	})
	private Team[] getTeams(String leagueId) {
	     final String uri = footballApiBaseUrl+"get_teams&APIkey="+apiKey+"&league_id="+leagueId;
	  
	     Team[] l = restTemplate.getForObject(uri,Team[].class);
	     return l;  
	 }
	
	@HystrixCommand(fallbackMethod="commonFallback", commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000")
	})
	private Standing[] getStandings(String leagueId) {
		final String uri = footballApiBaseUrl+"get_standings&APIkey="+apiKey+"&league_id="+leagueId;

		Standing[] l = restTemplate.getForObject(uri,Standing[].class);
		return l;
	}
	
	private void commonFallback() throws CustomException {
		throw new CustomException("FE-1003", "could not get the response, please try again after sometime");
	}
	
	
	private Map<String,String> buildResponse(Country c, League l, Team t, Standing s){
		Map<String,String> m = new HashMap<String,String>();
		m.put("Country ID & Name", c.toString());
		m.put("League ID & Name",l.toString());
		m.put("Team ID & Name",t.toString());
		m.put("Overall League Position", s !=null?s.getOverall_league_position():"NA");
		return m;
	}
}
