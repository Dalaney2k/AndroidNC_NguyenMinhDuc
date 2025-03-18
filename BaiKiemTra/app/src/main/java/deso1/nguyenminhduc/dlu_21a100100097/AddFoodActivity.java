package deso1.nguyenminhduc.dlu_21a100100097;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class AddFoodActivity extends AppCompatActivity {
    private EditText edtTenFood, edtGiaFood, edtImageUrl;
    private ImageView imgFood;
    private Button btnLuu, btnHuy;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        edtTenFood = findViewById(R.id.edtTenFood);
        edtGiaFood = findViewById(R.id.edtGiaFood);
        edtImageUrl = findViewById(R.id.edtImageUrl);
        imgFood = findViewById(R.id.imgFood);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        dbHelper = new DatabaseHelper(this);

        edtImageUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Glide.with(this).load(edtImageUrl.getText().toString()).into(imgFood);
            }
        });

        btnLuu.setOnClickListener(v -> addFood());

        btnHuy.setOnClickListener(v -> finish());
    }

    private void addFood() {
        String tenFood = edtTenFood.getText().toString().trim();
        String giaText = edtGiaFood.getText().toString().trim();
        String imageUrl = edtImageUrl.getText().toString().trim();

        if (tenFood.isEmpty() || giaText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int giaFood = Integer.parseInt(giaText);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenfood", tenFood);
        values.put("gia", giaFood);
        values.put("imageUrl", imageUrl);

        long newRowId = db.insert("foods", null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thêm món ăn thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
