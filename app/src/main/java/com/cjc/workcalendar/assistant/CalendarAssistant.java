package com.cjc.workcalendar.assistant;

import android.util.Log;

import com.cjc.workcalendar.assistant.strategy.TwoWorkTypeStrategy;
import com.cjc.workcalendar.assistant.strategy.WorkTypeStrategy;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CalendarAssistant {

    private static CalendarAssistant calendarAssistant;

    private WorkTypeStrategy strategy;

    private Calendar calendar;

    private static WorkCalendar currentWorkCalendar;



    // 保存每一个生成的workCalendar，Integer为月份，
    private Map<Integer,WorkCalendar> workCalendarMap = new HashMap<Integer, WorkCalendar>();

    public static CalendarAssistant getCalendarAssistant() {

        if(calendarAssistant==null) {
            synchronized (CalendarAssistant.class) {

                if (calendarAssistant == null) {
                    calendarAssistant = new CalendarAssistant();
                    return calendarAssistant;
                }
            }
        }
        return calendarAssistant;
    }

    public CalendarAssistant(){
        Calendar calendar = Calendar.getInstance();
        this.calendar = calendar;
        // 默认两班制
        strategy = new TwoWorkTypeStrategy();
    }

    public void setCurrentWorkCalendar(WorkCalendar currentWorkCalendar){
        this.currentWorkCalendar = currentWorkCalendar;
    }

    public WorkCalendar getCurrentWorkCalendar(){
        return this.currentWorkCalendar;
    }


    /**
     * 主要方法，生成工作日历，用于前端显示
     * @param workTypeNum 今天上什么班
     * @param month 月，如果为null，即为当前月
     * @param day 日，如果为null，即为当前日
     * @return
     */
    public WorkCalendar createCalendar(Integer workTypeNum,Integer month,Integer day){

        // month为null，默认为当前月
        if(month==null){
            month =calendar.get(Calendar.MONTH)+1;
        }
        // day为null，默认为当前日
        if(day==null){
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        // 根据输入的月份和号数，定制日历
        initCalendar(month, day);

        // 生成日历mapper
        ResultCalendarMapper resultCalendarMapper = calculateCalendarMapper(workTypeNum);
        // 获取数据
        Map<Integer, String> calendarMapper = resultCalendarMapper.getCalendarMapper();
        Integer getNextMothFirstDayWorkType = resultCalendarMapper.getNextMothFirstDayWorkType();

        // 空格多少个
        Integer spaceAmount = getSpaceAmount();

        // 这个月有多少天
        int totalDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 创建workCalendar对象
        WorkCalendar workCalendar = new WorkCalendar(calendarMapper, getNextMothFirstDayWorkType, month,day, spaceAmount,totalDay);

        // 判断workCalendarMap是否有该workCalendar
        // 如果不包含
        if(!workCalendarMap.containsKey(month)){
            // 放入map中
            workCalendarMap.put(month,workCalendar);
        }
        Log.i("createCalendar","创建workCalendar对象: "+workCalendar);
        return workCalendar;
    }


    public WorkCalendar getNextWorkCalendar(WorkCalendar thisMonthWorkCalendar){
        Integer thisMonth = thisMonthWorkCalendar.getCurrentMonth();
        Integer nextMothFirstDayWorkType = thisMonthWorkCalendar.getNextMothFirstDayWorkType();
        // 如果当前月是12月那么直接输出12月份的workCalendar
        if(thisMonth==12){
            Log.i("getNextWorkCalendar","当前月份为12月，直接返回12月份: "+thisMonthWorkCalendar);
            return thisMonthWorkCalendar;
        }
        Integer nextMonth = thisMonth + 1;
        // 如果存在
        if(workCalendarMap.containsKey(nextMonth)){
            Log.i("getNextWorkCalendar","返回下一月份， "+nextMonth+" 月份: "+thisMonthWorkCalendar);
            return workCalendarMap.get(nextMonth);
            // 如果不存在
        }else{
            Log.i("getNextWorkCalendar","返回下一月份， "+nextMonth+" 月份: "+thisMonthWorkCalendar);
            WorkCalendar workCalendar = createCalendar(nextMothFirstDayWorkType, nextMonth, 1);
            calendarAssistant.setCurrentWorkCalendar(workCalendar);
            workCalendarMap.put(nextMonth,workCalendar);
            return workCalendar;
        }
    }

    /**
     *
     * @param thisMonthWorkCalendar
     * @return
     */
    public WorkCalendar getLastWorkCalendar(WorkCalendar thisMonthWorkCalendar){



        Integer thisMonth = thisMonthWorkCalendar.getCurrentMonth();
        Integer nextMothFirstDayWorkType = thisMonthWorkCalendar.getNextMothFirstDayWorkType();


        Integer lastMonth = thisMonth -1;
        // 如果存在
        if(workCalendarMap.containsKey(lastMonth)){
            WorkCalendar workCalendar = workCalendarMap.get(lastMonth);
            calendarAssistant.setCurrentWorkCalendar(workCalendar);
            Log.i("getLastWorkCalendar","返回上一个workCalendar: "+workCalendar);
            return workCalendar;
            // // 如果没有，那么直接返回当前月的
        }else{
            Log.i("getLastWorkCalendar","没有上一个，直接返回当前: "+thisMonthWorkCalendar);
            return thisMonthWorkCalendar;
        }
    }



    private void initCalendar(Integer month,Integer day){

        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month - 1, day);
    }




    /**
     * 该方法必须在initCalenar之后
     * @param workTypeNum
     * @return
     */
    private ResultCalendarMapper calculateCalendarMapper(Integer workTypeNum){

        Map<Integer, String> result = new HashMap<>();

        String[] workTypeArray = strategy.getWorkTypeArray();
        String todayWorkType = workTypeArray[workTypeNum];

        int length = workTypeArray.length;

        int today = calendar.get(Calendar.DAY_OF_MONTH);

        System.out.println(today);

        // 这个月的总天数
        int totalDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i=1;i<=totalDay;i++){
            if(i>=today){
                workTypeNum = workTypeNum%length;
                result.put(i,workTypeArray[workTypeNum]);
                workTypeNum++;
                continue;
            }
        }

        ResultCalendarMapper calendarMapper = new ResultCalendarMapper(result, workTypeNum++ % length);
        return calendarMapper;

    }

    /** 获取日历输出时的空格数
     *  该月份的第一天周几-1
     * @return
     */
    private Integer getSpaceAmount(){
        // 获取当前号数
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        // 设置为第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        // 该月份第一天为周几
        int space = calendar.get(Calendar.DAY_OF_WEEK)-1;
        // 设置空格
        // 还原日期
        calendar.set(Calendar.DAY_OF_MONTH,currentDay);
        return space;
    }





    public WorkTypeStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(WorkTypeStrategy strategy) {
        this.strategy = strategy;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }


}
