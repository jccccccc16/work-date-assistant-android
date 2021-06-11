package com.cjc.workcalendar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cjc.workcalendar.assistant.CalendarAssistant;
import com.cjc.workcalendar.assistant.WorkCalendar;
import com.cjc.workcalendar.assistant.strategy.WorkTypeStrategy;

import java.util.Calendar;
import java.util.Map;

public class CalendarFragment extends Fragment {

    public static final String STRING_WORK_CALENDAR="workCalendar";
    private Button nextBtn;
    private Button lastBtn;
    private TextView monthTextView;
    private CalendarAssistant calendarAssistant;
    private Integer currentMonth;
    private static WorkCalendar currentWorkCalendar;

    private TableLayout tableLayout;
    private TextViewFactory textViewFactory;

    private TextView explainText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.calendar_fragment, container, false);

        // 初始化组件
        nextBtn =  inflate.findViewById(R.id.next_btn);
        lastBtn =  inflate.findViewById(R.id.last_btn);
        tableLayout = inflate.findViewById(R.id.tableLayout);
        monthTextView = inflate.findViewById(R.id.month_tv);
        explainText = inflate.findViewById(R.id.explain_tv);
        calendarAssistant = CalendarAssistant.getCalendarAssistant();
        textViewFactory = new TextViewFactory(inflate.getContext(),calendarAssistant.getStrategy());
        currentMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


        // 绑定按钮
        initButtonBind();
        // 初始化时，显示为空
        // 获取数据
        Bundle arguments = getArguments();
        // 如果为空，那么为应用初始化，界面显示为空



        // 如果不为空，那么显示日历
        // 获取传送过来的workCalendar
        if(arguments!=null){
            WorkCalendar workCalendar
                    = (WorkCalendar) arguments.get(CalendarFragment.STRING_WORK_CALENDAR);

            updateData(workCalendar);

            showCalendar(workCalendar,inflate);
        }

        // 显示



        return inflate;
    }


    // 更新月份，和workCalendar
    private void updateData(WorkCalendar newWorkCalendar){
        this.currentWorkCalendar = newWorkCalendar;
        this.currentMonth = newWorkCalendar.getCurrentMonth();
        currentWorkCalendar = newWorkCalendar;
    }

    private void showCalendar(WorkCalendar workCalendar,View view){

        monthTextView.setText(this.currentMonth +" 月");
        explainText.setText(calendarAssistant.getStrategy().getColorExplanation());

        // 总天苏
        Integer dayAmount = workCalendar.getDayAmount();
        // 空格数
        Integer spaceAmount = workCalendar.getSpaceAmount();

//        TableRow oneRow = new TableRow(tableLayout.getContext());
//        TextView textView = new TextView(view.getContext());
//        textView.setText("1|日");
//        oneRow.addView(textView,0);
//        tableLayout.addView(oneRow,1);

        Map<Integer, String> dayWorkTypeMapper = workCalendar.getDayWorkTypeMapper();
        TableRow oneRow = new TableRow(view.getContext());
        // 填充空格

//        for (int space=0;space<spaceAmount;space++){
//            TextView textView = textViewFactory.createTextView("");
//            oneRow.addView(textView,space);
//        }
        // 元素填充到tableRow的位置
        TableRow tableRow = new TableRow(view.getContext());
        TextView textView = null;
        int tableRowindex = 0;
        int tableLayoutIndex = 1;
        for(int day=1;day<=dayAmount;){

            if(spaceAmount!=0){
                textView = textViewFactory.createTextView("", "");
                tableRow.addView(textView,tableRowindex);
                tableRowindex++;
                spaceAmount--;
                continue;
            }

            // 如果有那么填充
            if(dayWorkTypeMapper.containsKey(day)){
                String workType = dayWorkTypeMapper.get(day);

                textView = textViewFactory.createTextView(String.valueOf(day), workType);
            }else{ // 如果没有那么填上空格
                textView = textViewFactory.createTextView(String.valueOf(day),"");
            }
            tableRow.addView(textView,tableRowindex);
            day++;
            tableRowindex++;
            if(tableRowindex%7==0){
                tableLayout.addView(tableRow,tableLayoutIndex);
                tableRowindex = 0;
                // 下一行
                tableLayoutIndex++;
                // 新的一行
                tableRow = new TableRow(view.getContext());
            }
        }

        tableLayout.addView(tableRow,tableLayoutIndex);



    }

    private void initButtonBind(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                synchronized (this){
                    WorkCalendar nextWorkCalendar = calendarAssistant.getNextWorkCalendar(currentWorkCalendar);
                    // 更新为当前workCalendar

                    // 显示fragment
                    updateCalendar(nextWorkCalendar);
                }




            }
        });

        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronized (this){
                    WorkCalendar lastWorkCalendar = calendarAssistant.getLastWorkCalendar(currentWorkCalendar);
                    // 更新为当前workCalendar
                    updateCalendar(lastWorkCalendar);
                }

            }
        });

    }

    private void updateCalendar(WorkCalendar workCalendar){

        updateData(workCalendar);

        // 唤起CalendarFragment进行显示
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CalendarFragment calendarFragment = new CalendarFragment();
        fragmentTransaction.replace(R.id.fragment,calendarFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CalendarFragment.STRING_WORK_CALENDAR,workCalendar);
        calendarFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    /***
     * <TextView
     *                 android:id="@+id/textView6"
     *                 android:layout_width="50dp"
     *                 android:layout_height="70dp"
     *                 android:text="六"
     *                 android:textSize="24sp" />
     */
    static class TextViewFactory{

        private Context context;
        private final Integer WIDTH = 50;
        private final Integer HEIGHT = 70;
        private final Integer TEXTSIZE = 24;
        private TextView textView;
        private WorkTypeStrategy strategy;

        public TextViewFactory(Context context,WorkTypeStrategy strategy){
            this.context = context;
            this.strategy = strategy;
        }
        public  TextView createTextView(String text,String workType){
            this.textView = new TextView(this.context);
            setShape(text);
            setColor(workType);
            return textView;
        }

        private void setShape(String text){
            textView.setText(text);
            textView.setWidth(WIDTH);
            textView.setHeight(HEIGHT);
            textView.setTextSize(TEXTSIZE);
        }

        private void  setColor(String workType){
            if(!workType.equals("")){
                Integer[] color = strategy.getColorByWorkType(workType);
                textView.setBackgroundColor(android.graphics.Color.rgb(color[0],color[1],color[2]));
            }

        }


    }
}
