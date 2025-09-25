document.querySelectorAll('.tab-button').forEach(button => {
       button.addEventListener('click', () => {
           const tab = button.getAttribute('data-tab');

           // 탭 버튼 활성화 상태 변경
           document.querySelectorAll('.tab-button').forEach(btn => {
               btn.classList.remove('active');
           });
           button.classList.add('active');

           // 탭 콘텐츠 활성화 상태 변경
           document.querySelectorAll('.tab-panel').forEach(panel => {
               if (panel.getAttribute('data-tab') === tab) {
                   panel.classList.add('active');
               } else {
                   panel.classList.remove('active');
               }
           });
       });
   });
   
