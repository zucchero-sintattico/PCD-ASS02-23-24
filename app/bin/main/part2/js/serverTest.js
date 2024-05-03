//creete a server for testing with a semplo page wher is write the word "test" ten times

const express = require('express');
const app = express();
const http = require('http').Server(app);
const io = require('socket.io')(http);





app.get('/', (req, res) => {
    let word = req.query.word;
    let numberOfWords = req.query.numberOfWords;
    
    //response must be a string with the word repeated numberOfWords times
    let response = "";
    for (let i = 0; i < numberOfWords; i++) {
        response += word + " ";
        response += `<a href="http://localhost:4000/?word=${word}&numberOfWords=${Number(numberOfWords) + i }">add ${i}</a> `;
    }
    
    res.send(response);

});





http.listen(4000, () => {
    console.log('API server is running on port http://localhost:4000');
    
});