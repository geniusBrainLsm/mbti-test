import { questions } from './data.js'
//{}안에 data.js 에서 가지고 올 데이터 이름 작성

const progressValueEl = document.querySelector('.progress .value')
const numberEl = document.querySelector('.number')
const questionEl = document.querySelector('.question')
const choice1El = document.querySelector('.choice1')
const choice2El = document.querySelector('.choice2')

//const 변수 재할당 x

let currentNumber = 0
let mbti = ''

function renderQuestion() {
    const question = questions[currentNumber]

    numberEl.innerHTML = question.number
    questionEl.innerHTML = question.question
    choice1El.innerHTML = question.choices[0].text
    choice2El.innerHTML = question.choices[1].text
    progressValueEl.style.width= (currentNumber + 1 ) * 10 + '%'
}

function nextQuestion(choiceNumber) {
    if(currentNumber === questions.length - 1 ){
        showResultPage()
        return 
    }
    // 10번째 질문후 결과 페이지로 넘어감

    const question = questions[currentNumber]
    mbti = mbti + question.choices[choiceNumber].value
    // mbti='' => 값이 비워져 있는 단순 문자 데이터
    // mbti = '' + 'i' or 'e' 
    // mbti = 'infj''
    currentNumber=currentNumber+1
    renderQuestion()
}

function showResultPage() {
    location.href = '/results.html?mbti=' + mbti //쿼리스트링
}

choice1El.addEventListener('click',function() {
    nextQuestion(0)
})//매개변수 choiceNumber 0 전달
choice2El.addEventListener('click',function() {
    nextQuestion(1)
})

renderQuestion()