from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/lenguajes', methods=['GET'])
def get_favorite_languages():
    lenguajes = ['Python', 'JavaScript', 'Java', 'C#', 'C++']
    return jsonify({'lenguajes': lenguajes})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=4000)