const express = require('express');
const app = express();
const http = require('http').Server(app);
const io = require('socket.io')(http);

const WordCounter = require('./WordCounter');

var wordCounter;


app.get('/socket.io/socket.io.js', (req, res) => {
    res.sendFile(__dirname + '/node_modules/socket.io/client-dist/socket.io.js');
  });


io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
      console.log('user disconnected');
    });
  });




app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.get('/count-words', (req, res) => {
    
    function logger(url, count, globalWordsCounter, justVisitedLength) {
        console.log(`The word "${req.query.word}" appears ${count} times in the page ${url}  \t  Total: ${globalWordsCounter}, Links checked: ${justVisitedLength}`);
        io.emit('countRealTime', { url, count, globalWordsCounter, justVisitedLength });
    }

    function runInTheEnd(countedWords) {
        io.emit('endProcess');
    }

    wordCounter = new WordCounter();



    wordCounter.startCounting(req.query.word, req.query.url, req.query.deep, runInTheEnd, logger);

    res.json({ message: 'Process started' });
    
});

app.get('/stop-process', (req, res) => {
    wordCounter.stopCounting();
    res.json({ message: 'Process stopped' });
});


app.get('/force-stop', (req, res) => {
    delete wordCounter;
    res.json({ message: 'Process stopped' });
});


  

http.listen(3000, () => {
    console.log('API server is running on port 3000');
    
});