package com.t_academy_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


public class F_menu3 extends Fragment {

    EditText editText;
    TextView textView;

    public F_menu3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.f_menu3,container,false);

        editText=rootview.findViewById(R.id.editText);
        textView=rootview.findViewById(R.id.textView);

        final Button clickSetBt = (Button) rootview.findViewById(R.id.clickSetBt);
        clickSetBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  // Set버튼 클릭 시    SharedPreferences에 값 저장.
                if(editText.getText().toString().isEmpty()){ // 공백 또는 size=0이면
                    Toast.makeText(getActivity(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정
                    SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                    editor.putString("inputText",editText.getText().toString()); // key,value 형식으로 저장
                    editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                    Toast.makeText(getActivity(), "저장되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final Button clickGetBt = (Button) rootview.findViewById(R.id.clickGetBt);
        clickGetBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  // Get버튼 클릭 시   SharedPreferences에 값 불러오기.
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
                String inputText = sharedPreferences.getString("inputText","");
                textView.setText(inputText);    // TextView에 SharedPreferences에 저장되어있던 값 찍기.
                Toast.makeText(getActivity(), "불러오기 하였습니다..", Toast.LENGTH_SHORT).show();
            }
        });

        return rootview;
    }

}
