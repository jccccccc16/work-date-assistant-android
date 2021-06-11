package com.cjc.workcalendar.assistant.strategy;

public class StrategyFactory {

    public static final String[] strategyNames = new String[]{"两班制"};



    public static WorkTypeStrategy getStrategy(Integer strategyTypeNum){

        switch (strategyTypeNum){
            case 0: return new TwoWorkTypeStrategy();
        }
        return null;

    }

}
