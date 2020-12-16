package com.t_academy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Server_activity1 extends AppCompatActivity {

    EditText input01;
    TextView text2;
    Handler handler = new Handler();
    JSONObject obj;
    String sdcard = Environment.getExternalStorageDirectory()+"/AP/";
    File dir = new File(sdcard);
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_activity1);

        text2 = (TextView) findViewById(R.id.text01);
        try {
            String addr = "192.168.101.164";
            ListenThread thread2 = new ListenThread(addr);
            thread2.start();
        } catch(Exception e){
            Log.d("Client : ", "NO LISTENING");
            e.printStackTrace();
        }

        // 버튼 이벤트 처리
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String addr2 = input01.getText().toString().trim();
                String addr = "192.168.101.164";
                ConnectThread thread = new ConnectThread(addr);


                thread.start();

            }
        });

    }

    /**
     * 소켓 연결할 스레드 정의
     */
    class ConnectThread extends Thread {
        String hostname;
        public ConnectThread(String addr) {
            hostname = addr;
        }
        public void run() {
            try {
                JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(sdcard+"/data.json"));
                int port = 11001;
                Socket sock = new Socket(hostname, port);
                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                //outstream.writeObject(input01.getText().toString().trim());
                outstream.writeObject(obj);
                outstream.flush();
                Log.d("MainActivity", "서버로 보낼 메시지 : " + obj);
                sock.close();

            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    class ListenThread extends Thread {
        String hostname;
        public ListenThread(String addr) {
            hostname = addr;
        }
        Writer output = null;
        public void run() {
            try {
                while(true) {
                    int port = 11002;
                    Socket sock = new Socket(hostname, port);
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                    obj = (JSONObject)instream.readObject();
                    final String json = obj.toJSONString().trim();
                    //obj.put("name", "@서버에서왔쪄욤@");
                    //final String name = (String) obj.get("name");
                    Log.d("MainActivity", "서버에서 받은 메시지 : " + json);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //if(!dir.exists()){dir.mkdirs();}
                                File file = new File(dir + "/data.json");
                                output = new BufferedWriter(new FileWriter(file));
                                output.write(obj.toString());
                                output.close();
                            } catch(IOException e){
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), dir+"/data.json에 저장했어요", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                            text2.setText("받은 데이터 : " + json);
                        }
                    });
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }

    }
}
