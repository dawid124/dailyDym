package pl.webd.dawid124.dailygym.layout.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.engine.TExerciseTypeEngine;
import pl.webd.dawid124.dailygym.enum_type.ExerciseTypeEnum;

public class CreateExerciseActivity extends AppCompatActivity {
    ImageView createFotoBtn;
    Button saveBtn;
    EditText nameField;
    EditText descriptionsField;
    Spinner typeSpinner;
    String mCurrentPhotoPath;
    File photo;
    Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise_activity);

        createFotoBtn = (ImageView) findViewById(R.id.exercisePhoto);
        saveBtn = (Button) findViewById(R.id.exerciseSave);
        nameField = (EditText) findViewById(R.id.exerciseName);
        descriptionsField = (EditText) findViewById(R.id.exerciseDesc);
        typeSpinner = (Spinner) findViewById(R.id.exerciseType);

        typeSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, ExerciseTypeEnum.getFullName(ExerciseTypeEnum.values())));

        createFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }


    public void createExercise(View view) {
        String name = nameField.getText().toString();
        String descriptions = descriptionsField.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        if (name.length() == 0) {
            Toast.makeText(getBaseContext(), "Wprowadż nazwę", Toast.LENGTH_LONG).show();
        } else if (type.equals("Typ ćwiczenia")) {
            Toast.makeText(getBaseContext(), "Wprowadż typ ćwiczenia", Toast.LENGTH_LONG).show();
        } else {
            TExerciseTypeEngine exerciseTypeEngine = new TExerciseTypeEngine(this);
            exerciseTypeEngine.createExercise(name, descriptions, ExerciseTypeEnum.Get(type), photoBitmap);
            finish();
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                photo = photoFile;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap imageBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photo.getPath()), 370, 370);
            photoBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photo.getPath()), 640, 480);
            createFotoBtn.setImageBitmap(photoBitmap);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(448, 336);
            layoutParams.setMargins(0, 10, 10, 10);
            createFotoBtn.setLayoutParams(layoutParams);
            createFotoBtn.setPadding(0, 0, 0, 0);
        }
    }
}
