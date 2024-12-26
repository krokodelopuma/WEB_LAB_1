let jsonData = { x: null, y: null, r: null };
let jsonANS = { x: null, y: null, r: null,result:null,current_time:null,exec_time:null };
let mas = [];


document.getElementById('form').addEventListener('submit', function(event) {
    event.preventDefault();
    validateForm();
});

function onlyOneCheckbox(selectedCheckbox) {
    const checkboxes = document.querySelectorAll('input[name="r"]');
    checkboxes.forEach((checkbox) => {
        if (checkbox !== selectedCheckbox) {
            checkbox.checked = false;
        }
    });
}

function validateForm() {
  var form = document.getElementById('form');
  jsonData = {x: null, y: null, r: null };
  jsonANS = {x: null, y: null, r: null,result:null,current_time:null,exec_time:null };
  var a=validateX() && validateY() && validateR()
  if (a){
    jsonANS.x=jsonData.x;
    jsonANS.y=jsonData.y;
    jsonANS.r=jsonData.r;


    //alert(jsonData.x +" "+jsonData.y+" "+jsonData.r)
    var jsonString = JSON.stringify(jsonData);
    sendForm(jsonString);
  }
  //alert(a)
  return (a);
}

function sendForm(jsonData) {
  fetch('/httpd-root/fcgi-bin/hello-world.jar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: jsonData,
  })
  .then(response => {
    if (!response.ok) {
      throw new Error("Ошибка сети: " + response.status);
    }
    return response.json();  // response.text(), response.json()
  })
  .then(data => {
    createNotification(JSON.stringify(data));
    //location.reload();
    jsonANS.result=data.result;
    jsonANS.current_time=data.current_time;
    jsonANS.exec_time=data.exec_time;

    mas.push(jsonANS);
    //alert(JSON.stringify(jsonANS));
    //alert('Ответ сервера: ' + JSON.stringify(data));
    populateTable(mas);
  })
  .catch(error => {
    //alert("3")
    createNotification("Ошибка отправки: " + error.message);
  });
}




function validateX() {
  let x = document.querySelector("select[name=x]").value;
  if (x === undefined) {
    createNotification("Значение X не выбрано");
    return false;
  }else if (0 !== (parseFloat(parseInt(x))-parseFloat(x))) {
      createNotification("X кривое");
      return false;
  }else if (!(x >= -5 && x <= 3)) {
    createNotification("X за пределами");
    return false;
  }else{
    jsonData.x  = parseInt(x);
  }
  return true;
}

function validateY() {
  let y = document.querySelector("input[name=y]").value.replace(",", ".");
  if (y === undefined || y === "") {
    createNotification("Y не введён");
    return false;
  } else if (!isNumeric(y)) {
    createNotification("Y не число");
    return false;
  } else if (!(y > -5 && y < 5)) {
    createNotification("Y не входит в область допустимых значений (-5 < Y < 5)");
    return false;
  }else {
    jsonData.y = y;//parseDouble(y);
  }
  return true;
}

function validateR() {
  let checkboxes = document.querySelectorAll("input[name=r]:checked");
  if (checkboxes.length === 0) {
    createNotification("Значение R не выбрано");
    return false;
  }else if (checkboxes.length > 1) {
    createNotification("Значений R много");
    return false;
  }else if (0 !== (parseFloat(parseInt(checkboxes[0].value))-parseFloat(checkboxes[0].value))) {
    createNotification("R кривое");
    return false;
  }else if (!(checkboxes[0].value >= 1 && checkboxes[0].value <= 5)) {
    createNotification("R за пределами");
    return false;
  }else{
    r = checkboxes[0].value;
    jsonData.r  = parseInt(r);
  }
  return true;
}

function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

function createNotification(message) {
  let notification = document.createElement("div");
  notification.className = "notification";
  notification.innerText = message;
  document.body.appendChild(notification);

  setTimeout(() => {
    notification.remove();
  }, 5000);
}


function populateTable(data) {
  const tbody = document.querySelector("#dynamic-table tbody");
  tbody.innerHTML = "";
  //alert(JSON.stringify(data[0]))
  data.forEach(obj => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${obj.x ?? ""}</td>
      <td>${obj.y ?? ""}</td>
      <td>${obj.r ?? ""}</td>
      <td>${obj.result ?? ""}</td>
      <td>${obj.current_time ?? ""}</td>
      <td>${obj.exec_time ?? ""}</td>
    `;
    tbody.appendChild(row);
  });
}




