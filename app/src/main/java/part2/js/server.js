const { createServer } = require('node:http');
const { countWords } = require('./all_in_one.js');

//import all_in_one.js

const hostname = '127.0.0.1';
const port = 3000;
const server = createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/html');
    res.write(`
        <html>
        <head>
            <title>Word Counter</title>
        </head>
        <body>
            <h1>Word Counter</h1>
            <form action="/count" method="POST">
                <label for="url">URL:</label>
                <input type="text" id="url" name="url"><br><br>
                <label for="word">Word to Search:</label>
                <input type="text" id="word" name="word"><br><br>
                <input type="submit" value="Count Words">
                <input type="submit" value="Count Words" onclick="countWords()">
                <input type="submit" value="Stop">
            </form>

            <h2>Results</h2>
            <p><span id="word">
            
            </span></p>
        </body>
        </html>
    `);
    res.end();
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});