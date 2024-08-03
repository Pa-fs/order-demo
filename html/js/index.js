fetch("header.html")
  .then((response) => response.text())
  .then((html) => {
    document.getElementById("header").innerHTML = html;

    const script = document.createElement("script");
    script.type = "module";
    script.src = "../js/page_load.js";
    document.head.appendChild(script);
  });

fetch("slider.html")
  .then((response) => response.text())
  .then((html) => {
    document.getElementById("dynamic_content_load").innerHTML = html;
    const script = document.createElement("script");
    script.src = "../js/tp_hp.js";
    document.head.appendChild(script);
  });

fetch("footer.html")
  .then((response) => response.text())
  .then((html) => {
    document.getElementById("footer").innerHTML = html;
  });
