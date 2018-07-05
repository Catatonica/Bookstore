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

import butterknife.BindView;
import butterknife.ButterKnife;
import izenka.hfad.com.bookstore.R;

public class UserRegistrationFragment extends Fragment implements RegistrationFragmentNavigator {

    @BindView(R.id.etUserEmail) EditText etUserEmail;
    @BindView(R.id.etUserPassword) EditText etUserPassword;
    @BindView(R.id.btnCreateAccount) Button btnCreateAccount;
    @BindView(R.id.btnSignIn) Button btnSignIn;
    @BindView(R.id.tvRegistrationInfo) TextView tvRegistrationInfo;

    private AccountViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_registration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel.class);
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
    public void onFailedRegistration() {
        tvRegistrationInfo.setText(R.string.registration_failed);
    }
}
