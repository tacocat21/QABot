Here there are the instructions to launch our application:

STEP 1) Launch elastic search on your machine. From the root folder of the project execute the following command: 
sh run_elasticsearch.sh

STEP 2) Load the dataset. From the root folder of the project execute the following command : 
sh load_dataset.sh

STEP 3) Launch the classifier server. It's required to have the following python library: python3, flask v1.0.2, numpy v1.14.2 and scikit-learn 0.19.1. Then open a new terminal and from the root folder run: 
sh run_server_classifier.sh

The server may require a minute to be ready. If something goes wrong with this step look at the end of this file.

STEP 4) Launch the backend java server. It's required to have installed the tomcat server and maven. Then open a new terminal and from the root folder of the project run: 
sh run_backend.sh

STEP 5) Finally open a new terminal and from the root folder of the project run: 
sh run_frontend.sh 

This last script requires firefox to be installed, if not, you can open the file "public/index.html" using any browser.



-------------------------------------------

If something goes wrong trying to launch the python server you can try to retrain the classifier. From the root folder of the project run:
python3 classifier/classifier_tuning.py
