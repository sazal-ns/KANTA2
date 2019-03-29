package com.brosolved.siddiqui.kanta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Category;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.viewModel.ProductViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ProductAddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;

    private File image1;
    private int catId;

    private ProductViewModel viewModel;
    private Categories mainCategories;

    private Spinner categorySpinner;
    private ImageButton imageButton;
    private Button saveButton;
    private TextInputLayout product, price, desc, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        categorySpinner = findViewById(R.id.categorySpinner);
        imageButton = findViewById(R.id.imageButton);
        saveButton = findViewById(R.id.addProduct);
        product = findViewById(R.id.productName);
        price = findViewById(R.id.price);
        desc = findViewById(R.id.description);
        quantity = findViewById(R.id.quantity);

        viewModel.getCategory().observe(this, new Observer<Categories>() {
            @Override
            public void onChanged(Categories categories) {
                if (categories != null){
                    mainCategories = categories;
                    ArrayAdapter<Category> arrayAdapter = new ArrayAdapter(ProductAddActivity.this, android.R.layout.simple_spinner_item, mainCategories.getData());
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(arrayAdapter);
                }

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catId = mainCategories.getData().get((int)id).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonTask.checkInput(product.getEditText().getText().toString()) && CommonTask.checkInput(price.getEditText().getText().toString()) && CommonTask.checkInput(desc.getEditText().getText().toString()) && image1.isFile() && CommonTask.checkInput(quantity.getEditText().getText().toString())){
                    Product theProduct = new Product();
                    theProduct.setName(product.getEditText().getText().toString().trim());
                    theProduct.setPrice(price.getEditText().getText().toString().trim());
                    theProduct.setDetails(desc.getEditText().getText().toString().trim());
                    theProduct.setQuantity(Integer.parseInt(quantity.getEditText().getText().toString().trim()));
                    theProduct.setProductCategoryId(String.valueOf(catId));
                    theProduct.setImageFile(image1);

                    viewModel.addProduct(theProduct).observe(ProductAddActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean)
                            {
                                CommonTask.showToast(ProductAddActivity.this, "Added");
                            }
                            else
                                CommonTask.showToast(ProductAddActivity.this, "Something Wrong");
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageButton.setImageResource(R.drawable.ic_check_black_24dp);
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            image1 = new File(filePath);
        }
    }
}
