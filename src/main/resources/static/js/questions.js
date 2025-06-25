import { questions, mbtis } from './data.js'

const progressValueEl = document.querySelector('.progress .value')
const numberEl = document.querySelector('.number')
const questionEl = document.querySelector('.question')
const choice1El = document.querySelector('.choice1')
const choice2El = document.querySelector('.choice2')

let currentNumber = 0
let scores = {
    E: 0, I: 0,
    S: 0, N: 0,
    T: 0, F: 0,
    J: 0, P: 0
}

function renderQuestion() {
    const question = questions[currentNumber]

    numberEl.innerHTML = question.number
    questionEl.innerHTML = question.question
    choice1El.innerHTML = question.choices[0].text
    choice2El.innerHTML = question.choices[1].text
    progressValueEl.style.width = (currentNumber + 1) * 5 + '%'  // 20문항이므로 5%씩
}

function nextQuestion(choiceNumber) {
    if(currentNumber === questions.length - 1) {
        // 마지막 선택도 점수에 반영
        const question = questions[currentNumber]
        const selectedChoice = question.choices[choiceNumber].value
        if(selectedChoice) {
            scores[selectedChoice]++
        }

        showResultPage()
        return
    }

    const question = questions[currentNumber]
    const selectedChoice = question.choices[choiceNumber].value

    // 선택한 답변의 점수 증가
    if(selectedChoice) {
        scores[selectedChoice]++
    }

    currentNumber = currentNumber + 1
    renderQuestion()
}

function showResultPage() {
    // MBTI 결과 계산
    let mbtiResult = ''
    mbtiResult += scores.E > scores.I ? 'E' : 'I'
    mbtiResult += scores.S > scores.N ? 'S' : 'N'
    mbtiResult += scores.T > scores.F ? 'T' : 'F'
    mbtiResult += scores.J > scores.P ? 'J' : 'P'

    location.href = '/results?mbti=' + mbtiResult
}

choice1El.addEventListener('click', function() {
    nextQuestion(0)
})

choice2El.addEventListener('click', function() {
    nextQuestion(1)
})

renderQuestion()