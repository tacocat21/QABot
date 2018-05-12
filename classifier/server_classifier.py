#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed May  9 18:25:01 2018

@author: riccardo
"""

from flask import Flask, jsonify, request
from sklearn.externals import joblib
from data_processing import get_word_dictionary, quest2vect, average_vector
from short_sentence_similarity import semantic_similarity, similarity #added by terra

app = Flask(__name__)

print('Loading Classifier')
cls = joblib.load('data/random_forest.pkl')

print('Loading Word2Vector model...')
vector_dim = 50
word_vector_path = "data/glove.6B.50d.txt"
word_vector = get_word_dictionary(word_vector_path, vector_dim)
    
@app.route('/label/<question>', methods=['GET'])
def label(question):
    vector = quest2vect(word_vector, question, vector_dim)
    label = cls.predict(vector[None])[0]
    return jsonify({'label': label})

@app.route('/vector/<question>', methods=['GET'])
def vector(question):
    vector = average_vector(word_vector, question, vector_dim)
    return jsonify({'vector': vector.tolist()})

#added by terra
@app.route('/semantic/', methods=['GET'])
def semantic():
    check = request.args.to_dict()
    score = semantic_similarity(check['query'].lower(), check['hit'].lower(), False)
    return jsonify({'score': score})

if __name__ == '__main__':
    app.run(debug=True, use_reloader=False)
