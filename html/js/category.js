let categoryMenus = document.querySelectorAll(".menu_category_list a");

function focusAfterClick(event) {
  for (let i = 0; i < categoryMenus.length; i++) {
    categoryMenus[i].classList.remove("on");
  }
  event.target.classList.add("on");
}

for (let i = 0; i < categoryMenus.length; i++) {
  categoryMenus[i].onclick = focusAfterClick;
}
