package com.cjc.workcalendar.assistant.strategy;

import androidx.annotation.IntegerRes;

/**
 * 必须保证workType顺序
 * 颜色顺序必须对应workType
 */
public interface WorkTypeStrategy {
    /**
     * 获取worktype,必须保证顺序正确
     * @return
     */
    public String[] getWorkTypeArray();

    /**
     * 获取workType，对应的颜色
     * @return
     */
    public Integer[] getColorRgb(int index);

    /**
     * 获取颜色的说明
     * @return
     */
    public String getColorExplanation();

    public Integer[] getColorByWorkType(String workType);
}
