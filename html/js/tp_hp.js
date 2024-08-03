// Function to initialize slider when content is dynamically loaded
function initializeSlider() {
  let slideIndex = 1;
  showSlides(slideIndex);

  function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("s-slideimg");

    if (!slides.length) return;

    if (n > slides.length) {
      slideIndex = 1;
    }
    if (n < 1) {
      slideIndex = slides.length;
    }

    for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
  }

  let autoSlideIndex = 0;
  showAutoSlides();

  function showAutoSlides() {
    let i;
    let autoSlides = document.getElementsByClassName("s-slideimg");

    if (!autoSlides.length) return;

    for (i = 0; i < autoSlides.length; i++) {
      autoSlides[i].style.display = "none";
    }
    autoSlideIndex++;
    if (autoSlideIndex > autoSlides.length) {
      autoSlideIndex = 1;
    }
    autoSlides[autoSlideIndex - 1].style.display = "block";

    setTimeout(showAutoSlides, 3000);
  }
}

// Call this function after dynamically loading content
initializeSlider();
