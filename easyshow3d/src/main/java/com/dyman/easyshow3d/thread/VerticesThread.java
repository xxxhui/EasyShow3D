package com.dyman.easyshow3d.thread;

import java.util.ArrayList;

/**
 * Created by dyman on 18/5/17.
 */

public class VerticesThread extends Thread {

    private static final String TAG = "VerticesThread";
    /** 定义解析的起始点 */
    private int start;
    /** 定义解析的结束点 */
    private int end;
    /** obj文件的数据 */
    private ArrayList<String[]> vLines;
    /** 当前线程的ID */
    private int threadID;
    /** 解析完成的回调 */
    private IAnalysisFinishCallback finishCallback;
    private boolean isFinish = false;


    public VerticesThread(int threadID, ArrayList<String[]> vLines, int start, int end, IAnalysisFinishCallback finishCallback) {
        this.threadID = threadID;
        this.vLines = vLines;
        this.start = start;
        this.end = end;
        this.finishCallback = finishCallback;
    }


    @Override
    public void run() {
        ArrayList<Float> alv = new ArrayList<>();

        for (int i = start; i < end; i++) {
            String[] tempsa = vLines.get(i);
            if (tempsa[0].trim().equals("v")) {
                alv.add(Float.parseFloat(tempsa[1]));
                alv.add(Float.parseFloat(tempsa[2]));
                alv.add(Float.parseFloat(tempsa[3]));
            }

            if ((i - start) % 100 == 0) {
                finishCallback.verticeProgressUpdate(threadID, i - start);
            }
        }
        isFinish = true;
        finishCallback.alvFinish(threadID, start, alv);

    }


    public boolean isFinish() {
        return isFinish;
    }

}