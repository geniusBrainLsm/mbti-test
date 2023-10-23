function enterkey() {
    if (window.event.keyCode == 13) {

        // 엔터키가 눌렸을 때 실행하는 반응
        $("#form").submit();
    }
}

const userInput = document.getElementById("userInput");

userInput.addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
        // 엔터 키가 눌렸을 때 실행할 동작을 여기에 추가
        sendMessage(); // 또는 원하는 다른 동작을 호출
    }
});

// 현재 페이지의 MBTI 값을 추출
const memberMbti = window.location.pathname.split('/').pop();
const csrfToken = document.getElementById("csrfToken").value;

// DOM 요소 캐싱
const commentsContainer = document.getElementById("comments");
const commentInput = document.getElementById("comment-input");
const commentCount = document.getElementById("count");
const submitButton = document.getElementById("submit");
const likedComments = new Set();

// 댓글 목록을 받아오는 함수
const getComments = () => {
    fetch(`/api/replies/${memberMbti}`)
        .then(response => response.json())
        .then(displayComments)
        .catch(error => console.error('Error fetching comments:', error));
}


// "추천" 버튼 클릭 시 호출되는 함수
const likeComment = commentId => {
    // 이미 추천한 댓글인지 확인
    if (likedComments.has(commentId)) {
        alert('이미 추천한 댓글입니다.');
        return;
    }

    // 서버로 추천 요청 전송
    fetch(`/api/replies/${commentId}/like`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
    })
        .then(response => response.json())
        .then(updatedComment => {
            // 서버로부터 업데이트된 댓글 정보를 받아와서 해당 댓글의 추천 수 업데이트
            const commentElement = document.querySelector(`.comment[data-comment-id="${commentId}"]`);
            if (commentElement) {
                const likesElement = commentElement.querySelector(".likes");
                if (likesElement) {
                    likesElement.textContent = `Likes: ${updatedComment.likes}`;
                }
            }

            // 추천한 댓글 기록
            likedComments.add(commentId);

            // 추천 버튼 비활성화
            const likeButton = commentElement.querySelector(".like-button");
            if (likeButton) {
                likeButton.disabled = true;
            }

        })
        .catch(error => console.error('Error liking comment:', error));
    // 화면 새로고침
    location.reload();
}
const deleteComment = commentId => {
    // 확인 대화 상자 표시
    const shouldDelete = confirm("정말로 이 댓글을 삭제하시겠습니까?");

    // 사용자가 "확인"을 누른 경우에만 삭제 진행
    if (shouldDelete) {
        // 서버로 삭제 요청 보내기
        fetch(`/api/replies/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(`Failed to delete comment: ${response.status}`);
                }
            })
            .then(data => {
                if (data.message === '댓글이 삭제되었습니다.') {
                    // 댓글이 성공적으로 삭제되었을 경우, 클라이언트에서도 삭제
                    const commentElement = document.querySelector(`.comment[data-comment-id="${commentId}"]`);
                    if (commentElement) {
                        commentElement.remove();
                    }
                } else {
                    console.error('서버에서 삭제 오류:', data.message);
                }
            })
            .catch(error => console.error('Error deleting comment:', error))
            .finally(() => {
                location.reload(); // 작업이 완료되면 화면 새로고침
            });
    }
}

// 댓글을 화면에 추가하는 함수
const addCommentToUI = comment => {
    const commentElement = document.createElement("div");
    commentElement.className = "comment";

    // 댓글 작성 시간 추가
    const timestampElement = document.createElement("div");
    timestampElement.className = "timestamp";
    timestampElement.textContent = formatTimestamp(comment.timestamp);
    commentElement.appendChild(timestampElement);

    // 닉네임 추가
    const nicknameElement = document.createElement("div");
    nicknameElement.className = "nickname";
    nicknameElement.textContent = comment.memberNickname; // 닉네임 표시
    commentElement.appendChild(nicknameElement);

    // 댓글 내용 추가
    const textElement = document.createElement("div");
    textElement.className = "text";
    textElement.textContent = comment.text;
    commentElement.appendChild(textElement);

    // 추천 수 추가
    const likesElement = document.createElement("div");
    likesElement.className = "likes";
    likesElement.textContent = `Likes: ${comment.likes}`;
    commentElement.appendChild(likesElement);

    // 추천 버튼 추가
    const likeButton = document.createElement("button");
    likeButton.className = "like-button";
    likeButton.textContent = "추천";
    likeButton.addEventListener("click", () => likeComment(comment.id)); // 추천 버튼 클릭 시 추천 함수 호출
    commentElement.appendChild(likeButton);

    // 삭제 버튼 추가
    const deleteButton = document.createElement("button");
    deleteButton.className = "delete-button";
    deleteButton.textContent = "삭제";
    deleteButton.addEventListener("click", () => deleteComment(comment.id)); // 삭제 버튼 클릭 시 삭제 함수 호출
    commentElement.appendChild(deleteButton);
    commentsContainer.appendChild(commentElement);


}

// "Submit" 버튼 클릭 시 호출되는 함수
const submitComment = () => {
    const commentText = commentInput.value.trim();
    if (commentText !== "") {
        const requestBody = {
            text: commentText,
            memberMbti: memberMbti
        };

        fetch('/api/replies', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken  // 추가된 부분
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('댓글 작성 실패');
                }
            })
            .then(newComment => {
                addCommentToUI(newComment);
                commentInput.value = "";

                // 댓글을 추가한 후에 댓글 목록을 다시 불러오도록 수정
                getComments();
            })
            .catch(error => {
                console.error('Error submitting comment:', error);
                alert('댓글 작성에 실패했습니다. 로그인 후 다시 시도하세요.'); // 알림창 표시
            });    }
}

// 댓글을 화면에 표시하는 함수
const displayComments = comments => {
    commentsContainer.innerHTML = "";
    commentCount.textContent = comments.length;

    // 역순으로 댓글을 추가
    comments.reverse().forEach(comment => {
        addCommentToUI(comment);
    });
}

// 페이지 로드 시 MBTI 값을 가져오고 댓글 목록을 불러오도록 수정
window.addEventListener("load", () => {
        getComments();
        let specialHTML = "";
        // memberMbti가 'isfp'인 경우 환영 메시지 추가


        if (memberMbti === 'infp') {
            specialHTML = `
            <div id="special-section">
                <h1>"열정적인 중재자"</h1>
                <p>이곳은 'infp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'enfp') {
            specialHTML = `
            <div id="special-section">
                <h1>"재기발랄한 활동가"</h1>
                <p>이곳은 'enfp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'intp') {
            specialHTML = `
            <div id="special-section">
                <h1>"논리적인 사색가"</h1>
                <p>이곳은 'intp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'entp') {
            specialHTML = `
            <div id="special-section">
                <h1>"뜨거운 논쟁을 즐기는 변론가"</h1>
                <p>이곳은 'entp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'infj') {
            specialHTML = `
            <div id="special-section">
                <h1>"선의의 옹호자"</h1>
                <p>이곳은 'infj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'enfj') {
            specialHTML = `
            <div id="special-section">
                <h1>"정의로운 사회운동가"</h1>
                <p>이곳은 'enfj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'intj') {
            specialHTML = `
            <div id="special-section">
                <h1>"용의주도한 전략가"</h1>
                <p>이곳은 'intj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'isfp') {
            specialHTML = `
            <div id="special-section">
                <h1>"호기심 많은 예술가"</h1>
                <p>이곳은 'isfp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'esfp') {
            specialHTML = `
            <div id="special-section">
                <h1>"자유로운 영혼의 연예인"</h1>
                <p>이곳은 'esfp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'istp') {
            specialHTML = `
            <div id="special-section">
                <h1>"만능 재주꾼"</h1>
                <p>이곳은 'istp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'estp') {
            specialHTML = `
            <div id="special-section">
                <h1>"모험을 즐기는 사업가"</h1>
                <p>이곳은 'entp' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'isfj') {
            specialHTML = `
            <div id="special-section">
                <h1>"용감한 수호자"</h1>
                <p>이곳은 'isfj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'esfj') {
            specialHTML = `
            <div id="special-section">
                <h1>"사교적인 외교관"</h1>
                <p>이곳은 'esfj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'istj') {
            specialHTML = `
            <div id="special-section">
                <h1>"청렴결백한 논리주의자"</h1>
                <p>이곳은 'istj' 게시판 입니다.</p>
            </div>
        `;
        }
        if (memberMbti === 'estj') {
            specialHTML = `
            <div id="special-section">
                <h1>"엄격한 관리자"</h1>
                <p>이곳은 'estj' 게시판 입니다.</p>
            </div>
        `;
        }

        // 특정 <h2> 태그 아래에 메시지 추가
        const chatbox = document.getElementById("resultmbti");
        chatbox.insertAdjacentHTML('beforeend', specialHTML);
    }
);


// "Submit" 버튼 클릭 시 댓글 작성 함수 호출
submitButton.addEventListener("click", () => submitComment());

// Enter 키 눌렀을 때도 댓글 작성 함수 호출
commentInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        submitComment();
    }
});

// 타임스탬프 형식을 변환하는 함수
const formatTimestamp = timestamp => {
    if (timestamp) {
        const date = new Date(timestamp);
        const options = {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            second: 'numeric'
        };
        return date.toLocaleDateString(undefined, options);
    } else {
        return "시간 정보 없음"; // 또는 원하는 다른 메시지
    }
}