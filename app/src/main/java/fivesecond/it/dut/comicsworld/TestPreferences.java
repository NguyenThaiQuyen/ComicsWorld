package fivesecond.it.dut.comicsworld;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TestPreferences extends AppCompatActivity {

    EditText edtPage;
    EditText edtComic;
    EditText edtChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_preferences);

        init();
    }

    private void init() {

    }

    public void savePrefer(View view) {
        try
        {
            edtComic = findViewById(R.id.edtComic);
            edtPage = findViewById(R.id.edtPage);
            edtChap = findViewById(R.id.edtChap);

            int idComic = Integer.parseInt(edtComic.getText().toString());
            int page = Integer.parseInt(edtPage.getText().toString());
            int chap = Integer.parseInt(edtChap.getText().toString());

            SharedPreferences pre = getApplicationContext().getSharedPreferences ("current_data",MODE_PRIVATE);
            SharedPreferences.Editor editor = pre.edit();
            editor.clear();
            editor.putInt("id", idComic);
            editor.putInt("page", page);
            editor.putInt("chap", chap);
            editor.apply();
            System.out.println("ok");
        }catch (Exception e)
        {

            System.out.println("not ok");


        }


    }
}
