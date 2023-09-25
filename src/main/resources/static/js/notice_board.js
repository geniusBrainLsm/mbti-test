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
        console.log('이미 추천한 댓글입니다.');
        return;
    }

    // 서버로 추천 요청 전송
    fetch(`/api/replies/${commentId}/like`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken  // 추가된 부분
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
    nicknameElement.textContent = comment.nickname; // 닉네임 표시
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
            .then(response => response.json())
            .then(newComment => {
                addCommentToUI(newComment);
                commentInput.value = "";

                // 댓글을 추가한 후에 댓글 목록을 다시 불러오도록 수정
                getComments();
            })
            .catch(error => console.error('Error submitting comment:', error));
    }
}

// 댓글을 화면에 표시하는 함수
const displayComments = comments => {
    commentsContainer.innerHTML = "";
    commentCount.textContent = comments.length;

    comments.forEach(comment => {
        addCommentToUI(comment);
    });
}

// 페이지 로드 시 MBTI 값을 가져오고 댓글 목록을 불러오도록 수정
window.addEventListener("load", () => getComments());

// "Submit" 버튼 클릭 시 댓글 작성 함수 호출
submitButton.addEventListener("click", () => submitComment());

// 타임스탬프 형식을 변환하는 함수
const formatTimestamp = timestamp => {
    const date = new Date(timestamp);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
}