//1.입력받으면 입력값 초기화
//2. 입력값 댓글로들어가기
//3. 댓글 삭제, 수정기능
//4. 좋아요 투표기능
//5. 타임스템프기능
//6. 무작위 아이디  
//7. 댓글 삭제기능
//8. 댓글 수정기능

const inputBar = document.querySelector("#comment-input");
const rootDiv = document.querySelector("#comments");
const btn = document.querySelector("#submit");
const mainCommentCount = document.querySelector('#count'); //맨위 댓글 숫자 세는거.

//글로벌로 뺏음. 값을 저장하기 위해서.
let idOrVoteCountList=[];


//타임스템프 만들기
function generateTime(){
    const date = new Date();
    const year = date.getFullYear();
    const month = date.getMonth();
    const wDate = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();

    const time = year+'-'+month+'-'+wDate+' '+hour+':'+min+':'+sec;
    return time;

}

//유저이름 발생기
//유저이름은 8글자로 제한.
function generateUserName(){
    let alphabet = 'abcdefghijklmnopqrstuvwxyz';
    var makeUsername = '';
    for(let i=0; i<4;i++){
        let index = Math.floor(Math.random(10) * alphabet.length);
        makeUsername += alphabet[index];        
    }
    for(let j=0;j<4;j++){
        makeUsername += "*";
    }
    return makeUsername;    
}

function numberCount(event){      
    event.preventDefault(); 
    console.log(event.target.parentNode.id);
    for(let i=0; i<idOrVoteCountList.length; i++){  
        if(event.target.className === "voteUp"){                       
            
           //event.target.parentNode.id 값이 string이기 때문에 이 값을 Number처리하던지, idOrVoteCountList[i]["commentId"]이 값을 string처리해야 넘어감.
           if(idOrVoteCountList[i]["commentId"].toString() === event.target.parentNode.id){                
               idOrVoteCountList[i]["voteUpCount"]++;               
               event.target.innerHTML = "👍"+idOrVoteCountList[i]["voteUpCount"]+" Likes";
           }       
         
         }else if(event.target.className === "voteDown"){
           if(idOrVoteCountList[i]["commentId"].toString() === event.target.parentNode.id){
               idOrVoteCountList[i]["voteDownCount"]++;
               event.target.innerHTML = "👎"+idOrVoteCountList[i]["voteDownCount"]+" Dislikes";              
         } 
       }

   } 
}

//기존에 남아있던 id초기화 및 새로추가된부분만 코멘트값 이어서 들어옴.
function initIdCount(){
    for(let i=0; i<idOrVoteCountList.length; i++){
      if(idOrVoteCountList[i]["commentId"] - i > 1){    
        idOrVoteCountList[i]["commentId"] =  idOrVoteCountList[i]["commentId"] - (idOrVoteCountList.length-(i+1));        
      }
    }
}


function deleteComments(){ 
    const btn = event.target;
    const list = btn.parentNode.parentNode;//commentList
    //삭제버튼도 마찬가지임. 여러개니깐 인식을 못함. 상위노드에 id 부여함.    
    //삭제버튼 클릭한 배열의 인덱스를 날리면 됨. 뭐 여기까지 해도 상관없는데...
    for(let i=0; i<idOrVoteCountList.length; i++){
        if(idOrVoteCountList[i]["commentId"].toString() === btn.parentNode.id){
            idOrVoteCountList.splice(btn.parentNode.id-1,1);   
        }

    }
    //그다음에 전체 지우기.
    rootDiv.removeChild(list);   

    //메인댓글 카운트 줄이기. 
    if(mainCommentCount.innerHTML <='0'){
        mainCommentCount.innerHTML = 0;        
    }else{
        mainCommentCount.innerHTML--; 
    }
}

//수정창 모달로 만들기
function modifyComments(event){
    const mBtn = event.target;
    const modal = document.createElement('div');
}


//댓글보여주기
function showComment(comment){
    const userName = document.createElement('div');
    const inputValue = document.createElement('span');
    const showTime = document.createElement('div'); // 타임스탬프 위치를 아래에서 위로 변경
    const voteDiv = document.createElement('div');
    const countSpan = document.createElement('span')
    const voteUp = document.createElement('button');
    const voteDown = document.createElement('button');  
    const commentList = document.createElement('div');  
    const modifyBtn = document.createElement('button');
    const spacer = document.createElement('div');

    const newId = idOrVoteCountList.length+1; //댓글하나에 달린 ID
    
    //스페이서만들기
    spacer.className = "spacer";
    //삭제버튼 만들기
    const delBtn = document.createElement('button');
    delBtn.className ="deleteComment";
    delBtn.innerHTML="Delete";    
    commentList.className = "eachComment";
    userName.className="name";
    userName.id = newId; //상위노드(삭제) 
    inputValue.className="inputValue";
    showTime.className="time";
    voteDiv.className="voteDiv";
    voteDiv.id = newId;
    //수정버튼 만들기
    //modifyBtn.className = 'modifyBtn';
    //modifyBtn.innerHTML = "수정";
    //유저네임가져오기 
    userName.innerHTML = generateUserName();  
    userName.appendChild(spacer);
    userName.appendChild(modifyBtn);
    userName.appendChild(delBtn);  
    //입력값 넘기기
    inputValue.innerText = comment;
    //투표창 만들기, css먼저 입혀야함. 
    voteUp.className ="voteUp";
    voteDown.className ="voteDown";     
    voteUp.innerHTML = "👍" + 0 + " Likes";         
    voteDown.innerHTML = "👎" + 0 + " Dislikes";       
    voteDiv.appendChild(voteUp);
    voteDiv.appendChild(voteDown);

    //타임스템프찍기 (댓글 내용 위로 이동)
    showTime.innerHTML = generateTime();

    //댓글뿌려주기       
    commentList.appendChild(userName);
    commentList.appendChild(showTime); // 타임스탬프를 먼저 추가하도록 변경
    commentList.appendChild(inputValue);
    commentList.appendChild(voteDiv);
    rootDiv.prepend(commentList);
   
    //아이디에 따른 투표수카운트. 배열에 접근해서 수정하는 방식으로 해야함.
    let IdAccordingToVoteCount ={
        "commentId" : newId,
        "voteUpCount" : 0,
        "voteDownCount" : 0
    }
    
    idOrVoteCountList.push(IdAccordingToVoteCount);
    console.log(idOrVoteCountList);
    
    initIdCount();
    
    voteUp.addEventListener("click",numberCount);
    voteDown.addEventListener("click",numberCount);
    delBtn.addEventListener("click",deleteComments);
    modifyBtn.addEventListener("click",modifyComments);
}




//버튼만들기+입력값 전달
function pressBtn(){ 
    event.preventDefault();

   const currentVal = inputBar.value;
   
   if(!currentVal.length){
      alert("내용을 입력해주세요.");
   }else{
      showComment(currentVal);  
      mainCommentCount.innerHTML++;
      inputBar.value ='';
   }
}

btn.onclick = pressBtn;