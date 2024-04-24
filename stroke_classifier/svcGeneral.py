from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
import pandas as pd
import numpy as np
from statistics import mean 
from sklearn import model_selection, metrics, neighbors
from sklearn.model_selection import cross_val_score


#read and prepare training data
X = pd.read_csv("general_x.csv")
Y_df = pd.read_csv("Y.csv")
Y = Y_df["stroke"]
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.25, random_state=42)

#linear
svm_classifier = SVC(kernel='linear', probability = True)
svm_classifier.fit(X_train, Y_train)

"""Y_predict = svm_classifier.predict_proba(X_test)
Y_trueFalse =[]
for element in Y_predict:
	if np.any(element < 0.5):
		Y_trueFalse.append(0)
	else:
		Y_trueFalse.append(1)"""
scores = cross_val_score(svm_classifier, X, Y, cv=5)
print("Accuracy of linear svm", scores.mean())

#poly 3
svm_classifier = SVC(kernel='poly', probability = True)
svm_classifier.fit(X_train, Y_train)


scores = cross_val_score(svm_classifier, X, Y, cv=5)
print("Accuracy of poly 3 svm", scores.mean())

#poly 4
svm_classifier = SVC(kernel='poly', degree = 4, probability = True)
svm_classifier.fit(X_train, Y_train)


scores = cross_val_score(svm_classifier, X, Y, cv=5)
print("Accuracy of poly 4 svm", scores.mean())

#rbf
svm_classifier = SVC(kernel='rbf', probability = True)
svm_classifier.fit(X_train, Y_train)


scores = cross_val_score(svm_classifier, X, Y, cv=5)
print("Accuracy of rbf svm", scores.mean())


#https://www.kaggle.com/datasets/prosperchuks/health-dataset