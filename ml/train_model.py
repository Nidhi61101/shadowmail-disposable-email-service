import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline
import joblib

# Load dataset
df = pd.read_csv("spam.csv", encoding="ISO-8859-1")[["v1", "v2"]]
df.columns = ["label", "text"]

# Drop missing rows
df = df.dropna(subset=["text"])

# Trim whitespace
df["text"] = df["text"].str.strip()

# Split features and labels
X = df["text"]
y = df["label"]

# Build pipeline
model = Pipeline([
    ("vectorizer", CountVectorizer()),
    ("classifier", MultinomialNB())
])

# Train the model
model.fit(X, y)

# Save model
joblib.dump(model, "spam_model.joblib")

print("✅ Model trained and saved as spam_model.joblib")