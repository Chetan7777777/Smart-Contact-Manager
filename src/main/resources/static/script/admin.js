console.log("adminpagge")
document.querySelector("#file_input").addEventListener('change', (e) => {
  let file = e.target.files[0];
  let reader = new FileReader();
  reader.onload = function () {
    document.querySelector("#preview").setAttribute("src", reader.result);
  };
  reader.readAsDataURL(file);
});