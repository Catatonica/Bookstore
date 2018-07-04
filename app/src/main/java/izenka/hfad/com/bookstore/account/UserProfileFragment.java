package izenka.hfad.com.bookstore.account;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import izenka.hfad.com.bookstore.R;

public class UserProfileFragment extends Fragment {

    private Button btnSignOut;

    private TextView tvEmail;
    private EditText etName;
    private EditText etSurname;
    private EditText etHouse;
    private EditText etFlat;
    private EditText etCity;
    private EditText etPhone;
    private EditText etStreet;
    private Button btnSaveChanges;

    private ProgressBar pbLoadingProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        tvEmail = view.findViewById(R.id.tvEmail);
//        tvEmail.setText("");
        etName = view.findViewById(R.id.etName);
//        etName.setText("");
        etSurname = view.findViewById(R.id.etSurname);
//        etSurname.setText("");
        etHouse = view.findViewById(R.id.etHouse);
//        etHouse.setText("");
        etFlat = view.findViewById(R.id.etFlat);
//        etFlat.setText("");
        etPhone = view.findViewById(R.id.etPhone);
//        etPhone.setText("");
        etCity = view.findViewById(R.id.etCity);
//        etCity.setText("");
        etStreet = view.findViewById(R.id.etStreet);
//        etStreet.setText("");
        pbLoadingProgress = view.findViewById(R.id.pbLoadingProgress);
        pbLoadingProgress.setVisibility(View.VISIBLE);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AccountViewModel viewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel.class);
        viewModel.getUserLiveData().observe(this, user -> {
            pbLoadingProgress.setVisibility(View.GONE);
            Log.d("userInfo", "observeUserLiveData, user = " + user);
            if (user != null) {
                etName.setText(user.name);
                etSurname.setText(user.surname);
                etPhone.setText(user.phone);
                if (user.Address != null) {
                    etCity.setText((String) user.Address.get("city"));
                    etStreet.setText((String) user.Address.get("street"));
                    etHouse.setText((String) user.Address.get("house"));
                    etFlat.setText((String) user.Address.get("flat"));
                }

                tvEmail.setText(user.email);
                btnSaveChanges.setOnClickListener(btn -> {
                    user.name = etName.getText().toString();
                    user.surname = etSurname.getText().toString();
                    user.phone = etPhone.getText().toString();
                    Map<String, Object> Address = new HashMap<>();
                    Address.put("city", etCity.getText().toString());
                    Address.put("street", etStreet.getText().toString());
                    Address.put("house", etHouse.getText().toString());
                    Address.put("flat", etFlat.getText().toString());
                    user.Address = Address;
                    viewModel.saveChanges(user.toMap());
                });
            }
        });
        btnSignOut.setOnClickListener(btn -> {
            viewModel.signOut();
        });
    }
}
