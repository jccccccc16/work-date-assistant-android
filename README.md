### 下拉菜单

网上示例：

- ![Alt text](http://c.biancheng.net/uploads/allimg/190403/5-1Z403154115Z2.jpg "optional title")

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <TextView
        android:id="@+id/textView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="textview"/>
    <Spinner
        android:id="@+id/spinner1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
</LinearLayout>
```

```java
public class SpinnerActivity extends Activity {
    private List<String> list = new ArrayList<String>();
    private TextView textview;
    private Spinner spinnertext;
    private ArrayAdapter<String> adapter;
    public void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.spiner);
        //第一步：定义下拉列表内容
        list.add("A型");
        list.add("B型");
        list.add("O型");
        list.add("AB型");
        list.add("其他");
        textview = (TextView) findViewByld(R.id.textViewl);
        spinnertext = (Spinner) findViewByld(R.id.spinnerl);
        //第二步：为下拉列表定义一个适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
        //第五步：添加监听器，为下拉列表设置事件的响应
        spinnertext.setOnltemSelectedListener(new Spinner.OnltemSelectedListener() {
            public void onltemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选spinnertext的值带入myTextView中*/
                textview.setText("你的血型是:" + adapter.getItem(arg2));
                /* 将 spinnertext 显示^*/
                argO.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                textview.setText("NONE");
                argO.setVisibility(View.VISIBLE);
            }
        });
        //将spinnertext添加到OnTouchListener对内容选项触屏事件处理
        spinnertext.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                v.setVisibility(View.INVISIBLE);
                Log.i("spinner", "Spinner Touch事件被触发!");
                return false;
            }
        });
        //焦点改变事件处理
        spinnertext.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
                Log.i("spinner", "Spinner FocusChange事件被触发！");
            }
        });
    }
}
```





### bug

2021/6/11

当不停点击下一个月是，会出现currentWorkCalendar为空的错误

```java
// CalendarFragment.class
lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronized (this){
                    WorkCalendar lastWorkCalendar = calendarAssistant.getLastWorkCalendar(currentWorkCalendar);
                    updateCalendar(lastWorkCalendar);
                }

            }
        });
```

解决：

添加static保证单例

```java
private static WorkCalendar currentWorkCalendar;
```



2021/6/14

bug：当点击下一个月时，然后改变workType，会跳转到第一个月份







解决：

在calendarAssistant中添加currentWorkCalendar的属性，static修饰，唯一，当点击上一月或者下一月时，更新currentWorkCalendar

```java
// CalendarFragment
// 更新月份，和workCalendar
    private void updateData(WorkCalendar newWorkCalendar){
        this.currentWorkCalendar = newWorkCalendar;
        this.currentMonth = newWorkCalendar.getCurrentMonth();
        currentWorkCalendar = newWorkCalendar;
    }
```





### 新的需求

用户，点击界面中的某一号数，可以改变号数的workType，该号数往后的工作日的workType跟着改变