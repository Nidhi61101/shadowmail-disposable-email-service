from flask import Flask, request, jsonify
import joblib

# Load trained model
model = joblib.load("spam_model.joblib")

app = Flask(__name__)

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    text = data.get("text", "")
    prediction = model.predict([text])[0]
    return jsonify({"result": prediction})

if __name__ == "__main__":
    app.run(port=5000)
