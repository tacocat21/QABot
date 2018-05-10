"""
Created on Mon May  7 17:12:57 2018

@author: riccardo
"""
import numpy as np

def load_data(filename):
    questions = []
    labels_fine = []
    labels_coarse = []
    with open(filename, 'r', encoding="ISO-8859-1") as f:
        for line in f:
            label, question = line.split(" ", 1)
            questions.append(question[0:-3])
            labels_coarse.append(label.split(":")[0])
            labels_fine.append(label)
    return questions, labels_coarse, labels_fine

"""
Compute the avarage vector of all the words inside a
question.
"""
def average_vector(dictionary, question, vector_dim):
    result = np.zeros(vector_dim)
    count = 0
    for w in question.split(" "):
        w = w.lower()
        if w in dictionary:
            vector = dictionary[w]
            result = result + vector
            count += 1
    if count > 0:
        result = result / count
    return result

"""
Compute the avarage vector of the first 2 words.
"""
def quest2vect(dictionary, question, vector_dim):
    splitted = question.lower().split(" ")
    if len(splitted) >= 2 and splitted[0] == 'what' and splitted[1] == 'is':
        return average_vector(dictionary, question, vector_dim)
    elif len(splitted) >= 2:
        return average_vector(dictionary, splitted[0] + " " + splitted[1], vector_dim)
    else:
        return average_vector(dictionary, question, vector_dim)

"""
Return the dictionary word->vector
"""
def get_word_dictionary(word_vector_path, vector_dim):
    word_vector = np.loadtxt(word_vector_path, dtype='str', comments=None)
    keys = word_vector[:,0]
    values = word_vector[:, 1:].astype('float')
    word_vector = {}
    for i in range(len(keys)):
        word_vector[keys[i]] = values[i]
    return word_vector
    

if __name__ == "__main__":
    word_vector_path = "data/glove.6B.50d.txt"
    training_data_path = "data/train_5500.label"
    testing_data_path = "data/TREC_10.label"
    
    print("Loading Word2vec...")
    
    vector_dim = 50
    word_vector = get_word_dictionary(word_vector_path, vector_dim)
    
    print("Start computing the vector...")
    
    questions_train, labels_coarse_train, labels_fine_train = load_data(training_data_path)
    questions_test, labels_coarse_test, labels_fine_test = load_data(testing_data_path)
    
    vectors_train = [quest2vect(word_vector, q, vector_dim) for q in questions_train]
    vectors_test = [quest2vect(word_vector, q, vector_dim) for q in questions_test]
    
    
    np.savetxt('data/X_train', np.vstack(vectors_train))
    np.savetxt('data/Y_coarse_train', np.array(labels_coarse_train), fmt="%s")
    np.savetxt('data/Y_fine_train', np.array(labels_fine_train), fmt="%s")
    
    np.savetxt('data/X_test', np.vstack(vectors_test))
    np.savetxt('data/Y_coarse_test', np.array(labels_coarse_test), fmt="%s")
    np.savetxt('data/Y_fine_test', np.array(labels_fine_test), fmt="%s")


