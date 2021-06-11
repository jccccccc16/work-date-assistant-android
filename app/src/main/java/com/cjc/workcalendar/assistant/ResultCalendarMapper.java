package com.cjc.workcalendar.assistant;

import java.util.HashMap;
import java.util.Map;

public class ResultCalendarMapper {

    private Map<Integer,String> calendarMapper = new HashMap<Integer, String>();
    private Integer nextMothFirstDayWorkType;

    public ResultCalendarMapper(Map<Integer, String> calendarMapper, Integer nextMothFirstDayWorkType) {
        this.calendarMapper = calendarMapper;
        this.nextMothFirstDayWorkType = nextMothFirstDayWorkType;
    }

    public Map<Integer, String> getCalendarMapper() {
        return calendarMapper;
    }

    public void setCalendarMapper(Map<Integer, String> calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public Integer getNextMothFirstDayWorkType() {
        return nextMothFirstDayWorkType;
    }

    public void setNextMothFirstDayWorkType(Integer nextMothFirstDayWorkType) {
        this.nextMothFirstDayWorkType = nextMothFirstDayWorkType;
    }
}
