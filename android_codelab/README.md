This will be the notes of this [blog](https://codelabs.developers.google.com/codelabs/tensorflow-style-transfer-android/index.html#0)

### Intro:

[Artistic style transfer](https://arxiv.org/abs/1508.06576) is the ability to create a new image from:
    - A content Image
    - A style Image

![intro img](./imgs/intro.png)

The generated image is called [pastiche](https://en.wikipedia.org/wiki/Pastiche).


### Code:

The code source is available at this [link](https://github.com/googlecodelabs/tensorflow-style-transfer-android/archive/codelab-start.zip).

> Check the link to use the _Github_ link.

Open Android Studio, and import the project into it.

Now find yourself into __StylizeActivity__ class.



### Perform inference using TensorFlow:

To add the inference libraries and their dependencies:

1. Open `build.gradle`.
2. Within the `android` block add:
```
dependencies {
    compile 'org.tensorflow:tensorflow-android:1.2.0-preview'
}
```
3. Sync the file.


For Android, we are going to perfrom inference over a prebuit graph.  
The Java interface that manages the graph and sesion is: `TensorFlowInferenceInterface`.

> For more control use `Session` and `Graph` objects.


4. Open the __StylizedActivity__ class and above the declaration of `NUM_STYLES` add:
```
private TensorFlowInferenceInterface inferenceInterface;

private static final String MODEL_FILE = "file:///android_asset/stylize_quantized.pb";

private static final String INPUT_NODE = "input";
private static final String STYLE_NODE = "style_num";
private static final String OUTPUT_NODE = "transformer/expand/conv3/conv/Sigmoid";
```

> Where you see a `/`, you will need to __expand__ a node to see it's children.


5. Find `onPreviewSizeChosen` method, and add this line anywhere:
```
inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);
```

6. Find `stylizeImage` method, add add this code which will go between the two loops in the else block:
```
// Copy the input data into TensorFlow.
inferenceInterface.feed(INPUT_NODE, floatValues, 
        1, bitmap.getWidth(), bitmap.getHeight(), 3);
inferenceInterface.feed(STYLE_NODE, styleVals, NUM_STYLES);

// Execute the output node's dependency sub-graph.
inferenceInterface.run(new String[] {OUTPUT_NODE}, isDebug());

// Copy the data from TensorFlow back into our array.
inferenceInterface.fetch(OUTPUT_NODE, floatValues);
```


7. Run the App.
