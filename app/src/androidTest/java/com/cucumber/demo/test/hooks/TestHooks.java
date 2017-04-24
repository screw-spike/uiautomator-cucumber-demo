package com.cucumber.demo.test.hooks;

import android.content.Context;
import android.graphics.Path;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.cucumber.demo.test.elements.CalculatorActivity;
import com.cucumber.demo.test.utils.HelpTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.After;
/**
 * Created by ogq on 4/18/17.
 */
public class TestHooks {
    final UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    final String TAG = "AUTOTEST-HOOKS";
    @Before
    public void befor_features() throws UiObjectNotFoundException {
        //判断当前是否打开被测应用
        String curPkgName = uiDevice.getCurrentPackageName();
        Log.v(TAG,"当前的包名为");
        Log.v(TAG, curPkgName);
        if (curPkgName.equals("com.android.calculator2")){
            // 计算器归零
            CalculatorActivity.getClsBtn().click();
            return;
        }
        //        打开应用
        uiDevice.pressHome();
        List<UiObject2> bottom_btns = uiDevice.findObjects(By.clazz("android.widget.TextView"));
        for (int i =0;i < bottom_btns.size();i++){
            if (i==2){
                ((UiObject2)bottom_btns.toArray()[i]).click();
            }
        }
        UiObject calc = uiDevice.findObject(new UiSelector().text("Calculator").packageName("com.android.launcher"));
        if (calc.waitForExists(3000)){
            calc.clickAndWaitForNewWindow();
        }else{
            throw new UiObjectNotFoundException("计算器应用没有找到");
        }

    }
    @After
    public void after_features(Scenario scenario){
        String name = scenario.getName();
        String status = scenario.getStatus();

        Log.v(TAG,"当前的用例名称：" + name);
        Log.v(TAG,"当前的用例状态：" + status);
        if (status.equals("passed")){
            return;
        }
        String cur_path =  "/data/data/com.cucumber.demo";
//        String png_name = (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) + ".png";
        String png_name = "error.png";
        String png_path =  cur_path + '/' + png_name;
        uiDevice.takeScreenshot(new File(png_path));
        byte[] imageAsByte = HelpTools.image2Bytes(png_path);
        scenario.embed(imageAsByte, "image/png");
        Log.v(TAG, "用例《" + name + "》失败截图成功!");

    }
}
