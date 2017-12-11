import tensorflow as tf
import sys

from tensorflow.python.platform import gfile
from tensorflow.core.protobuf import saved_model_pb2
from tensorflow.python.util import compat


with tf.Session() as sess:
    # path of the graph
    model_filename ='/old_home/machine_learning/tensorflow/pix2pix/day2night.pb'

    with gfile.FastGFile(model_filename, 'rb') as f:
        graph_def = tf.GraphDef()
        graph_def.ParseFromString(f.read())
        g_in = tf.import_graph_def(graph_def)

# path of the log dir
# will be used when we run tensorbord --logdir
LOGDIR='/old_home/machine_learning/tensorflow/pix2pix/'

train_writer = tf.summary.FileWriter(LOGDIR)
train_writer.add_graph(sess.graph)
