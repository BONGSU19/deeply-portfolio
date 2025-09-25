// formValidation.js

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('video-upload-form');

    form.addEventListener('submit', function(event) {
        // CKEditor의 데이터를 textarea에 저장
        if (editorInstance) {
            const descriptionData = editorInstance.getData();
            document.getElementById('description').value = descriptionData;
        }

        // 필수 필드 검증
        const requiredFields = form.querySelectorAll('[data-required="true"]');
        const missingFields = [];

        requiredFields.forEach(field => {
            if (field.type === 'checkbox' || field.type === 'radio') {
                if (!field.checked) {
                    missingFields.push(field);
                }
            } else if (field.tagName.toLowerCase() === 'div' && field.id === 'description-editor') {
                // CKEditor의 경우, 에디터 내용이 비어있는지 확인
                if (editorInstance && editorInstance.getData().trim() === '') {
                    missingFields.push(field);
                }
            } else {
                if (!field.value || field.value.trim() === '') {
                    missingFields.push(field);
                }
            }
        });

        if (missingFields.length > 0) {
            event.preventDefault(); // 폼 제출 차단
            alert('모든 필수 필드를 채워주세요.');
            
            // 첫 번째 누락된 필드로 포커스 이동
            const firstMissing = missingFields[0];
            if (firstMissing.tagName.toLowerCase() === 'div' && firstMissing.id === 'description-editor') {
                if (editorInstance) {
                    editorInstance.editing.view.focus();
                }
            } else {
                firstMissing.focus();
            }
        }
    });
});
