"""
Created on Mon May  9 15:55:57 2018

@author: riccardo
"""

from sklearn.externals import joblib
import json
from data_processing import get_word_dictionary, quest2vect

vector_dim = 50
datset_file = 'dataset/datasetStem.json'
word_vector_path = "data/glove.6B.50d.txt"
cls = joblib.load('data/random_forest.pkl')
output_file = 'dataset/stam_classified.json'
count = 0
index_name='qas'

print("Creation of final file...")
f = open(output_file,"w+")

print("Loading words dictionary...")
word_vector = get_word_dictionary(word_vector_path, vector_dim)

print("Start classification...")
with open(datset_file, 'r') as ds:
    for line in ds:
        json_obj = json.loads(line)
        
        if 'index' in json_obj.keys():
            qa_header = "{\"index\":{\"_index\":\"%s\",\"_id\":\"%d\"}}\n" % (index_name, count)
            f.write(qa_header)
            count = count + 1
            
        if 'question' in json_obj.keys():
            question = json_obj['question']
            answer = json_obj['answer']
            stemmedQuestion = json_obj['stemmedQuestion']
            label = cls.predict(quest2vect(word_vector, question, vector_dim)[None])[0]
            qa_body = "{\"question\":\"%s\",\"answer\":\"%s\", \"stemmedQuestion\": \"%s\", \"label\":\"%s\"}\n" % (question, answer, stemmedQuestion, label) 
            f.write(qa_body)
f.close()
