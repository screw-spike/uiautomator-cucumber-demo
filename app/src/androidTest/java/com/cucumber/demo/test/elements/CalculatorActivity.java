package com.cucumber.demo.test.elements;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by ogq on 4/19/17.
 */
public class CalculatorActivity {

    private static final UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    /**
     * 获取数字按键
     * @param num
     * @return
     */
    public static UiObject getNumBtn(String num){
        return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/digit" + num));
    }

    /**
     * 获取运算符和非数字字符
     * @param op
     * @return
     * @throws UiObjectNotFoundException
     */
    public static UiObject getCharBtn(String op) throws UiObjectNotFoundException {
        switch (op) {
            case "+":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/plus"));
            case "-":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/minus"));
            case "x":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/mul"));
            case "/":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/div"));
            case "%":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/pct"));
            case "=":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/equal"));
            case ".":
                return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/dot"));
            default:
                throw new UiObjectNotFoundException("运算符不正确");
        }
    }

    /**
     * 获取清除按钮
     * @return
     */
    public static UiObject getClsBtn(){
        return uiDevice.findObject(new UiSelector().resourceId("com.android.calculator2:id/clear"));
    }

    /**
     * 获取计算结果
     * @return
     */
    public static UiObject getResultView(){
        return uiDevice.findObject(new UiSelector().className("android.widget.EditText"));
    }
}
