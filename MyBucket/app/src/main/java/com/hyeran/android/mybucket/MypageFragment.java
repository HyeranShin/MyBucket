package com.hyeran.android.mybucket;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MypageFragment extends Fragment {
    View view;
    int count = 0;
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

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameEditText.setText(nameTextView.getText().toString());

                mottoEditText.setText(mottoTextView.getText().toString());

                if(count % 2 == 0) {switcher.showNext(); switcher2.showNext(); count++;}

                switcher3.showNext();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameTextView.setText(nameEditText.getText().toString());

                mottoTextView.setText(mottoEditText.getText().toString());

                if(count % 2 == 1) {switcher.showNext(); switcher2.showNext(); count++;}

                switcher3.showNext();
            }
        });
        return view;
    }
}
