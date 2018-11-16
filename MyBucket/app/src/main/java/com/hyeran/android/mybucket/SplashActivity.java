package com.hyeran.android.mybucket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView icon = findViewById(R.id.iv_icon_splash);
        icon.setColorFilter(Color.parseColor("#AA1212"));

        // 명언 배열(20개)
        String[] famous_saying = {
                "가장 중요한 약속은 \n스스로 하겠다고 다짐한 약속이다.",
                "똑같은 상황임에도 불구하고 \n내 기분에 따라 좋고 나쁨도 결정됩니다.\n\n상황을 바꿀 수 없다면 \n상황을 바라보는 마음가짐을 바꿔봅시다.",
                "웃을 준비를 하고 있으면 웃을 일이 더 빨리 온다.",
                "누군가 당신을 예로 들 때 그것은 어떤 예일까?",
                "순간순간 내리는 선택은, \n결국 자신을 선택하는 과정이다.",
                "하루는 지나가는 것이 아니라 쌓여가는 것입니다.\n하루하루 쌓여간다는 것 잊지 마세요!",
                "인생은 남과 비교하는 것이 아니라 \n어제의 나와 비교하는 것입니다.\n\n인생에서 중요한 것은 속도가 아니라 방향입니다.",
                "당신이 거둔 것으로 하루를 판단하지 말고\n당신이 뿌린 것으로 판단하라.",
                "늘 자신을 설레게 하는 쪽으로 가는 거야.",
                "남들이 보기에 화려하지 않아도 \n내게 중요한 일이라면 그걸 해야 한다.",
                "목표를 세워 그 길을 가라. 좌절도 있고 어려움도 있으리라.\n그러나 다시 일어나 그 길을 가라.",
                "버킷 리스트는 당신의 삶의 매 순간에 최선을 다할 수 있도록\n이끌어주는 이정표가 되어줄 것이다.",
                "힘들기만 한 일이면 그만두세요.\n그러나 힘든 만큼의 가치가 있다면 계속하세요.",
                "시작하는 방법은 그냥 시작하면 된다.",
                "남다른 도전만이 남다른 도약을 할 수 있으며,\n남다른 도약이 남다른 성취를 가져온다",
                "같은 날의 반복에서는 다른 것이 나올 수가 없다.",
                "오늘 하면 한 번만 하면 될 텐데 내일로 미루면 미루는 동안\n머릿속으로는 수십 번 그 일을 반복하게 될 것이다.",
                "1분마다 인생을 바꿀 수 있는 기회가 온다.",
                "인생을 바꾸는 변화는 노력 없이 생겨나지 않아요.\n변화하고 싶다면 노력하세요.",
                "해보고 후회하는 것이 정답입니다.\n미련은 남지 않으니까요."
        };

        // 명언 배열에서 출력할 index를 관리하는 SharedPreferences
        SharedPreferences pref = getSharedPreferences("famous_saying", MODE_PRIVATE);
        int index = pref.getInt("index", 0);
        TextView tv_famous_saying = findViewById(R.id.tv_famous_saying_splash);
        tv_famous_saying.setText(famous_saying[index]);
        SharedPreferences.Editor editor = pref.edit();
        if(++index > 19) index = 0;
        editor.putInt("index", index);
        editor.commit();

        // 3초 뒤 MainActivity로 이동
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
