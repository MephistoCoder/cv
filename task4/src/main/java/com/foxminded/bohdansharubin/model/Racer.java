package com.foxminded.bohdansharubin.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.foxminded.bohdansharubin.utils.RaceDataUtils;

public class Racer implements Comparable<Racer> {
	private String abbreviation;
	private String fullName;
	private String racingTeam;
	private long bestLapInMilliSeconds;
	
	public Racer(String abbreviation, String fullName, String racingTeam) {
		this.abbreviation = abbreviation;
		this.fullName = fullName;
		this.racingTeam = racingTeam;
	}

	public String getFullName() {
		return fullName;
	}

	public String getRacingTeam() {
		return racingTeam;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public long getBestLap() {
		return bestLapInMilliSeconds;
	}

	private void setBestLap(long bestLap) {
		this.bestLapInMilliSeconds = bestLap;
	}
	
	public void calculateBestLapTime(LocalDateTime startLapTime, LocalDateTime endLapTime) {
		setBestLap(RaceDataUtils.getDifferenceWithToDates(startLapTime, endLapTime));
	}
	
	@Override
	public int compareTo(Racer otherRacer) {
		if(this.bestLapInMilliSeconds > otherRacer.bestLapInMilliSeconds) {
			return 1;
		} else if(this.bestLapInMilliSeconds < otherRacer.bestLapInMilliSeconds) {
			return -1;
		} else {			
			return 0;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(abbreviation, bestLapInMilliSeconds, fullName, racingTeam);
	}

	@Override
	public boolean equals(Object otherObject) {
		if(otherObject == null) {
			return false;
		}
		if (this == otherObject) {
			return true;
		}
		if (!(otherObject instanceof Racer)) {
			return false;
		}
		Racer other = (Racer) otherObject;
		return Objects.equals(abbreviation, other.abbreviation) && bestLapInMilliSeconds == other.bestLapInMilliSeconds
				&& Objects.equals(fullName, other.fullName) && Objects.equals(racingTeam, other.racingTeam);
	}
	
}
