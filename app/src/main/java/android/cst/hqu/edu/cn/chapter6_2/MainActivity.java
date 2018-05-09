package android.cst.hqu.edu.cn.chapter6_2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private EditText sendTxt,showTxt;
    private Button sendBtn;
    private Handler handler;
    private ClientThread clientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendTxt=findViewById(R.id.sendTxt);
        showTxt=findViewById(R.id.showTxt);
        sendBtn=findViewById(R.id.sendBtn);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0x123){
                    showTxt.append("\n"+msg.obj.toString());
                }
            }
        };
        clientThread=new ClientThread(handler);
        new Thread(clientThread).start();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                msg.what=0x345;
                msg.obj=sendTxt.getText().toString();
                clientThread.revHandler.sendMessage(msg);
                sendTxt.setText("");
            }
        });


    }
}
