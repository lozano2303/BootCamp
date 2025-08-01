const LoadBar=() =>{
const progressBar = document.querySelector(".progress-bar");
let width = 0;
const interval = setInterval (animate, 20);


function animate (){
 if(width >= 98){
   window.location.href = "../Frontend/index.html";
 } else{
    width++;
    progressBar.style.width = `${width}%`;
 }
}
}

window.addEventListener('DOMContentLoaded', LoadBar);