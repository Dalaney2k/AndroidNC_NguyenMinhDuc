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

public class UpdateFoodActivity extends AppCompatActivity {
    private EditText edtTenFood, edtGiaFood, edtImageUrl;
    private ImageView imgFood;
    private Button btnLuu, btnHuy;
    private DatabaseHelper dbHelper;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        edtTenFood = findViewById(R.id.edtTenFood);
        edtGiaFood = findViewById(R.id.edtGiaFood);
        edtImageUrl = findViewById(R.id.edtImageUrl);
        imgFood = findViewById(R.id.imgFood);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        foodId = intent.getStringExtra("food_id");
        String tenFood = intent.getStringExtra("ten_food");
        int giaFood = intent.getIntExtra("gia_food", 0);
        String imageUrl = intent.getStringExtra("image_url");

        edtTenFood.setText(tenFood);
        edtGiaFood.setText(String.valueOf(giaFood));
        edtImageUrl.setText(imageUrl);

        Glide.with(this).load(imageUrl).into(imgFood);

        edtImageUrl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Glide.with(this).load(edtImageUrl.getText().toString()).into(imgFood);
            }
        });

        btnLuu.setOnClickListener(v -> updateFood());

        btnHuy.setOnClickListener(v -> finish());
    }

    private void updateFood() {
        String tenMoi = edtTenFood.getText().toString().trim();
        int giaMoi = Integer.parseInt(edtGiaFood.getText().toString().trim());
        String imageMoi = edtImageUrl.getText().toString().trim();

        if (tenMoi.isEmpty() || edtGiaFood.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenfood", tenMoi);
        values.put("gia", giaMoi);
        values.put("imageUrl", imageMoi);

        int rowsAffected = db.update("foods", values, "id=?", new String[]{foodId});
        db.close();

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
