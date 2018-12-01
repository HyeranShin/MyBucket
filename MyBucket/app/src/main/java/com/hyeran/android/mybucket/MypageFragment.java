package com.hyeran.android.mybucket;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

public class MypageFragment extends Fragment {
    View view;
    int count = 0;

    private static int RESULT_LOAD_IMAGE = 1;

    Realm realm;
    RealmResults<BucketlistVO> bucketlistVO;
    RealmResults<BucketlistVO> bucketlistArchievedVO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        init();
        setAchievementStatus();

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

        final ImageButton buttonLoadImage = (ImageButton) view.findViewById(R.id.MyPageImageChange);
        buttonLoadImage.setVisibility(View.INVISIBLE);

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
        final CircleImageView imageView = (CircleImageView) view.findViewById(R.id.MyPageImage);
        if(showMyImageString == null) {
            imageView.setImageResource(R.drawable.initial_profile);
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
                buttonLoadImage.setVisibility(View.VISIBLE);
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
                buttonLoadImage.setVisibility(View.INVISIBLE);
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

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });


        ImageView rand_1 = (ImageView) view.findViewById(R.id.FirstRandomImage);
        ImageView rand_2 = (ImageView) view.findViewById(R.id.SecondRandomImage);
        ImageView rand_3 = (ImageView) view.findViewById(R.id.ThirdRandomImage);
        ImageView rand_4 = (ImageView) view.findViewById(R.id.FourthRandomImage);
        ImageView rand_5 = (ImageView) view.findViewById(R.id.FifthRandomImage);
        ImageView rand_6 = (ImageView) view.findViewById(R.id.SixthRandomImage);

        File folder = new File(Environment.getExternalStorageDirectory().toString());

        if(folder.exists()) {
            File[] allFiles = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return (s.endsWith(".jpg"));
                }
            });

            if(allFiles != null) {
                if(allFiles.length != 0) {
                    List<File> filelist = new ArrayList<File>(Arrays.asList(allFiles));
                    Random rand = new Random();
                    int rndInt = rand.nextInt(filelist.size());

                    if (filelist.size() == 0) {

                    } else if (filelist.size() == 1) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                    } else if (filelist.size() == 2) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                        filelist.remove(rndInt);
                        String stringuri2 = filelist.get(0).getAbsolutePath().toString();
                        Uri uri2 = Uri.parse(stringuri2);
                        rand_2.setImageURI(uri2);
                    } else if (filelist.size() == 3) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                        filelist.remove(rndInt);
                        int rndInt2 = rand.nextInt(filelist.size());
                        String stringuri2 = filelist.get(rndInt2).getAbsolutePath().toString();
                        Uri uri2 = Uri.parse(stringuri2);
                        rand_2.setImageURI(uri2);
                        filelist.remove(rndInt2);
                        String stringuri3 = filelist.get(0).getAbsolutePath().toString();
                        Uri uri3 = Uri.parse(stringuri3);
                        rand_3.setImageURI(uri3);
                    } else if (filelist.size() == 4) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                        filelist.remove(rndInt);
                        int rndInt2 = rand.nextInt(filelist.size());
                        String stringuri2 = filelist.get(rndInt2).getAbsolutePath().toString();
                        Uri uri2 = Uri.parse(stringuri2);
                        rand_2.setImageURI(uri2);
                        filelist.remove(rndInt2);
                        int rndInt3 = rand.nextInt(filelist.size());
                        String stringuri3 = filelist.get(rndInt3).getAbsolutePath().toString();
                        Uri uri3 = Uri.parse(stringuri3);
                        rand_3.setImageURI(uri3);
                        filelist.remove(rndInt3);
                        String stringuri4 = filelist.get(0).getAbsolutePath().toString();
                        Uri uri4 = Uri.parse(stringuri4);
                        rand_4.setImageURI(uri4);
                    } else if (filelist.size() == 5) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                        filelist.remove(rndInt);
                        int rndInt2 = rand.nextInt(filelist.size());
                        String stringuri2 = filelist.get(rndInt2).getAbsolutePath().toString();
                        Uri uri2 = Uri.parse(stringuri2);
                        rand_2.setImageURI(uri2);
                        filelist.remove(rndInt2);
                        int rndInt3 = rand.nextInt(filelist.size());
                        String stringuri3 = filelist.get(rndInt3).getAbsolutePath().toString();
                        Uri uri3 = Uri.parse(stringuri3);
                        rand_3.setImageURI(uri3);
                        filelist.remove(rndInt3);
                        int rndInt4 = rand.nextInt(filelist.size());
                        String stringuri4 = filelist.get(rndInt4).getAbsolutePath().toString();
                        Uri uri4 = Uri.parse(stringuri4);
                        rand_4.setImageURI(uri4);
                        filelist.remove(rndInt4);
                        String stringuri5 = filelist.get(0).getAbsolutePath().toString();
                        Uri uri5 = Uri.parse(stringuri5);
                        rand_5.setImageURI(uri5);
                    } else if (filelist.size() >= 6) {
                        String stringuri = filelist.get(rndInt).getAbsolutePath().toString();
                        Uri uri = Uri.parse(stringuri);
                        rand_1.setImageURI(uri);
                        filelist.remove(rndInt);
                        int rndInt2 = rand.nextInt(filelist.size());
                        String stringuri2 = filelist.get(rndInt2).getAbsolutePath().toString();
                        Uri uri2 = Uri.parse(stringuri2);
                        rand_2.setImageURI(uri2);
                        filelist.remove(rndInt2);
                        int rndInt3 = rand.nextInt(filelist.size());
                        String stringuri3 = filelist.get(rndInt3).getAbsolutePath().toString();
                        Uri uri3 = Uri.parse(stringuri3);
                        rand_3.setImageURI(uri3);
                        filelist.remove(rndInt3);
                        int rndInt4 = rand.nextInt(filelist.size());
                        String stringuri4 = filelist.get(rndInt4).getAbsolutePath().toString();
                        Uri uri4 = Uri.parse(stringuri4);
                        rand_4.setImageURI(uri4);
                        filelist.remove(rndInt4);
                        int rndInt5 = rand.nextInt(filelist.size());
                        String stringuri5 = filelist.get(rndInt5).getAbsolutePath().toString();
                        Uri uri5 = Uri.parse(stringuri5);
                        rand_5.setImageURI(uri5);
                        filelist.remove(rndInt5);
                        int rndInt6 = rand.nextInt(filelist.size());
                        String stringuri6 = filelist.get(rndInt6).getAbsolutePath().toString();
                        Uri uri6 = Uri.parse(stringuri6);
                        rand_6.setImageURI(uri6);
                    }
                }
            }
        }

        return view;
    }

    private void setAchievementStatus() {
        ImageView stamp = view.findViewById(R.id.iv_medal);
        stamp.setColorFilter(Color.parseColor("#FFCD12"));
        TextView tvTotal = view.findViewById(R.id.total_bucket_mypage);
        tvTotal.setText(bucketlistVO.size()+"");

        TextView tvArchieved = view.findViewById(R.id.archieved_bucket_mypage);
        bucketlistArchievedVO = realm.where(BucketlistVO.class).equalTo("state", 2).findAll();
        tvArchieved.setText(bucketlistArchievedVO.size()+"");

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

    // Realm 초기화
    private void init() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance(); // 쓰레드의 Realm 인스턴스 얻기
        // 데이터 세팅
        bucketlistVO = realm.where(BucketlistVO.class).findAll();
    }
}
