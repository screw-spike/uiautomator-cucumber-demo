package com.cucumber.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private final String TAG = "AUTOTEST-MainActivity";

    public EditText et;
    public Button bt;
    DataOutputStream dos = null;
    //  执行UiAutomator脚本命令
    String cmd = "ls";
    String keyword = "10000";
    int result = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }


    private void findView(){
        et = (EditText) findViewById(R.id.edit_text);
        bt = (Button) findViewById(R.id.run_btn);
        bt.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        try{
            if (et.getText().length() > 0){
                keyword = et.getText().toString();
            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.android.calculator2","com.android.calculator2.Calculator");
            intent.setComponent(comp);
            intent.setAction("android.intent.action.VIEW");
            // 启动被测app
            startActivity(intent);
            Log.v(TAG, "准备执行测试");
            new UiautomatorThread().start();

//             root权限下执行
//            Process p = Runtime.getRuntime().exec("su");
//            dos = new DataOutputStream(p.getOutputStream());
//            dos.writeBytes(cmd + keyword + "\n");
//             keyword 为传递的参数
//            dos.flush();
//            dos.writeBytes("exit\n");
//            dos.flush();
//            p.waitFor();
//            result = p.exitValue();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            if (dos != null){
//                try{
//                    dos.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
        }

    }

        /**
     * 运行uiautomator比较消耗时间，所以要另起一个线程来运行
     */
    class UiautomatorThread extends Thread{
            @Override
            public void run() {
                super.run();
                String command = genCommand();
                CMDRunner.CMD_Result rs = CMDRunner.runCmd(command, true, true);
                Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
            }
            /**
             * 生成命令
             */
            public String genCommand(){
                String cmd = "am instrument -w -r -e debug false com.cucumber.demo.test/.Instrumentation";
                Log.v("AUTOTEST", "生成的命令为：" + cmd);
                return cmd;
            }

    }
}


