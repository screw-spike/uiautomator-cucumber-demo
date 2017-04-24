package com.cucumber.demo.test.steps;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cucumber.demo.MainActivity;
import com.cucumber.demo.test.elements.CalculatorActivity;
import com.cucumber.demo.test.runner.SomeDependency;

import cucumber.api.CucumberOptions;
import cucumber.api.java.zh_cn.假如;
import cucumber.api.java.zh_cn.那么;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@CucumberOptions(features="features", glue = "com.cucumber.demo.test", format={"pretty","html:/data/data/com.cucumber.demo/reports"})
public class AppTestStep extends ActivityInstrumentationTestCase2<MainActivity>{

    final String TAG = "AUTOTEST";

    public AppTestStep(SomeDependency dependency) {

        super(MainActivity.class);
        assertNotNull(dependency);
    }


    @假如("^输入数字(\\S+)$")
    public void input_number(String number) throws UiObjectNotFoundException {
        Log.v(TAG, "输入数字为：" + number);
        char[] chars = number.toCharArray();

        for(int i = 0; i < chars.length; i++){
            if (chars[i] == '.'){
                CalculatorActivity.getCharBtn(String.valueOf(chars[i])).click();
            }
            else {
                CalculatorActivity.getNumBtn(String.valueOf(chars[i])).click();
            }
        }
    }

    @假如("^输入运算符([+-x\\/=])$")
    public void input_op(String op) throws UiObjectNotFoundException {
        Log.v(TAG, "输入运算符为：" + op);
        CalculatorActivity.getCharBtn(op).click();
    }

    @假如("^计算器归零$")
    public void reset_calc() throws UiObjectNotFoundException {
        Log.v(TAG, "计算器归零");
        UiObject clear_obj = CalculatorActivity.getClsBtn();
        if (clear_obj.waitForExists(3000)){
            clear_obj.click();
        }
    }

    @那么("^验证运算结果(\\S+)$")
    public void chk_result(String result) throws UiObjectNotFoundException {
        Log.v(TAG, "期望运算结果为：" + result);
        UiObject result_obj = CalculatorActivity.getResultView();
        if (result_obj.waitForExists(5000)){
            String act_result = result_obj.getText();
            Log.v(TAG, "实际运算结果为：" + act_result);
           if (!result.equals(act_result)) {
               throw new UiObjectNotFoundException("结果比对异常，期望值是:" + result + ",实际值是:" +   act_result);
           }
        }else{
            throw new UiObjectNotFoundException("结果控件不存在");
        }
    }
}