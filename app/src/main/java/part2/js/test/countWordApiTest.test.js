const assert = require('assert');
const http = require('http');



function getWordCount(word, numberOfWords, deep, numberOfLinks , path = 0) {
  let testServerUrl = `http://localhost:4000/?word=${word}&numberOfWords=${numberOfWords}&numberOfLinks=${numberOfLinks}&path=${path}`;
  let url = `http://localhost:3000/base-count-words?word=${word}&url=${encodeURIComponent(testServerUrl)}&deep=${deep}`;
  return url;
}

function getTeoricalNumber(numberOfWords, deep, numberOfLinks) {
  sum = 0;
  for (let i = 0; i <= deep; i++) {
    sum = sum + numberOfWords *  Math.pow(numberOfLinks, i);
  }
  return sum ;
  
}




describe('countWordApiTest', () => {
  it('should return the correct count when all parameters are provided', (done) => {

    word = 'test';
    numberOfWords = 1;
    numberOfLinks = 3;
    deep = 3;

    let url = getWordCount(word, numberOfWords, deep, numberOfLinks);
    let numWordResult = getTeoricalNumber(numberOfWords, deep, numberOfLinks);

    console.log('url', url);
  


    http.get(url, (res) => {
      let data = '';
      
      res.on('data', (chunk) => {
        data += chunk;
      });
      
      res.on('end', () => {
        const result = JSON.parse(data);
        assert.deepStrictEqual(result, { n: numWordResult });
        done();
      });
    }).on('error', (err) => {
      done(err);
    });
  });
});
