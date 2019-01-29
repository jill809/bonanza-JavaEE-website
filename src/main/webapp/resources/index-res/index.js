(function () {
    var toHide = document.querySelectorAll(".anime-while-logreg");

    function beginLogin() {
        for (var i = 0; i < toHide.length; i++) {
            toHide[i].setAttribute("while-logreg", true);
        }
        var skt = document.getElementById("sketch");
        skt.removeAttribute("while-register");
        skt.setAttribute("while-login", true);
    };

    function beginRegister() {
        for (var i = 0; i < toHide.length; i++) {
            toHide[i].setAttribute("while-logreg", true);
        }
        var skt = document.getElementById("sketch");
        skt.removeAttribute("while-login");
        skt.setAttribute("while-register", true);
    };

    var loginBtn = document.querySelector(".btn.show-login"),
        regBtn = document.querySelector(".btn.show-register");

    loginBtn.addEventListener("mousedown", beginLogin);
    regBtn.addEventListener("mousedown", beginRegister);
})();