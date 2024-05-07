const assert = require("assert");
const http = require("http");

function getWordCount(word, numberOfWords, depth, numberOfLinks, path = 0) {
  let testServerUrl = `http://localhost:4000/?word=${word}&numberOfWords=${numberOfWords}&numberOfLinks=${numberOfLinks}&path=${path}`;
  let url = `http://localhost:3000/base-count-words?word=${word}&url=${encodeURIComponent(
    testServerUrl
  )}&depth=${depth}`;
  return url;
}

function getTeoricalNumber(numberOfWords, depth, numberOfLinks) {
  sum = 0;
  for (let i = 0; i <= depth; i++) {
    sum = sum + numberOfWords * Math.pow(numberOfLinks, i);
  }
  return sum;
}

describe("countWordApiTest", () => {
  it("should return the correct count when all parameters are provided", (done) => {
    word = "test";
    numberOfWords = 2;
    numberOfLinks = 3;
    depth = 5;

    let url = getWordCount(word, numberOfWords, depth, numberOfLinks);
    let numWordResult = getTeoricalNumber(numberOfWords, depth, numberOfLinks);

    console.log("url", url);

    http
      .get(url, (res) => {
        let data = "";

        res.on("data", (chunk) => {
          data += chunk;
        });

        res.on("end", () => {
          const result = JSON.parse(data);
          assert.deepStrictEqual(result, { n: numWordResult });
          done();
        });
      })
      .on("error", (err) => {
        done(err);
      });
  });
});
