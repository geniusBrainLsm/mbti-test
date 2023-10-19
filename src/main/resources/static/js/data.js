export const questions = [
  {
    number: '01',
    question: '곧 크리스마스! 학교에서 크리스마스 파티 스텝을 모집한다는데?',
    choices: [
      { text: '파티 스텝은 좀..ㅎ <br>(그래도 파티는 재밌겠다 헤헤)', value: 'i' },
      { text: '재밌겠다!<br>○○한테 같이 지원해보자고 해야지~', value: 'e' }
    ]
  },
  {
    number: '02',
    question: '여행 일정이 잡혔다!<br>전날 잠들기 전 나는?',
    choices: [
      { text: '짐은 다 챙겼고 빼놓은건 없겠지? 일찍 자야지~', value: 's' },
      { text: '다 챙겼겠지?<br>다른 것들 좀 더 챙길까..(뒤척뒤척)', value: 'n' }
    ]
  },
  {
    number: '03',
    question: '친한 친구가<br>한숨을 자꾸 쉰다',
    choices: [
      { text: '뭐가 많이 힘든가?<br>내가 좀 챙겨줘야겠다.', value: 'f' },
      { text: '왜그러지?<br>왜 자꾸 그러는지 물어봐야겠다.', value: 't' }
    ]
  },
  {
    number: '04',
    question: '중요한 발표를 맡게 되어 떨리는 마음에 처음 시작한 건?',
    choices: [
      { text: '중요한 발표인 만큼 계획이 중요해!<br>발표 자료 먼저 정리해봐야지!', value: 'j' },
      { text: '당장 미팅 잡고<br>팀원이랑 이야기하면 되겠지?', value: 'p' }
    ]
  },
  {
    number: '05',
    question: '첫 입사 날!<br>나의 첫 인사말은?',
    choices: [
      { text: '안녕하세요ㅎㅎ 잘 부탁드려요~<br>(부끄러워 >///<)', value: '' },
      { text: '안녕하세요! 저는 자취하고 있고요,<br>MBTI는 ~~ (빨리 친해져야지 >0<) ', value: '' }
    ]
  },
  {
    number: '06',
    question: '날씨가 좋아~<br>유난히 공부하기 싫은 날엔?',
    choices: [
      { text: '이런 날 공부라니.<br>그런데 어쩌겠어? 공부해야지!', value: '' },
      { text: '이런 날 공부만 할 순 없어!!<br>이따가 해야겠다ㅎㅎ!', value: '' }
    ]
  },
  {
    number: '07',
    question: '잘 모르는 사람이 뒤에서 내 욕을 했다고 들었다면?',
    choices: [
      { text: '왜? 뭐라고? 아 진짜?<br>(신경 쓰려하지 않지만 자꾸 기분이 상함.)', value: '' },
      { text: '나를? 왜? 잘 모르는 사람인데?<br>(별 생각 없음. 그냥 궁금해서 물어봄.)', value: '' }
    ]
  },
  {
    number: '08',
    question: '과제를 하는 데 너무 어려워ㅠㅠ<br>어떻게 해결하지?',
    choices: [
      { text: '시간을 더 끌 순 없지.<br>친구들한테 물어봐야겠다!', value: '' },
      { text: '어떡하지.. 오래 걸리더라도<br> 더 찾아봐야겠다. ', value: '' }
    ]
  },
  {
    number: '09',
    question: '시험이 다가오는데 공부가 손에 안잡힌다면, 이때 하는 생각은?',
    choices: [
      { text: '시험은 왜 있는거지?<br>왜 봐야하는거야', value: '' },
      { text: '저번 시험 문제 어떻게 나왔었더라?<br>어떡하면 더 잘 외워질까? ', value: '' }
    ]
  },
  {
    number: '10',
    question: '갑자기 약속이 취소된다면?',
    choices: [
      { text: '아 너무 아쉬운데..<br>다른 친구라도 부를까?', value: '' },
      { text: '갑자기 취소되어서 아쉽지만~ <br>집에 있어야겠다ㅎㅎ ', value: '' }
    ]
  },
]
export const results = [
  {
    title: '출근 시간은<br>내가 정해!',
    character: '../static/images/result1.png',
    results: [
      '혼자서 멘탈 케어가 가능해요!<br>소속이 없어도, 벌이가 불안정해도 저는 지금이 좋아요!',
      '일에 대한 욕심이 많아요.<br>행복한 야근이라고 할 수 있어요!',
      '자유로운 환경에서 더 잘해요!<br>하지만 누구보다 스스로 시간 관리가 철저해요:)',
      '리더십 있다는 소리를 자주 들어요.<br>추진력이 있는 편이죠~ (뿌듯)'
    ],
    jobs: ['프리랜서', '창업가'],
    lectureImg: '../static/images/result_lecture1.png',
    lectureUrl: 'https://bit.ly/3Wr0kt6'
  },
  {
    title: '프로직진러!<br>진행력 갑',
    character: '../static/images/result2.png',
    results: [
      '나는야 회사 사람들 멘탈 지킴이!<br>언제나 토닥토닥 으샤으샤 응원하며 다니는 파워 인싸!',
      '여러 사람과 협업이 필요한 직무에 찰떡!',
      '진행력 갑! 추진력 갑!<br>빠른 속도로 동시에 여러 업무를 해결할 수 있어요.',
      '회사 행사나 크리스마스 이벤트,<br>회사사람들 생일까지 우리가 다 챙길게요'
    ],
    jobs: ['마케터', '기획자'],
    lectureImg: '../static/images/result_lecture2.png',
    lectureUrl: 'https://bit.ly/3SZl1t9'
  },
  {
    title: '우리 무기는<br>상상력!',
    character: '../static/images/result3.png',
    results: [
      '이것저것 관심이 많은 편이에요.<br>모든 게 궁금해요!',
      '섬세함이 장점으로<br>업무를 센스 있게 처리한다는 말을 자주 들어요.',
      '가끔은 거절을 잘 못해서 일이 몰릴 때가 있지만..!<br>전 괜찮아요~ (프로긍정러)',
      '업무에 예민할 때도 있지만,<br>그래서 결과물은 완벽하죠!'
    ],
    jobs: ['디자이너', '예술가'],
    lectureImg: '../static/images/result_lecture3.png',
    lectureUrl: 'https://bit.ly/3DWlesG'
  },
  {
    title: '인생은<br>한방!',
    character: '../static/images/result4.png',
    results: [
      '전략적으로 계획을 세우는 걸 좋아해요!',
      '평소 경제와 사회 이슈에 관심이 매우 많아요.',
      '꿈이 커요!<br>크게 성공할 수 있는 한 방을 이뤄낼 거예요~',
      '일할 땐 일하고! 놀 땐 놀고!<br>뭐든 확실한 게 좋아요.'
    ],
    jobs: ['부동산<br>투자자', '주식<br>투자자'],
    lectureImg: '../static/images/result_lecture4.png',
    lectureUrl: 'https://bit.ly/3fvoTo5'
  },
  {
    title: '한 번 집중하면<br>멈출 수 없지!',
    character: '../static/images/result5.png',
    results: [
      '저는 위선과 가식이 없어요.<br>진실만 말하죠. (진지)',
      '혼자서 더 잘해요.<br>솔로 워커 최고!',
      '관심 분야가 생기면 끝도 없이 깊어져요.<br>다만 이외에 모든 것엔 관심이 없어요.',
      '(이상) <<<<<< (현실)<br>가끔 이성에게 상처를 줄 때가 있어요, 그게 아닌데...'
    ],
    jobs: ['개발자', '데이터분석가'],
    lectureImg: '../static/images/result_lecture5.png',
    lectureUrl: 'https://bit.ly/3U0O3Kj'
  },
  {
    title: '인생 욜로지~<br>일단 놀자 ><',
    character: '../static/images/result6.png',
    results: [
      '취미가 100개!<br>이것저것 다재다능해요~',
      '한가하냐고요?<br>원래 백수가 젤 바쁜 거에요!',
      '결혼사진에 친구들이 다 들어갈 수 있을까요?',
      '가끔이지만, 갓생살기 도전해요!'
    ],
    jobs: ['돈 많은 백수', '유튜버'],
    lectureImg: '../static/images/result_lecture6.png',
    lectureUrl: 'https://bit.ly/3fzdinR'
  }
]
export const mbtis = {
  entj: 0,
  entp: 0,
  estp: 0,
  esfp: 1,
  esfj: 1,
  enfj: 1,
  infp: 2,
  isfp: 2,
  isfj: 2,
  infj: 2,
  estj: 3,
  istp: 3,
  intj: 4,
  intp: 4,
  istj: 4,
  enfp: 5
}