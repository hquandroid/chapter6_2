package android.cst.hqu.edu.cn.chapter6_2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

class ClientThread implements Runnable{
    private Socket s;
    private Handler handler;
    public Handler revHandler;
    BufferedReader br=null;
    OutputStream os=null;
    public ClientThread(Handler handler) {
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
            s = new Socket("192.168.31.59", 30000);
            br = new BufferedReader(new InputStreamReader((s.getInputStream())));
            os = s.getOutputStream();

            new Thread() {
                @Override
                public void run() {
                    String content = null;
                    try {
                        content = br.readLine();
                        while(content!=null){
                            Message msg=new Message();
                            msg.what=0x123;
                            msg.obj=content;
                            handler.sendMessage(msg);
                            content = br.readLine();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Looper.prepare();
        revHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
               if(msg.what==0x345){
                   try{
                       os.write((msg.obj.toString()+"\r\n").getBytes("utf-8"));
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }
        };
        Looper.loop();

    }
}
