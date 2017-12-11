```
 _____                         _____ _               
|_   _|__ _ __  ___  ___  _ __|  ___| | _____      __
  | |/ _ \ '_ \/ __|/ _ \| '__| |_  | |/ _ \ \ /\ / /
  | |  __/ | | \__ \ (_) | |  |  _| | | (_) \ V  V / 
  |_|\___|_| |_|___/\___/|_|  |_|   |_|\___/ \_/\_/  
```                                                     
Some useful information regarding the TensorFlow library

#### Getting Started:

The first example this repo will go through will reside in the `getting_started/` directory.

#### Tensorflow Style Transfer:

The `android_codelad` contains the android project provided by Google in the Codelab. 


#### TensorBoard:

The `log_it.py` file is the python script that allow you to generate log data that can be used to visualze the graph with: `$ tensorboard --logdir path/to/log`.

You need to change both `model_filename` and `LOGDIR`. 

#### pix2pix Android:

The third directory `pix2pix_android/` contains the android implementation of pix2pix facades.

> Missing the trained model.
> Also we need to change the model to remove the unsupported operations manually (using Python) because the `optimize_for_inference` did not work.
