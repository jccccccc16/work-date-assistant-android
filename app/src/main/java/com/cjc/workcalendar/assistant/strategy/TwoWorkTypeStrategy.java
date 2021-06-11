package com.cjc.workcalendar.assistant.strategy;

import androidx.annotation.IntegerRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TwoWorkTypeStrategy implements WorkTypeStrategy{

    // String：workType
    // Integer[]: rgb颜色
    private final static List<String> workTypeList = new ArrayList<String>();
    private final static List<Integer[]> rgbList = new ArrayList<Integer[]>();
    static {



        // 蓝色
        workTypeList.add("早");
        rgbList.add(new Integer[]{0,204,255});

        // 咖啡色
        workTypeList.add("晚");
        rgbList.add(new Integer[]{208,164,99});
        // 灰色
        workTypeList.add("休");
        rgbList.add(new Integer[]{0,255,51});
    }

    @Override
    public String[] getWorkTypeArray() {

        String[] workTypeArray = workTypeList.toArray(new String[workTypeList.size()]);
        return workTypeArray;
    }

    @Override
    public Integer[] getColorRgb(int index) {
        return rgbList.get(index);
    }

    @Override
    public String getColorExplanation() {
        return "蓝色为早班，咖啡色为晚班，绿色为休息";
    }

    @Override
    public Integer[] getColorByWorkType(String workType) {
        int index = workTypeList.indexOf(workType);
        Integer[] color = rgbList.get(index);
        return color;
    }
}
