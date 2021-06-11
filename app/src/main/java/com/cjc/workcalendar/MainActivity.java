package com.cjc.workcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cjc.workcalendar.assistant.CalendarAssistant;
import com.cjc.workcalendar.assistant.WorkCalendar;
import com.cjc.workcalendar.assistant.strategy.StrategyFactory;
import com.cjc.workcalendar.assistant.strategy.WorkTypeStrategy;

public class MainActivity extends AppCompatActivity {

    private Spinner workPatternSpinner;
    // 菜单的列表顺序与对应的策略工厂中获取策略的顺序一致
    private ArrayAdapter<String> workPatternAdapter;
    private ArrayAdapter<String> workTypeAdapter;
    // 菜单的列表顺序与对应的策略的workType的顺序一致，比如两班制，为早，晚，休
    private Spinner workTypeSpinner;
    private CalendarAssistant calendarAssistant;

    private WorkCalendar currentWorkCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化代码
        // 初始化calendarAssistant
        calendarAssistant = CalendarAssistant.getCalendarAssistant();
        // 获取策略名称
        String[] strategyNames = StrategyFactory.strategyNames;
        workPatternSpinner = findViewById(R.id.patternSpinner);
        workTypeSpinner = findViewById(R.id.workTypeSpinner);
        workPatternAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,strategyNames);

        // 初始化工作制度下拉菜单

        workPatternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workPatternSpinner.setAdapter(workPatternAdapter);
        bind();
    }

    private void bind(){
        //
        workPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // 设置策略
                WorkTypeStrategy strategy = StrategyFactory.getStrategy(i);
                calendarAssistant.setStrategy(strategy);
                String[] workTypeArray = strategy.getWorkTypeArray();
                workTypeAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,workTypeArray);
                workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                workTypeSpinner.setAdapter(workTypeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 当下拉菜单被改变时，默认显示当天月当天
        workTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // i为下拉菜单item的下标，
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                // 显示日历
                // 获取当前日工作类型
                Integer workTypeNum = index;
                WorkCalendar currentWorkCalendar = calendarAssistant.getCurrentWorkCalendar();
                if(currentWorkCalendar==null){
                    // 得到工作日历
                    currentWorkCalendar = calendarAssistant.createCalendar(workTypeNum, null, null);
                    calendarAssistant.setCurrentWorkCalendar(currentWorkCalendar);
                }else{
                    currentWorkCalendar = calendarAssistant.createCalendar(workTypeNum, currentWorkCalendar.getCurrentMonth(),currentWorkCalendar.getCurrentDay() );
                    calendarAssistant.setCurrentWorkCalendar(currentWorkCalendar);
                }

                // 唤起CalendarFragment进行显示
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CalendarFragment calendarFragment = new CalendarFragment();
                fragmentTransaction.replace(R.id.fragment,calendarFragment);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CalendarFragment.STRING_WORK_CALENDAR,currentWorkCalendar);
                calendarFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }




    /**
     * 提示框
     */
    private void showExitDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}