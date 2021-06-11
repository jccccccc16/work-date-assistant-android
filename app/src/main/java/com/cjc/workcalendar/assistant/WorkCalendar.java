package com.cjc.workcalendar.assistant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WorkCalendar implements Serializable {

    // integer为号数，String为今天上什么班workType
    private Map<Integer,String> dayWorkTypeMapper = new HashMap<Integer, String>();
    // 下个月第一天上什么班
    private Integer nextMothFirstDayWorkType;
    // 当前月
    private Integer currentMonth;

    // 输出多少个空格
    private Integer spaceAmount;

    // 这个月有多少天
    private Integer dayAmount;

    private Integer currentDay;

    public WorkCalendar(Map<Integer,String> dayWorkTypeMapper,
                        Integer nextMothFirstDayWorkType,
                        Integer currentMonth,
                        Integer currentDay,
                        Integer spaceAmount,
                        Integer dayAmount){
        this.dayWorkTypeMapper = dayWorkTypeMapper;
        this.nextMothFirstDayWorkType = nextMothFirstDayWorkType;
        this.currentMonth = currentMonth;
        this.spaceAmount = spaceAmount;
        this.dayAmount = dayAmount;
        this.currentDay = currentDay;
    }

    public Map<Integer, String> getDayWorkTypeMapper() {
        return dayWorkTypeMapper;
    }

    public void setDayWorkTypeMapper(Map<Integer, String> dayWorkTypeMapper) {
        this.dayWorkTypeMapper = dayWorkTypeMapper;
    }

    public Integer getNextMothFirstDayWorkType() {
        return nextMothFirstDayWorkType;
    }

    public void setNextMothFirstDayWorkType(Integer nextMothFirstDayWorkType) {
        this.nextMothFirstDayWorkType = nextMothFirstDayWorkType;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }

    public Integer getSpaceAmount() {
        return spaceAmount;
    }

    public void setSpaceAmount(Integer spaceAmount) {
        this.spaceAmount = spaceAmount;
    }

    public Integer getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(Integer dayAmount) {
        this.dayAmount = dayAmount;
    }

    public Integer getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Integer currentDay) {
        this.currentDay = currentDay;
    }

    @Override
    public String toString() {
        return "WorkCalendar{" +
                "dayWorkTypeMapper=" + dayWorkTypeMapper +
                ", nextMothFirstDayWorkType=" + nextMothFirstDayWorkType +
                ", currentMonth=" + currentMonth +
                ", spaceAmount=" + spaceAmount +
                ", dayAmount=" + dayAmount +
                ", currentDay=" + currentDay +
                '}';
    }
}
