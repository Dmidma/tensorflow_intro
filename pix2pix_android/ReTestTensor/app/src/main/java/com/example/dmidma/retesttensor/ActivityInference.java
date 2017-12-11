package com.example.dmidma.retesttensor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.IOException;
import java.io.InputStream;

public class ActivityInference {


    private static ActivityInference activityInferenceInstance;
    private TensorFlowInferenceInterface inferenceInterface;
    private static final String MODEL_FILE = "file:///android_asset/frozen1.pb";
    private static final String INPUT_NODE = "batch/fifo_queue"; // generator/encoder_1/conv/Pad
    private static final String OUTPUT_NODE = "encode_images/output_pngs/TensorArrayPack_1/TensorArrayGatherV2";
    private static AssetManager assetManager;

    public static ActivityInference getInstance(final Context context)
    {
        if (activityInferenceInstance == null)
        {
            activityInferenceInstance = new ActivityInference(context);
        }
        return activityInferenceInstance;
    }

    public ActivityInference(final Context context) {
        this.assetManager = context.getAssets();
        inferenceInterface = new TensorFlowInferenceInterface(assetManager, MODEL_FILE);
    }

    public Bitmap getActivityProb() throws IOException
    {
        // Let's starting by creating a cropped bitmap
        // Bitmap croppedBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);


        // get the image, set it to a bitmap and crop it.
        final InputStream inputStream = this.assetManager.open("img_test.png");
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 256, 256);


        // decalare the pixels array and fill it
        int[] intValues = new int[256 * 256];
        croppedBitmap.getPixels(intValues, 0, croppedBitmap.getWidth(), 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight());

        // set a float array of the RGB values of the image
        float[] rgbValues = new float[256 * 256 * 3];
        for (int i = 0; i < intValues.length; i++)  {
            final int val = intValues[i];
            rgbValues[i * 3] = ((val >> 16) & 0xFF) / 255.0f;
            rgbValues[i * 3 + 1] = ((val >> 8) & 0xFF) / 255.0f;
            rgbValues[i * 3 + 2] = (val & 0xFF) / 255.0f;
        }

        // feed it to the model
        /*
            - Name of the input node
            - the data assigned to that node
            - all following parameters are for size
         */
        inferenceInterface.feed(
                INPUT_NODE,
                rgbValues,
                1,
                croppedBitmap.getWidth(),
                croppedBitmap.getWidth(),
                3);
        // run it
        inferenceInterface.run(new String[] {OUTPUT_NODE});

        // decalre ouput array and fetch the result.
        float[] result = new float[256 * 256 * 3];
        inferenceInterface.fetch(OUTPUT_NODE, result);
        for (int i = 0; i < intValues.length; i++)  {
            intValues[i] =
                0xFF000000
                    | ((int) (result[i * 3] * 255)) << 16
                    | ((int) (result[i * 3] * 255)) << 8
                    | ((int) (result[i * 3] * 255));
        }

        // set the pixels of the bitmap and return it
        croppedBitmap.setPixels(intValues, 0, croppedBitmap.getWidth(), 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight());
        return croppedBitmap;
    }
}