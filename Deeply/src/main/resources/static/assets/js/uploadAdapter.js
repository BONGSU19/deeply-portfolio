class UploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then( file => new Promise(((resolve, reject) => {
            this._initRequest();
            this._initListeners( resolve, reject, file );
            this._sendRequest( file );
        })))
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        //컨텍스 경로가 /springPage 형식일 때
        //xhr.open('POST', location.protocol + '//' + location.host + location.href.substring(location.href.indexOf(location.host)+location.host.length,location.href.indexOf('/',location.href.indexOf(location.host)+location.host.length+1)) + '/common/imageUploader', true);
        //컨텍스 경로가 / 형식일 때
        xhr.open('POST', location.protocol + '//' + location.host + '/common/imageUploader', true);
		xhr.setRequestHeader('X-CSRF-TOKEN',document.getElementsByName('_csrf')[0].value);
        xhr.responseType = 'json';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '파일을 업로드 할 수 없습니다.'

        xhr.addEventListener('error', () => {reject(genericErrorText)})
        xhr.addEventListener('abort', () => reject())
        xhr.addEventListener('load', () => {
            const response = xhr.response
			if(!response || response.error) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }
			//---------최대 업로드 사이즈 초과 및 메시지/로그아웃 메시지 처리---------//
            if(response && response.result){
				if(response.result == 'logout') return reject("로그아웃되어 이미지를 업로드할 수 없습니다.");
				return reject(response.result); //kr.spring.util의 FileUploadExceptionAdvice 참조
			}
            //---------최대 업로드 사이즈 초과 및 메시지/로그아웃 메시지 처리---------//
            
            resolve({
                default: response.url //업로드된 파일 주소
            })
        })
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append('upload',file)
        this.xhr.send(data)
    }
}