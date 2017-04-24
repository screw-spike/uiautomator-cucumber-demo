package com.cucumber.demo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by ogq on 4/18/17.
 */
public class CMDRunner {
    private static final String TAG = "AUTOTEST-CMDRunner";

    public static class CMD_Result{
        public int resultCode;
        public String error;
        public String success;

        public CMD_Result(int resultCode, String error, String success){
            this.resultCode = resultCode;
            this.error = error;
            this.success = success;
        }
    }

    /**
     * 执行命令
     * @param cmd
     * @param isShow
     * @param isNeedResultMsg
     * @return
     */
    public static CMD_Result runCmd(String cmd, boolean isShow, boolean isNeedResultMsg){
        if (isShow){
            Log.v(TAG, "runCmd:" + cmd);
        }
        CMD_Result cmdResult = null;
        int result;
        try{
            Process process = Runtime.getRuntime().exec(cmd);
            result = process.waitFor();
            if (isNeedResultMsg){
                StringBuilder successMsg = new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                BufferedReader successResult = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                BufferedReader errorResult = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String s;
                while ((s = successResult.readLine()) != null){
                    successMsg.append(s);
                }

                while ((s =errorResult.readLine()) != null){
                    errorMsg.append(s);
                }

                cmdResult = new CMD_Result(result, errorMsg.toString(), successMsg.toString());
            }

        }catch (Exception e){
            Log.e(TAG, "run cmd:" + cmd + " failed!!");
            e.printStackTrace();
        }
        return cmdResult;
    }

}
