package com.assessment.football.service;

import java.util.Map;

import com.assessment.football.exception.CustomException;
import com.assessment.football.vo.LeagueRequestVO;

public interface LeagueService {

	Map<String, String> getStatus(LeagueRequestVO leagueRequest) throws CustomException;

}
