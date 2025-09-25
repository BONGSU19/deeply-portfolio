<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<hr>
<div class="main-div" style="height:1000px; display: flex;">

	<div class="admin-one">
		<ul>
			<li><a href="admin_list">사용자 조회</a></li>
			<li><a href="admin_artist">아티스트 정보 조회</a></li>
			<li><a href="/charge/userBal">사이트 관리</a></li>
			<li><a href="#">1대1문의 관리</a></li>
		</ul>
	</div>
	<div class="admin-two">
<div style="width:1000px; margin-left:100px;"><canvas id="userStatisticsChart"></canvas></div>
<div style="clear:both;"></div>
<script>
    // 데이터를 Spring에서 JSON 형식으로 받아옵니다.
    fetch('/user-statistics')
        .then(response => response.json())
        .then(data => {
            // 받은 데이터 전체 출력
            console.log('Fetched data:', data);  // 받은 데이터의 전체 구조를 출력

            // 데이터가 배열인지 객체인지 확인
            if (Array.isArray(data)) {
                // 최신 5일 데이터만 선택
                const latestData = data.slice(-5);

                // 날짜별 접속자 수 데이터를 처리
                const labels = latestData.map(item => item.record_date);  // 날짜
                const activeUsers = latestData.map(item => item.active_users);  // 접속자 수

                // 날짜와 접속자 수 데이터를 확인하기 위한 로그 출력
                console.log('Labels (날짜):', labels);
                console.log('Active Users (접속자 수):', activeUsers);

                // Chart.js를 사용해 그래프를 그립니다.
                const ctx = document.getElementById('userStatisticsChart').getContext('2d');
                new Chart(ctx, {
                    type: 'line',  // 라인 그래프
                    data: {
                        labels: labels,  // x축 (날짜)
                        datasets: [{
                            label: '접속자 수',
                            data: activeUsers,  // y축 (접속자 수)
                            borderColor: 'rgba(75, 192, 192, 1)',  // 선 색상
                            fill: false,  // 선 아래 색상 채우지 않기
                            tension: 0.1  // 선의 곡선 정도 (선택 사항)
                        }]
                    },
                    options: {
                        responsive: true,
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: '날짜'
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: '접속자 수'
                                },
                                beginAtZero: true,  // y축 시작값 0
                                max: 1000,  // y축 최대값 200으로 설정
                                ticks: {
                                    stepSize: 20  // y축 값 간격을 20으로 설정
                                }
                            }
                        }
                    }
                });
            } else {
                console.error("Data is not an array:", data);
            }
        })
        .catch(error => console.error('Error fetching data:', error));
</script>

	</div>

</div>