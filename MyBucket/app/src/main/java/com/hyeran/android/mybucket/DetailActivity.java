package com.hyeran.android.mybucket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class DetailActivity extends AppCompatActivity {

    int count = 0;

    int startday;
    int startmonth;
    int startyear;

    int endday;
    int endmonth;
    int endyear;

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int count4 = 0;
    int count5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("자세히 보기");

        Button modifyBtn = findViewById(R.id.DetailModifyButton);

        final ViewSwitcher switcher = (ViewSwitcher)findViewById(R.id.TItleSwitcher);
        final EditText titleEditText = (EditText)findViewById(R.id.DetailTItleEdit);
        final TextView titleTextView = (TextView)findViewById(R.id.DetailTitle);

        final ViewSwitcher switcher2 = (ViewSwitcher)findViewById(R.id.ContentSwitcher);
        final EditText contentEditText = (EditText)findViewById(R.id.DetailContentEdit);
        final TextView contentTextView = (TextView)findViewById(R.id.DetailContent);

        final ViewSwitcher switcher3 = (ViewSwitcher) findViewById(R.id.PersonSwitcher);
        final TextView personTextView = (TextView) findViewById(R.id.DetailPerson);
        final EditText personEditText = (EditText) findViewById(R.id.DetailPersonEdit);

        final ViewSwitcher switcher4 = (ViewSwitcher) findViewById(R.id.PlaceSwitcher);
        final TextView placeTextView = (TextView) findViewById(R.id.DetailPlace);
        final EditText placeEditText = (EditText) findViewById(R.id.DetailPlaceEdit);

        final ViewSwitcher switcher5 = (ViewSwitcher) findViewById(R.id.HashTag1Switcher) ;
        final TextView hashtag1TextView = (TextView) findViewById(R.id.DetailHashtag1);
        final EditText hashtag1EditText = (EditText) findViewById(R.id.HastTag1EditText);

        final ViewSwitcher switcher6 = (ViewSwitcher) findViewById(R.id.HastTag2Switcher) ;
        final TextView hashtag2TextView = (TextView) findViewById(R.id.DetailHashtag2);
        final EditText hashtag2EditText = (EditText) findViewById(R.id.HastTag2EditText);

        final ViewSwitcher switcher7 = (ViewSwitcher) findViewById(R.id.HashTag3Switcher) ;
        final TextView hashtag3TextView = (TextView) findViewById(R.id.DetailHashtag3);
        final EditText hashtag3EditText = (EditText) findViewById(R.id.HashTag3EditText);

        final ViewSwitcher switcher8 = (ViewSwitcher) findViewById(R.id.OpinionSwitcher);
        final TextView opinionTextView = (TextView) findViewById(R.id.DetailOpinion);
        final EditText opinionEditText = (EditText) findViewById(R.id.DetailOpinionEditText);

        final ImageButton plusImageBtn = (ImageButton) findViewById(R.id.DetailImagePlusBtn);
        final ImageButton plusvideoBtn = (ImageButton) findViewById(R.id.DetailVideoPlusBtn);

        final ImageButton detailstar1 = (ImageButton) findViewById(R.id.DetailStar1);
        final ImageButton detailstar2 = (ImageButton) findViewById(R.id.DetailStar2);
        final ImageButton detailstar3 = (ImageButton) findViewById(R.id.DetailStar3);
        final ImageButton detailstar4 = (ImageButton) findViewById(R.id.DetailStar4);
        final ImageButton detailstar5 = (ImageButton) findViewById(R.id.DetailStar4);

        detailstar1.setEnabled(false);
        detailstar2.setEnabled(false);
        detailstar3.setEnabled(false);
        detailstar4.setEnabled(false);
        detailstar5.setEnabled(false);

        plusImageBtn.setVisibility(View.INVISIBLE);
        plusvideoBtn.setVisibility(View.INVISIBLE);

        String[] category = {"Goal", "Learning", "Travel", "WishList", "Sharing", "Etc"};

        final Spinner categorySpinner = findViewById(R.id.DetailCategorySpinner);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        categorySpinner.setAdapter(adapter);


        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titleEditText.setText(titleTextView.getText().toString());

                contentEditText.setText(contentTextView.getText().toString());

                personEditText.setText(personTextView.getText().toString());

                placeEditText.setText(placeTextView.getText().toString());

                hashtag1EditText.setText(hashtag1TextView.getText().toString());

                hashtag2EditText.setText(hashtag2TextView.getText().toString());

                hashtag3EditText.setText(hashtag3TextView.getText().toString());

                opinionEditText.setText(opinionTextView.getText().toString());

                plusImageBtn.setVisibility(View.VISIBLE);
                plusvideoBtn.setVisibility(View.VISIBLE);


                if(count % 2 == 0) {switcher.showNext(); switcher2.showNext(); switcher3.showNext(); switcher4.showNext(); switcher5.showNext(); switcher6.showNext(); switcher7.showNext(); switcher8.showNext(); count++;}

                ViewSwitcher btnSwitcher = findViewById(R.id.BtnSwitcher);
                btnSwitcher.showNext();

                detailstar1.setEnabled(true);
                detailstar2.setEnabled(true);
                detailstar3.setEnabled(true);
                detailstar4.setEnabled(true);
                detailstar5.setEnabled(true);

                detailstar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(count1 % 2 == 0) {detailstar1.setColorFilter(getResources().getColor(R.color.staryellow)); count1++;}
                        else {detailstar1.setColorFilter(null); count1++;}
                    }
                });

                detailstar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(count2 % 2 == 0) {detailstar2.setColorFilter(getResources().getColor(R.color.staryellow)); count2++;}
                        else {detailstar2.setColorFilter(null); count2++;}
                    }
                });

                detailstar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(count3 % 2 == 0) {detailstar3.setColorFilter(getResources().getColor(R.color.staryellow)); count3++;}
                        else {detailstar3.setColorFilter(null); count3++;}
                    }
                });

                detailstar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(count4 % 2 == 0) {detailstar4.setColorFilter(getResources().getColor(R.color.staryellow)); count4++;}
                        else {detailstar4.setColorFilter(null); count4++;}
                    }
                });

                detailstar5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(count5 % 2 == 0) {detailstar5.setColorFilter(getResources().getColor(R.color.staryellow)); count5++;}
                        else {detailstar5.setColorFilter(null); count5++;}
                    }
                });
            }
        });

        Button saveBtn = (Button)findViewById(R.id.DetailSaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePickerStart = findViewById(R.id.DetailDatePickerStart);

                startday = datePickerStart.getDayOfMonth();
                startmonth = datePickerStart.getMonth();
                startyear = datePickerStart.getYear();
                datePickerStart.updateDate(startyear, startmonth, startday);

                DatePicker datePickerEnd = findViewById(R.id.DetailDatePickerEnd);

                endday = datePickerEnd.getDayOfMonth();
                endmonth = datePickerEnd.getMonth();
                endyear = datePickerEnd.getYear();
                datePickerEnd.updateDate(endyear, endmonth, endday);

                titleTextView.setText(titleEditText.getText().toString());

                contentTextView.setText(contentEditText.getText().toString());

                personTextView.setText(personEditText.getText().toString());

                placeTextView.setText(placeEditText.getText().toString());

                hashtag1TextView.setText(hashtag1EditText.getText().toString());

                hashtag2TextView.setText(hashtag2EditText.getText().toString());

                hashtag3TextView.setText(hashtag3EditText.getText().toString());

                opinionTextView.setText(opinionEditText.getText().toString());

                if(count % 2 == 1 ) {switcher.showNext(); switcher2.showNext();switcher3.showNext(); switcher4.showNext(); switcher5.showNext(); switcher6.showNext(); switcher7.showNext(); switcher8.showNext();count++;}

                ViewSwitcher btnSwitcher = findViewById(R.id.BtnSwitcher);
                btnSwitcher.showNext();

                plusImageBtn.setVisibility(View.INVISIBLE);
                plusvideoBtn.setVisibility(View.INVISIBLE);

                String text = categorySpinner.getSelectedItem().toString();
                int spinnerposition = 0;
                if(text == "Goal") { spinnerposition = 0;}
                else if(text == "Learning") {spinnerposition = 1;}
                else if(text == "Travel") {spinnerposition = 2;}
                else if(text == "WishList") {spinnerposition = 3;}
                else if(text == "Sharing") {spinnerposition = 4;}
                else if(text == "Etc") {spinnerposition = 5;}
                categorySpinner.setSelection(spinnerposition);

                detailstar1.setEnabled(false);
                detailstar2.setEnabled(false);
                detailstar3.setEnabled(false);
                detailstar4.setEnabled(false);
                detailstar5.setEnabled(false);
            }
        });


    }
}
