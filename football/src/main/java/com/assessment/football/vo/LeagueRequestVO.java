package com.assessment.football.vo;

public class LeagueRequestVO {
	private String countryName;
	private String leagueName;
	private String teamName;
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Override
	public String toString() {
		return "LeagueRequestVO [countryName=" + countryName + ", leagueName=" + leagueName + ", teamName=" + teamName
				+ "]";
	}
	
	
}
