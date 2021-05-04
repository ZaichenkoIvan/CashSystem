// var id_code = document.getElementById("id_code");
// var id_name = document.getElementById("id_name");
// if (id_code != null) {
// 	id_code.addEventListener("input", function(){
// 	    if (id_code.value != null) {
// 	    	id_name.value = null;
// 	    }
// 	});
// 	id_name.addEventListener("input", function(){
// 	    if (id_name.value != null) {
// 	    	id_code.value = null;
// 	    }
// 	});
// }

function check_pass() {
    if (document.getElementById('id_password').value ==
            document.getElementById('id_confirm').value) {
        document.getElementById('id_btnReg').disabled = false;
    } else {
        document.getElementById('id_btnReg').disabled = true;
    }
}
