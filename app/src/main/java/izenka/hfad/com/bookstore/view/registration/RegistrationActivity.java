package izenka.hfad.com.bookstore.view.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.presenter.RegistrationPresenter;
import stanford.androidlib.SimpleActivity;


public class RegistrationActivity extends SimpleActivity implements IRegistrationView {

    RegistrationPresenter presenter;

    private EditText etPhoneNumber;
    private EditText etName;
    private EditText etCity;
    private EditText etStreet;
    private EditText etHouse;
    private EditText etFlatNumber;
    private EditText etPorchNumber;
    private EditText etFloor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        if (presenter == null) {
            presenter = new RegistrationPresenter(this, intent, getSharedPreferences("ordersPref", MODE_PRIVATE));
        }

        presenter.onViewCreated();
    }

    @Override
    public void initViews() {
        $B(R.id.btnGoBack).setOnClickListener(view -> presenter.onBackClicked());
        findViewById(R.id.btnRegister).setOnClickListener(view -> presenter.onRegisterClicked(view));
        etPhoneNumber = $ET(R.id.etPhoneNumber);
        etName = $ET(R.id.etName);
        etCity = $ET(R.id.etCity);
        etStreet = $ET(R.id.etStreet);
        etHouse = $ET(R.id.etHouse);
        etFlatNumber = $ET(R.id.etFlatNumber);
        etPorchNumber = $ET(R.id.etPorchNumber);
        etFloor = $ET(R.id.etFloor);
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public void onRegisterClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);

        String phoneNumber = etPhoneNumber.getText().toString();
        String city = etCity.getText().toString();
        String street = etStreet.getText().toString();
        String house = etHouse.getText().toString();
        String flatNumber = etFlatNumber.getText().toString();
        String porchNumber = etPorchNumber.getText().toString();
        String floor = etFloor.getText().toString();
        String name = etName.getText().toString();

        if (phoneNumber.isEmpty() || city.isEmpty() || street.isEmpty() || house.isEmpty() || flatNumber.isEmpty()) {
            presenter.showToast();
        } else {
            presenter.writeNewOrder(phoneNumber, name, city, street, house, flatNumber, porchNumber, floor);
        }
    }

    @Override
    public void showSucceedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.succeed_message, null)).show();
    }

    public void onBtnOKClick(View view) {
        //TODO:remove books count from firebase
        SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
        presenter.onOkClicked(sp);
        finish();
    }

    public void onBtnToOrdersClick(View view) {
        presenter.onToOrdersClicked();
    }

    @Override
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {

    }
}
