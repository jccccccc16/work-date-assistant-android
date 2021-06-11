package com.cjc.workcalendar;

import com.cjc.workcalendar.assistant.CalendarAssistant;
import com.cjc.workcalendar.assistant.WorkCalendar;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Map;

public class CalendarTest {

    private CalendarAssistant calendarAssistant;

    @Before
    public void init(){
        calendarAssistant = CalendarAssistant.getCalendarAssistant();
    }

    @Test
    public void test(){
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);
        int actualMaximum = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(actualMaximum);
    }

    @Test
    public void test02(){
        CalendarAssistant calendarAssistant = new CalendarAssistant();
        WorkCalendar calendar = calendarAssistant.createCalendar(0, 6, null);
        System.out.println(calendar);
    }

    @Test
    public void test03(){
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);
        int i1 = instance.get(Calendar.DAY_OF_WEEK);
        System.out.println(i1);
    }
    @Test
    public void test04(){
        WorkCalendar calendar = calendarAssistant.createCalendar(0, 6, 10);
        System.out.println(calendar);
        WorkCalendar nextWorkCalendar = calendarAssistant.getNextWorkCalendar(calendar);
        System.out.println(nextWorkCalendar);
        WorkCalendar lastWorkCalendar = calendarAssistant.getLastWorkCalendar(nextWorkCalendar);
        System.out.println(lastWorkCalendar);
    }


    @Test
    public void test05(){
        WorkCalendar calendar = calendarAssistant.createCalendar(0, 6, 10);
        System.out.println(calendar);
        WorkCalendar lastWorkCalendar = calendarAssistant.getLastWorkCalendar(calendar);
        System.out.println(lastWorkCalendar);
    }


    @Test
    public void test06(){
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.MONTH);
        System.out.println(i);
    }





}
