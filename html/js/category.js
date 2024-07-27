import * as product from "./product.js";

let categoryMenusList = document.querySelectorAll(".menu_category_list");

function focusAfterClick(event) {
  // 메뉴 어느 곳이든 클릭했을 때 항상 같은 결과를 내도록 함

  // 모든 메뉴 항목에서 on 클래스 제거
  for (let i = 0; i < categoryMenusList.length; i++) {
    categoryMenusList[i].querySelector("a").classList.remove("on");
  }

  // 이벤트 타겟에 따라 가장 가까운 해당 부모 요소 찾음
  let targetMenu = event.target.closest(".menu_category_list");

  // 부모 요소 찾고 자식 a태그에 on 클래스 추가
  if (targetMenu) {
    targetMenu.querySelector("a").classList.add("on");
  }

  // rendering
  const categoryId = targetMenu.getAttribute("data-category");

  product.renderRecommendedProducts(categoryId);
  product.renderProducts(categoryId);
}

for (let i = 0; i < categoryMenusList.length; i++) {
  categoryMenusList[i].onclick = focusAfterClick;
}

product.renderRecommendedProducts(1);
product.renderProducts(1);
