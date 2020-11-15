package com.t_academy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu1 extends AppCompatActivity {

    int mainValue = 0;
    int backValue = 0;
    TextView mainText;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        Button b = (Button)findViewById(R.id.button1);
        final ImageView iv = (ImageView)findViewById(R.id.imageView1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils
                        .loadAnimation
                                (getApplicationContext(),
                                        R.anim.set_animation);
                iv.startAnimation(anim);
            }
        });

        mainText = (TextView)findViewById(R.id.mainvalue);
        backText = (TextView)findViewById(R.id.backvalue);

        // 스레즈 생성하고 시작
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();


    }

    // 버튼을 누르면 mainValue 증가
    public void mOnClick(View v){
        mainValue++;
        mainText.setText("MainValue:" + mainValue);
    }

    class BackThread extends Thread{
        @Override
        public void run() {
            while(true){
                backValue++;
                // 메인에서 생성된 Handler 객체의 sendEmpryMessage 를 통해 Message 전달
                handler.sendEmptyMessage(0);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    } // end class BackThread

    // '메인스레드' 에서 Handler 객체를 생성한다.
    // Handler 객체를 생성한 스레드 만이 다른 스레드가 전송하는 Message나 Runnable 객체를
    // 수신할수 있다.
    // 아래 생성된 Handler 객체는 handlerMessage() 를 오버라이딩 하여
    // Message 를 수진합니다.
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){   // Message id 가 0 이면
                backText.setText("BackValue:" + backValue); // 메인스레드의 UI 내용 변경
            }
        }
    };

} // end class
