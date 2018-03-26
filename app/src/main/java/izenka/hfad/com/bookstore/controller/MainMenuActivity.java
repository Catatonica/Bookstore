package izenka.hfad.com.bookstore.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.helper.Helper;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;
import stanford.androidlib.SimpleActivity;

public class MainMenuActivity extends SimpleActivity {

    public static final String FIREBASE_USERNAME = "izenka666@gmail.com";
    public static final String FIREBASE_PASSWORD = "sepultura777";

    public static Set<String> stringSet = new HashSet<>();
    public static Set<String> ordersSet = new HashSet<>();


    private FirebaseAuth mAuth;
    //  private ArrayList<String> categoriesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(FIREBASE_USERNAME, FIREBASE_PASSWORD);

        $TV(R.id.tvRunningLine).setSelected(true);

        final Activity activity = this;
        final EditText etSearch = $ET(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Helper.hideKeyboard(activity, etSearch);
                    GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, -1);
                    //getBooks(categoryID);
                    return true;
                }
                return false;
            }
        });

        new Thread( // создаём новый поток
                    new Runnable() { // описываем объект Runnable в конструкторе
                        public void run() {
                            GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, -1);
                        }
                    }
        );
       // GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, -1);

        setCategoriesNames();

    }


    private void setCategoriesNames() {

        Button[] btnArray = {
                $B(R.id.btnForeign),
                $B(R.id.btnKid),
                $B(R.id.btnBusieness),
                $B(R.id.btnFiction),
                $B(R.id.btnStudy),
                $B(R.id.btnNonfiction)
        };

        String[] stringArray = getResources().getStringArray(R.array.categoriesNames);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setText(stringArray[i]);
            btnArray[i].setTextSize(36);
            btnArray[i].setTypeface(Typeface.createFromAsset(
                    getAssets(), "fonts/5.ttf"));
        }
    }

    public void onKindOfBookClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);

        Intent intent = new Intent(this, BookListActivity.class);
        switch (view.getId()) {
            case R.id.btnBusieness:
                //  intent.putExtra("categoryName", categoriesNames.get(2));
                intent.putExtra("categoryID", 2);
                break;
            case R.id.btnFiction:
                //  intent.putExtra("categoryName", categoriesNames.get(3));
                intent.putExtra("categoryID", 3);
                break;
            case R.id.btnForeign:
                //  intent.putExtra("categoryName", categoriesNames.get(0));
                intent.putExtra("categoryID", 0);
                break;
            case R.id.btnKid:
                //  intent.putExtra("categoryName", categoriesNames.get(1));
                intent.putExtra("categoryID", 1);
                break;
            case R.id.btnNonfiction:
                //   intent.putExtra("categoryName", categoriesNames.get(5));
                intent.putExtra("categoryID", 5);
                break;
            case R.id.btnStudy:
                //   intent.putExtra("categoryName", categoriesNames.get(4));
                intent.putExtra("categoryID", 4);
                break;
        }
        view.startAnimation(anim);
        startActivity(intent);
    }

    public void onShopCartClick(View view) {
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim2);
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
    }

    public void onSearchClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("categoryID", -1);
        startActivity(intent);
    }

    public void onOrdersClick(View view) {
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim2);
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate3, R.anim.alpha2);

    }

    public void onShowInfoAboutCompany(View view) {
        //Intent intent=new Intent(this,CompanyInfoActivity.class);
        //startActivity(intent);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.company_info, null))

               .setCancelable(true)

               .show();
    }

    public void onBtnOKClick(View view) {

    }

    public void onBtnScanClick(View view) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);

    }

/*
    private void setCategoriesNames(){
        categoriesNames=new ArrayList<>();
        final ArrayList<String> catPicsPaths=new ArrayList<>();
        DatabaseReference fb= FirebaseDatabase.getInstance().getReference();
        DatabaseReference cat=fb.child("bookstore/category");
        Query query=cat.orderByChild("category_id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                if(!data.hasChildren()){
                    return;
                }
                for(DataSnapshot child: data.getChildren()){
                    Category category=child.getValue(Category.class);
                    String categoryName=category.category_name;
                    String imagePath=category.image_path;
                    categoriesNames.add(categoryName);
                    catPicsPaths.add(imagePath);
                }
                setButtonText(categoriesNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error"," Event Listener error");
            }
        });
    }

    private void setButtonImages(ArrayList<String> images){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReferenceFromUrl(images.get(0));
        log("imageRef"+imageRef);

        Button btnForeign=(Button) findViewById(R.id.btnForeign);
        ImageView imageView=new ImageView(this);

        Glide.with(this )
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imageView);
        btnForeign.setBackground(imageView.getDrawable());

    } */
/*
    private void setButtonText(ArrayList<String> categoryName){
        List<Button> btnArray=new ArrayList<>();
        btnArray.add($B(R.id.btnForeign));
        btnArray.add($B(R.id.btnKid));
        btnArray.add($B(R.id.btnBusieness));
        btnArray.add($B(R.id.btnFiction));
        btnArray.add($B(R.id.btnStudy));
        btnArray.add($B(R.id.btnNonfiction));

        for(int i=0; i<btnArray.size(); i++){
            btnArray.get(i).setText(categoryName.get(i));
            btnArray.get(i).setTypeface(Typeface.createFromAsset(
                    getAssets(), "fonts/5.ttf"));
            btnArray.get(i).setTextSize(36);
        }
    }
    */
}