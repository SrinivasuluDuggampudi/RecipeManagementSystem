import spacy
from spacy.matcher import PhraseMatcher
from flask import Flask, request, jsonify
import requests
import logging
from flask_cors import CORS

app = Flask(__name__)
CORS(app, origins=["http://localhost:3000"], resources={r"/process_input": {"origins": "*"}})

logging.basicConfig(level=logging.INFO)
app.logger.setLevel(logging.INFO)

# Load spaCy model
try:
    nlp = spacy.load("en_core_web_sm")
except Exception as e:
    app.logger.error(f"Error loading spaCy model: {e}")
    exit("Failed to load spaCy model.")

# Food categories
FOOD_CATEGORIES = [
    "italian", "mexican", "indian", "chinese", "american", "french", "thai",
    "japanese", "greek", "mediterranean", "vegan", "vegetarian", "breakfast",
    "dessert", "snack", "salad", "soup", "seafood", "chicken", "beef", "pasta",
    "rice", "bread", "smoothie", "barbecue"
]

matcher = PhraseMatcher(nlp.vocab)
patterns = [nlp.make_doc(food) for food in FOOD_CATEGORIES]
matcher.add("FoodCategories", None, *patterns)

JAVA_BACKEND_URL = "http://localhost:8080/api/search-by-keywords"
SPOONACULAR_API_KEY = "8b42bebf23284e36bc691f484c9b73c7"  # Replace with your Spoonacular API key

USER_NAME = "Sri"
SMALL_TALK_RESPONSES = {
    "hello": f"Hello, I'm {USER_NAME}! How can I assist you today?",
    "hi": f"Hi there, I'm {USER_NAME}! Looking for something delicious?",
    "hey": f"Hey, I'm {USER_NAME}! Want a recipe recommendation?",
    "goodbye": "Goodbye, happy cooking!",
    "suggest recipes": "Sure! Tell me what cuisine or ingredient you're interested in.",
    "recommend recipes": "Of course! What kind of dish are you in the mood for?",
    "show me some recipes": "Absolutely! What type of recipe would you like?",
    "suggest a recipe": "What cuisine or ingredient are you thinking about? I'll suggest something.",
    "yeah": f"Great! What type of dish or ingredient do you like?",
    "recommend good recipes": "I'd love to! Please tell me what kind of meal you're thinking about."
}

@app.route('/process_input', methods=['POST'])
def process_input():
    try:
        data = request.get_json()
        if not data or 'input' not in data:
            return jsonify({"error": "Invalid input data. Provide an 'input' field."}), 400

        user_input = data.get("input", "").strip().lower()
        user = data.get('user')
        if not user:
            return jsonify({"error": "User not authenticated."}), 401

        if not user_input:
            return jsonify({"error": "Empty input provided."}), 400

        # Check for small talk
        for phrase, response in SMALL_TALK_RESPONSES.items():
            if user_input == phrase:
                return jsonify({"message": response})

        # Detect categories using spaCy
        doc = nlp(user_input)
        detected_categories = {doc[start:end].text.lower() for match_id, start, end in matcher(doc)}
        if not detected_categories:
            return jsonify({"message": "I couldn't detect a cuisine or ingredient. Could you mention what you're craving?"})

        # Try Java backend
        response = send_to_java_backend(list(detected_categories), user)
        if response.status_code == 200:
            recipe_data = response.json()
            if recipe_data:
                return jsonify({
                    "message": "Here are some recipes from your database!",
                    "recipes": recipe_data
                })
            else:
                spoonacular_ideas = fetch_spoonacular_ideas(detected_categories)
                return jsonify({
                    "message": "I couldn't find any recipes in your database, but here are some from the web!",
                    "ideas": spoonacular_ideas
                })
        else:
            return jsonify({
                "error": f"Java backend error: {response.status_code}",
                "details": response.text
            }), 500

    except Exception as e:
        app.logger.error(f"Error processing input: {e}")
        return jsonify({"error": f"Error processing input: {e}"}), 500


def send_to_java_backend(categories, user):
    keywords = ",".join(categories)
    params = {"keywords": keywords, "user": user}
    headers = {"Content-Type": "application/json"}
    return requests.get(JAVA_BACKEND_URL, params=params, headers=headers)


def fetch_spoonacular_ideas(categories):
    ideas = []
    for category in categories:
        try:
            url = f"https://api.spoonacular.com/recipes/complexSearch"
            params = {
                "query": category,
                "number": 3,
                "apiKey": SPOONACULAR_API_KEY
            }
            response = requests.get(url, params=params)
            results = response.json().get("results", [])
            for item in results:
                ideas.append({
                    "name": item.get("title"),
                    "link": f"https://spoonacular.com/recipes/{item.get('title').replace(' ', '-')}-{item.get('id')}"
                })
        except Exception as e:
            app.logger.error(f"Spoonacular error for {category}: {e}")
    return ideas


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
