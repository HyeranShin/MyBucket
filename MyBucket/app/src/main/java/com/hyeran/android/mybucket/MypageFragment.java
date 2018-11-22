package com.hyeran.android.mybucket;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MypageFragment extends Fragment {
    View view;
    int count = 0;

    private static int RESULT_LOAD_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        ImageButton modifyBtn = (ImageButton) view.findViewById(R.id.MypageModifyBtn);
        ImageButton saveBtn = (ImageButton) view.findViewById(R.id.MyPageSaveBtn);

        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.MyPageNameSwitcher);
        final TextView nameTextView = (TextView) view.findViewById(R.id.MypageName);
        final EditText nameEditText = (EditText) view.findViewById(R.id.MyPageNameEdit);

        final ViewSwitcher switcher2 = (ViewSwitcher) view.findViewById(R.id.MyPageMottoSwitcher);
        final TextView mottoTextView = (TextView) view.findViewById(R.id.MypageMotto);
        final EditText mottoEditText = (EditText) view.findViewById(R.id.MyPageMottoEdit);

        final ViewSwitcher switcher3 = (ViewSwitcher) view.findViewById(R.id.MyPageImageButtonSwticher);

        //SharedPreferences 사용
        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        //사용자가 마지막으로 지정한 이름, 다짐, 사진이 앱이 실행되었을때 표시되도록 한다.

        String showMyName = pref.getString("Myname", null);
        if(showMyName == null) {
            nameTextView.setText("이름 입력");
        } else {
            nameTextView.setText(showMyName);
        }
        String showMyMotto = pref.getString("MyMotto", null);
        if(showMyMotto == null) {
            mottoTextView.setText("다짐 입력");
        } else {
            mottoTextView.setText(showMyMotto);
        }
        String showMyImageString = pref.getString("MyImage", null);
        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.MyPageImage);
        if(showMyImageString == null) {
            imageView.setImageResource(R.drawable.charlie);
        } else {
            Bitmap showMyImage = decodeBase64(showMyImageString);
            imageView.setImageBitmap(showMyImage);
        }

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //수정 버튼이 눌리면 이름과 다짐을 수정할 수 있다.
                nameEditText.setText(nameTextView.getText().toString());
                mottoEditText.setText(mottoTextView.getText().toString());
                if(count % 2 == 0) {switcher.showNext(); switcher2.showNext(); count++;}
                switcher3.showNext(); //수정 버튼이 저장 버튼으로 바뀐다.
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //저장 버튼이 눌리면 수정한 이름과 다짐이 반영됨.
                nameTextView.setText(nameEditText.getText().toString());
                mottoTextView.setText(mottoEditText.getText().toString());
                if(count % 2 == 1) {switcher.showNext(); switcher2.showNext(); count++;}
                switcher3.showNext(); //저장 버튼이 수정 버튼으로 바뀐다.

                //이름과 다짐을 SharedPreferences에 저장.
                String name;
                name = nameTextView.getText().toString();
                String motto;
                motto = mottoTextView.getText().toString();
                editor.putString("Myname", name);
                editor.putString("MyMotto", motto);

                editor.commit();
            }
        });

        //프로필 사진 수정
        ImageButton buttonLoadImage = (ImageButton) view.findViewById(R.id.MyPageImageChange);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });


        return view;
    }

    //앨범에서 프로필 사진 선택해서 바꾸기.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.MyPageImage);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

                //앨범에서 선택한 사진을 SharedPreferences에 저장.
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("MyImage", encodeTobase64(selectedImage));
                editor.commit();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "사진을 선택하지 않았습니다.",Toast.LENGTH_LONG).show();
        }

    }

    //사진을 SharedPreferences에 저장하기 위해 필요
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
