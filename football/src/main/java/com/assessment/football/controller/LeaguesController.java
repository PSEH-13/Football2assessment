package com.assessment.football.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.football.exception.CustomException;
import com.assessment.football.service.LeagueService;
import com.assessment.football.vo.LeagueRequestVO;


@RestController
@RequestMapping("/api/v1/league")
public class LeaguesController {

	@Autowired
	LeagueService leagueService;
	
    @GetMapping("/status")
    public Map<String,String> status(LeagueRequestVO leagueRequest) throws CustomException {
    	
      return leagueService.getStatus(leagueRequest);
    }
    
    @ExceptionHandler({CustomException.class })
    public Map<String,String> handleException(CustomException e) {
        Map<String,String> h = new HashMap<>();
        h.put("statusCode", e.getStatusCode());
        h.put("statusMessage", e.getStatusMsg());
        return h;
    }
    
}
