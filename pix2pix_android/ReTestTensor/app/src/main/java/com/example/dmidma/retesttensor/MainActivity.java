package com.example.dmidma.retesttensor;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityInference ai = ActivityInference.getInstance(getApplicationContext());
                try {
                    Bitmap b = ai.getActivityProb();

                    ImageUtils.saveBitmap(b, "img_test.png");

                    // save the bitmap somewhere
                    // ImageUtils.saveBitmap(croppedBitmap);

                    // display something
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    LOGGER.e("Error opening bitmap!", e);
                }
            }
        });
    }
}
