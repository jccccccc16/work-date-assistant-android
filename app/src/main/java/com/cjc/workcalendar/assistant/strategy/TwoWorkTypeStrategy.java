package com.cjc.workcalendar.assistant.strategy;

public class TwoWorkTypeStrategy implements WorkTypeStrategy{
    @Override
    public String[] getWorkTypeArray() {
        return new String[]{"早","晚","休"};
    }
}
