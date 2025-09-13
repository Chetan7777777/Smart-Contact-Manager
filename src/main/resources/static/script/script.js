// function that will change the darkMode if localstorage has the darkMode:true
function runPresentTheme() {
    const theme = document.querySelector("#darkLightMode")
    const html = document.querySelector("html");
  if (localStorage.getItem("darkMode") == null) {
    return;
  } if (localStorage.getItem("darkMode") == "true") {
    html.classList.add("dark");
    if(theme.textContent == null){
        return;
    }
    theme.textContent = "Light";
  } else {
      html.classList.remove("dark");
      if (theme.textContent == null){
        return;
      }
      theme.textContent = "Dark";

  }
}
// funciton that will change theme light to dark or vice-versa and also call the runPresentTheme function
function changeTheme() {
  if (localStorage.getItem("darkMode") == "true") {
    localStorage.setItem("darkMode", false);
  } else {
    localStorage.setItem("darkMode", true);
  }
  runPresentTheme();
}

