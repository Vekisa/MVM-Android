package com.example.mvm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.example.mvm.modals.SuggestionProductionModal;
import com.example.mvm.model.Category;
import com.example.mvm.services.CategoryService;

public class ProductionSuggesting extends NavigationActivity {

    private ProgressDialog loading = null;
    private RadioGroup group1;
    private RadioGroup group2;
    private Integer selected1 = 0;
    private Integer selected2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Calculating ...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_production_suggesting, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_suggestion_production);

        group1 = (RadioGroup) findViewById(R.id.radioObjects);
        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                selected1 = radioGroup.indexOfChild(radioButton);
            }
        });

        group2 = (RadioGroup) findViewById(R.id.radioFields);
        group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                selected2 = radioGroup.indexOfChild(radioButton);
            }
        });
    }

    public void onFinishedClick(View v) throws InterruptedException {
        loading.show();
        Category category = CategoryService.findSuggestion(selected1,selected2);
        loading.dismiss();
        System.out.println("CAO");
        SuggestionProductionModal spm = new SuggestionProductionModal(category);
        spm.show(getSupportFragmentManager(),"Suggestion");
    }
}
