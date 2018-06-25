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
import android.widget.TextView;

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
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        etHouse = view.findViewById(R.id.etHouse);
        etFlat = view.findViewById(R.id.etFlat);
        etPhone = view.findViewById(R.id.etPhone);
        etCity = view.findViewById(R.id.etCity);
        etStreet = view.findViewById(R.id.etStreet);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AccountViewModel viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
//        viewModel.loadUser();
        viewModel.getUserLiveData().observe(this , user->{
            Log.d("userInfo", "observeUserLiveData, user = "+user);
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
            }
        });
        tvEmail.setText(viewModel.getUser().getEmail());
        btnSaveChanges.setOnClickListener(btn -> {
            viewModel.saveChanges(etName.getText().toString(),
                                  etSurname.getText().toString(),
                                  etPhone.getText().toString(),
                                  etCity.getText().toString(),
                                  etStreet.getText().toString(),
                                  etHouse.getText().toString(),
                                  etFlat.getText().toString()

            );
        });
        btnSignOut.setOnClickListener(btn -> {
            viewModel.signOut();
        });
    }
}
