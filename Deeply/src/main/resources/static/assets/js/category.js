// 드롭다운 열고 닫기
function toggleDropdown() {
    const dropdownMenu = document.getElementById('dropdown-menu');
    dropdownMenu.classList.toggle('hidden'); // 열고 닫기 토글
}

// 카테고리 선택
function selectCategory(categoryId, categoryName) {
    document.getElementById('selected-category').innerText = categoryName;
    document.getElementById('selected-category-input').value = categoryId; // 선택된 ID를 hidden input에 저장
    toggleDropdown(); // 드롭다운 닫기
}

// 새로운 카테고리 추가
function addCategory() {
    const newCategoryName = $('#new-category-name').val().trim();

    if (newCategoryName) {
        // CSRF 토큰 읽기
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        // Ajax 요청으로 서버에 새 카테고리 추가 요청
        $.ajax({
            url: '/categories/add', // VideoCategoryController의 "/categories/add" 매핑
            type: 'POST',
            data: { categoryName: newCategoryName },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 토큰 추가
            },
            success: function (data) {
                // 추가 성공 시 새로운 카테고리 드롭다운에 추가
                const dropdownContainer = $('.dropdown-item-container');
                const newItem = `
                    <div class="dropdown-item" onclick="selectCategory(${data.categoryId}, '${data.categoryName}')">
                        ${data.categoryName}
                    </div>
                `;
                dropdownContainer.append(newItem);

                // 입력 필드 초기화
                $('#new-category-name').val('');
                alert('새 카테고리가 추가되었습니다.');
				loadCategories();
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                alert('카테고리 추가에 실패했습니다.');
            }
        });
    } else {
        alert('카테고리 이름을 입력하세요.');
    }
}

// 페이지 로드 시 카테고리 목록 로드
function loadCategories() {
    // CSRF 토큰 읽기
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    $.ajax({
        url: '/categories/list', // VideoCategoryController의 "/categories/list" 매핑
        type: 'GET',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 토큰 추가
        },
		success: function (data) {
		    console.log("Received data:", data); // 응답 데이터를 콘솔에 출력
		    const dropdown_container = $('.dropdown-item-container');
		    dropdown_container.empty(); // 기존 카테고리 제거

		    if (data && Array.isArray(data)) {
		        data.forEach(function (category) {
		            if (category && category.category_id && category.category_name) {
		                const category_item = `
		                    <div class="dropdown-item" onclick="selectCategory(${category.category_id}, '${category.category_name}')">
		                        ${category.category_name}
		                    </div>
		                `;
		                dropdown_container.append(category_item);
		            }
		        });
		    } else {
		        console.warn("No categories found or invalid response.");
		    }
		},
        error: function (xhr, status, error) {
            console.error('Error:', error);
            alert('카테고리 목록을 불러오는 데 실패했습니다.');
        }
    });
}

// 페이지 로드 시 실행
$(document).ready(function () {
    loadCategories();
});
