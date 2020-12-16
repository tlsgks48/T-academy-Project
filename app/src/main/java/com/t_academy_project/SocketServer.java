package com.t_academy_project;


import com.google.android.material.internal.FlowLayout;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {

        // swing 부분

        //JAVA Swing 을 통해서 통신하기
        JFrame frm = new JFrame("AP SERVER");
        frm.setBounds(100,100, 500, 100);
        frm.setLayout(new FlowLayout());

        JButton btn1 = new JButton("Send File");
        JButton btn2 = new JButton("Find File");
        JTextArea ta = new JTextArea(2,20);

        ServerSocket server_out = null;
        String path = "";
        val vv = new val(server_out, path);

        frm.add(btn1);
        frm.add(btn2);
        frm.add(ta);
        frm.setVisible(true);

        ta.setText("파일을 업로드해주세요");


        // 보내기 버튼을 클릭 했을 때 반응할 수 있도록 실행
        btn1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent ee){
                try {
                    Socket2 s2 = new Socket2(vv.serverSocket, vv.path);
                    Thread t2 = new Thread(s2);
                    t2.start();
                    ta.setText("파일을 보냈습니다.");
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        btn2.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
                JFileChooser jfc = new JFileChooser(); //FileChooser 선언
                jfc.setDialogTitle("Choose path"); //FileChooser 창 제목
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //DIRECTORIES_ONLY, FILES_ONLY, FILES_AND_DIRECTORIES
                int option = jfc.showSaveDialog(null); //FileChooser 창안의 버튼인덱스 반환
                if (option == JFileChooser.APPROVE_OPTION) { //승인 버튼
                    File f = jfc.getSelectedFile();
                    String path = f.getAbsolutePath(); // File f의 절대경로
                    ta.setText(path);
                    vv.path = path;
                } else { // 승인버튼 외(X, 취소)
                    System.out.println("저장 취소");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });


        //11001 & 11002 port를 열어둠
        try {
            vv.serverSocket = new ServerSocket(11002);
            ServerSocket server_in = new ServerSocket(11001);
            //ServerSocket server_in_image = new ServerSocket(11003);
            Socket1 s1 = new Socket1(server_in);
            //Socket3 s3 = new Socket3(server_in_image);
            Thread t1 = new Thread(s1);
            //Thread t3 = new Thread(s3);
            t1.start();
            //t3.start();
            System.out.println("Listening at port " + "11001 & 11002" + " ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class val{
    ServerSocket serverSocket;
    String path;
    public val(ServerSocket ss, String pp) {
        serverSocket = ss;
        path = pp;
    }
}

// Thread 부분

class Socket1 implements Runnable{
    ServerSocket serverSock;
    public Socket1(ServerSocket s) {
        serverSock = s;
    }
    public void run() {
        try {
            String path = "C:\\AP";
            File folder = new File (path);
            Writer output = null;
            //ServerSocket serverSock = new ServerSocket(5001);
            while(true) {
                Socket sock = serverSock.accept();
                InetAddress clientHost = sock.getLocalAddress();
                int clientPort = sock.getPort();
                System.out.println("A client connectecd. host : " + clientHost + ", port : " + clientPort);

                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                JSONObject obj2 =(JSONObject) instream.readObject();
                String obj = obj2.toJSONString().trim();
                System.out.println("받은 데이터 : " + obj);


                if(!folder.exists()) {
                    folder.mkdir();
                }
                File file = new File (folder + "/data.json");
                output = new BufferedWriter(new FileWriter(file));
                output.write(obj.toString());
                output.close();
				*/
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}




class Socket2 implements Runnable{
    ServerSocket serverSock2;
    String path;
    JSONParser parser = new JSONParser();
    public Socket2(ServerSocket s, String p) {
        serverSock2 = s;
        path = p;
    }
    public void run() {
        try {
            System.out.println(path);
            Socket sock2 = serverSock2.accept();
            JSONObject obj2 = (JSONObject) parser.parse(new FileReader(path));
            //ServerSocket serverSock2 = new ServerSocket(5002);
            System.out.println("파일을 보냈습니다");
            //while(true) { //클라이언트 연결 대기하기
            ObjectOutputStream outstream = new ObjectOutputStream(sock2.getOutputStream());
            outstream.writeObject(obj2);
            outstream.flush();
            System.out.println("보낼 데이터 : " + path + " from Server.");
            sock2.close();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("파일이 잘못 되었네요.");
        }
    }
}

