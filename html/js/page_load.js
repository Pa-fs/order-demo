
const pageLoadData = {
  slider: {},
  category: {
    css: ["../css/k-style.css", "../css/category.css", "../css/item.css"],
    js: ["../js/category.js"],
  },
};

const menuList = document.querySelector(".s-nav");

menuList.addEventListener("click", function (event) {
    // e.preventDefault();

      // 클릭한 요소가 .load_page 클래스를 가지고 있는지 확인
  if (event.target.classList.contains('load_page')) {
    event.preventDefault();
    const page = event.target.getAttribute('data-page');

    // 페이지 데이터 확인
    const pageData = pageLoadData[page];
    if (!pageData) {
      console.error(`No data found for page: ${page}`);
      return;
    }

    // HTML 페이지 로드
    fetch(`${page}.html`)
      .then((response) => response.text())
      .then((html) => {

        let html_dom = new DOMParser().parseFromString(html,'text/html');
        const dynamicContentLoad = document.getElementById('dynamic_content_load');
      if (!dynamicContentLoad) {
        console.error('dynamic_content_load element not found');
        return;
      }

      menuList.removeEventListener('click', this);

        // dynamicContentLoad 자체를 교체 (기존 내용 삭제 및 새로운 내용 추가)
        dynamicContentLoad.replaceWith(html_dom.body.firstChild);

        // CSS 파일 로드 (중복 로드 방지)
        if (pageData.css) {
          pageData.css.forEach((cssUrl) => {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = cssUrl;
            document.head.appendChild(link);
          });
        }

        // JavaScript 파일 로드 및 초기화 (중복 로드 및 초기화 방지)
        if (pageData.js) {
          loadJS(pageData.js, () => {
            // 필요한 경우 모듈 초기화 함수 호출
            if (!pageData.initialized) {
              initializeModules(page);
              pageData.initialized = true;
            }
          });
        }
      })
      .catch((error) => {
        console.error("Error loading page:", error);
        // 사용자에게 오류 메시지 표시 등
      });
    }
});


// Function to load CSS files
function loadCSS(url) {
  const link = document.createElement("link");
  link.rel = "stylesheet";
  link.href = `${url}?t=${new Date().getTime()}`; // Cache-busting query
  link.onload = () => console.log(`CSS loaded: ${url}`);
  link.onerror = () => console.error(`Error loading CSS: ${url}`);
  document.head.appendChild(link);
}

// Function to load JavaScript files
// function loadScripts(jsUrls, callback) {
//   let loadedScripts = 0;

//   jsUrls.forEach((jsUrl) => {
//     // 중복 로드 방지: 기존 스크립트를 제거
//     const existingScript = Array.from(document.querySelectorAll("script")).find(
//       (script) => script.src.includes(jsUrl)
//     );
//     if (existingScript) {
//       existingScript.remove();
//     }

//     const script = document.createElement("script");
//     script.type = "module";
//     script.src = `${jsUrl}?t=${new Date().getTime()}`;
//     script.onload = () => {
//       console.log(`Script loaded: ${jsUrl}`);
//       loadedScripts++;
//       if (loadedScripts === jsUrls.length && callback) {
//         callback();
//       }
//     };
//     script.onerror = () => console.error(`Error loading script: ${jsUrl}`);
//     document.body.appendChild(script);
//   });
// }

const loadedScripts = new Set();

function loadJS(url, callback) {
  if (loadedScripts.has(url)) {
    console.log(`Script already loaded: ${url}`);
    if (callback) callback();
    return;
  }

  const script = document.createElement("script");
  script.type = "module";
  script.src = `${url}`; // Remove cache-busting query
  script.onload = () => {
    console.log(`Script loaded: ${url}`);
    loadedScripts.add(url);
    if (callback) callback();
  };
  script.onerror = () => console.error(`Error loading script: ${url}`);
  document.body.appendChild(script);
}

// // Function to load JavaScript files
// function loadScripts(jsUrls, callback) {
//   jsUrls.forEach((jsUrl) => {
//     // Remove existing script if present
//     const existingScript = Array.from(document.querySelectorAll("script")).find(
//       (script) => script.src === jsUrl
//     );
//     if (existingScript) {
//       existingScript.remove();
//     }

//     const script = document.createElement("script");
//     script.type = "module"; // Ensure the script is treated as a module
//     script.src = `${jsUrl}?t=${new Date().getTime()}`; // Cache-busting query
//     script.onload = () => console.log(`Script loaded: ${jsUrl}`);
//     script.onerror = () => console.error(`Error loading script: ${jsUrl}`);
//     document.body.appendChild(script);

//   });
// }

function initializeModules(page) {
  switch (page) {
    case "category":
      import("../js/category.js")
        .then((module) => {
          module.initializeCategoryPage();
        })
        .catch((err) => console.error("Error importing category.js:", err));
      break;
    // 다른 페이지에 대한 초기화 작업도 이곳에 추가할 수 있습니다.
    default:
      console.error("No initialization logic defined for:", page);
  }
}




/* promise */
// Function to load CSS files
// function loadCSS(url) {
//   return new Promise((resolve, reject) => {
//     const link = document.createElement("link");
//     link.rel = "stylesheet";
//     link.href = `${url}?t=${new Date().getTime()}`; // Cache-busting query
//     link.onload = () => {
//       console.log(`CSS loaded: ${url}`);
//       resolve();
//     };
//     link.onerror = () => {
//       console.error(`Error loading CSS: ${url}`);
//       reject(new Error(`Error loading CSS: ${url}`));
//     };
//     document.head.appendChild(link);
//   });
// }

// // Function to load JavaScript files
// function loadScripts(jsUrls) {
//   return Promise.all(
//     jsUrls.map((jsUrl) => {
//       return new Promise((resolve, reject) => {
//         // Check if script already exists
//         const existingScript = document.querySelector(`script[src="${jsUrl}"]`);
//         if (existingScript) {
//           resolve(); // Script is already loaded
//           return;
//         }

//         const script = document.createElement("script");
//         script.type = "module";
//         script.src = jsUrl;
//         script.onload = () => {
//           console.log(`Script loaded: ${jsUrl}`);
//           resolve();
//         };
//         script.onerror = () => {
//           console.error(`Error loading script: ${jsUrl}`);
//           reject(new Error(`Error loading script: ${jsUrl}`));
//         };
//         document.body.appendChild(script);
//       });
//     })
//   );
// }
