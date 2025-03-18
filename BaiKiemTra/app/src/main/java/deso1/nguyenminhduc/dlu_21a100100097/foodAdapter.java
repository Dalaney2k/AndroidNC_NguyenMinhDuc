package deso1.nguyenminhduc.dlu_21a100100097;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;


public class foodAdapter extends RecyclerView.Adapter<foodAdapter.ViewHolder> {
    private List<food>foodList;
    private OnItemClickListener mListener;
    private Context mContext;
    private DatabaseHelper dbHelper;


    public foodAdapter(List<food> foodList, Context mContext, DatabaseHelper dbHelper) {
        this.foodList = foodList;
        this.mContext = mContext;
        this.dbHelper = new DatabaseHelper(mContext);
    }
    public interface OnItemClickListener {
        void onItemClick(food mbook);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView foodImage;
        public TextView foodName;
        public TextView foodPrice;
        public ImageView edit;

        public ViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageFood);
            foodName= itemView.findViewById(R.id.txtTenfood);
            foodPrice = itemView.findViewById(R.id.txtGia);
            edit = itemView.findViewById(R.id.edit);
//            itemView.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION && mListener != null) {
//                    mListener.onItemClick(foodList.get(position));
//                }
//            });

            edit.setOnClickListener(v -> showOptionsDialog(mContext, foodList.get(getAdapterPosition())));

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View foodView = inflater.inflate(R.layout.item_food, parent, false);

        return new ViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        food mFood = foodList.get(position);

        holder.foodName.setText(mFood.getTenfood());
        holder.foodPrice.setText(String.valueOf(mFood.getGia()));

        String imageUrl = mFood.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            if (imageUrl.contains("drive.google.com")) {
                try {
                    if (imageUrl.contains("/d/")) {
                        String fileId = imageUrl.split("/d/")[1].split("/")[0]; // Lấy FILE_ID
                        imageUrl = "https://drive.google.com/uc?id=" + fileId;
                    } else {
                        throw new Exception("URL không đúng định dạng Google Drive");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    imageUrl = ""; // Đặt ảnh trống nếu lỗi
                }
            }

            Glide.with(mContext)
                    .load(imageUrl)
                    .placeholder(R.drawable.banh1)
                    .into(holder.foodImage);
        } else {
            holder.foodImage.setImageResource(R.drawable.banh1);
        }
    }

    private void showOptionsDialog(Context context, food mfood) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chọn tác vụ");
        builder.setItems(new CharSequence[]{"Sửa", "Xóa"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    Intent intent = new Intent(context, UpdateFoodActivity.class);
                    intent.putExtra("food_id", String.valueOf(mfood.getMafood()));
                    intent.putExtra("ten_food", mfood.getTenfood());
                    intent.putExtra("gia_food", mfood.getGia());
                    intent.putExtra("image_url", mfood.getImageUrl());
                    context.startActivity(intent);
                    break;
                case 1:
                    deleteFood(mfood);
                    break;
            }
        });
        builder.create().show();
    }

    private void deleteFood(food mFood) {
        dbHelper.deleteFood(Integer.parseInt(mFood.getMafood()));
        int position = foodList.indexOf(mFood);
        if (position != -1) {
            foodList.remove(position);
            notifyItemRemoved(position);
        }
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
