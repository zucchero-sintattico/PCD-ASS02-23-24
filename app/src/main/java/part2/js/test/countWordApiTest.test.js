
const chaiHttp = require('chai-http');
const app = require('../app'); // Replace '../app' with the path to your server file

chai.use(chaiHttp);
const expect = chai.expect;

describe('API Tests', () => {
  it('should return the correct count of words', (done) => {
    const word = 'test';
    const url = 'http://localhost:4000/?word=test&numberOfWords=10';
    const deep = 1;

    chai.request(app)
      .get(`/base-count-words?word=${word}&url=${encodeURIComponent(url)}&deep=${deep}`)
      .end((err, res) => {
        expect(res).to.have.status(200);
        expect(res.body).to.deep.equal({ n: 155 });
        done();
      });
  });
});