package izenka.hfad.com.bookstore.account;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import izenka.hfad.com.bookstore.R;

public class UserRegistrationFragment extends Fragment implements RegistrationFragmentNavigator {

    private EditText etUserEmail;
    private EditText etUserPassword;

    private Button btnCreateAccount;
    private Button btnSignIn;

    private TextView tvRegistrationInfo;


    private AccountViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etUserEmail = view.findViewById(R.id.etUserEmail);
        etUserPassword = view.findViewById(R.id.etUserPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        tvRegistrationInfo = view.findViewById(R.id.tvRegistrationInfo);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        viewModel.setRegistrationFragmentNavigator(this);
        btnCreateAccount.setOnClickListener(btn -> {
            if (validateForm()) {
                viewModel.createAccount(getActivity(), etUserEmail.getText().toString(), etUserPassword.getText().toString());
            }
        });
        btnSignIn.setOnClickListener(btn -> {
            if (validateForm()) {
                viewModel.signIn(getActivity(), etUserEmail.getText().toString(), etUserPassword.getText().toString());
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etUserEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etUserEmail.setError("Required.");
            valid = false;
        } else {
            etUserEmail.setError(null);
        }

        String password = etUserPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etUserPassword.setError("Required.");
            valid = false;
        } else {
            etUserPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onSuccessfulRegistration() {
        tvRegistrationInfo.setText("Registration succeed !");
    }

    @Override
    public void onFailedRegistration() {
        tvRegistrationInfo.setText("Registration failed !");
    }
}
