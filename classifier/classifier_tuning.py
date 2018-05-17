#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed May  9 14:43:33 2018

@author: riccardo
"""
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn import linear_model
from scipy.stats import randint as sp_randint
from sklearn.model_selection import RandomizedSearchCV
from sklearn.externals import joblib

X_train, Y_train = np.loadtxt('classifier/data/X_train'), \
                   np.loadtxt('classifier/data/Y_coarse_train', dtype=str)
                   
X_test, Y_test = np.loadtxt('classifier/data/X_test'),\
                 np.loadtxt('classifier/data/Y_coarse_test', dtype=str)

# Utility function to report best scores
def report(results, n_top=3):
    for i in range(1, n_top + 1):
        candidates = np.flatnonzero(results['rank_test_score'] == i)
        for candidate in candidates:
            print("Model with rank: {0}".format(i))
            print("Mean validation score: {0:.3f} (std: {1:.3f})".format(
                  results['mean_test_score'][candidate],
                  results['std_test_score'][candidate]))
            print("Parameters: {0}".format(results['params'][candidate]))
            print("")

# Random search for a randon forest classifier
def search_parameter():
    clf = RandomForestClassifier()
    # specify parameters and distributions to sample from
    param_dist = {"max_depth": [3, None],
                  "max_features": sp_randint(1, 21),
                  "min_samples_split": sp_randint(2, 21),
                  "min_samples_leaf": sp_randint(1, 21),
                  "bootstrap": [True, False],
                  "criterion": ["gini", "entropy"]}
    
    # run randomized search
    n_iter_search = 20
    random_search = RandomizedSearchCV(clf, param_distributions=param_dist,
                                       n_iter=n_iter_search)
    
    random_search.fit(X_train, Y_train)
    report(random_search.cv_results_)

# Train and test best classifier
def best_classifier():
    rf = RandomForestClassifier(bootstrap=False, 
                                criterion='gini',
                                max_depth=None,
                                max_features=13,
                                min_samples_leaf=3,
                                min_samples_split=3)
    rf.fit(X_train, Y_train)
    joblib.dump(rf, 'classifier/data/random_forest.pkl') 
    print("Train accuracy %f" % rf.score(X_train, Y_train))
    print("Test accuracy %f"  % rf.score(X_test, Y_test))

if __name__ == '__main__':
    best_classifier()
