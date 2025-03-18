package deso1.nguyenminhduc.dlu_21a100100097;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private foodAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView addFoodButton;
    private List<food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food);


        dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadFoodList();

        addFoodButton = findViewById(R.id.addFood);
        addFoodButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });
    }

    private void loadFoodList() {
        foodList = dbHelper.getAllFood();
        if (foodList.isEmpty()) {
            Toast.makeText(this, "Không có món ăn nào!", Toast.LENGTH_SHORT).show();
        }
        adapter = new foodAdapter(foodList, this, dbHelper);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFoodList();
    }
}
