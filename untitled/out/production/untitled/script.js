let jsonData = { x: null, y: null, r: null };

function validateForm() {
  //alert("123123")
  var form = document.getElementById('form');
  jsonData = { x: null, y: null, r: null };
  var a=validateX() && validateY() && validateR()
  if (a){
    alert(jsonData.x +" "+jsonData.y+" "+jsonData.r)
    var jsonString = JSON.stringify(jsonData);
    // Отправляем запрос через AJAX
    alert(jsonString)
    sendForm(jsonString);
  }
  //alert("12")
  alert(a)
  return (a);
}

function sendForm(jsonData) {
  fetch('/httpd-root/fcgi-bin/hello-world.jar', {
    method: 'POST',  // Указываем метод POST
    headers: {
      'Content-Type': 'application/json',  // Устанавливаем заголовок
    },
    body: jsonData,  // Отправляем тело запроса в формате JSON
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Ошибка сети: ' + response.status);
    }
    return response.json();  // Или response.text(), response.json() если сервер возвращает текст
  })
  .then(data => {
    // Обработка ответа от сервера
    alert('Ответ сервера: ' + data);
  })
  .catch(error => {
    // Обработка ошибок
    alert('Ошибка отправки: ' + error.message);
  });
}




function validateX() {
  let x = document.querySelector("select[name=x]").value;
  if (x === undefined) {
    createNotification("Значение X не выбрано");
    return false;
  }else{
    jsonData.x  = parseInt(x);
  }
  //alert(x)
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
    jsonData.y = parseFloat(y);
  }
  return true;
}

function validateR() {
  let checkboxes = document.querySelectorAll("input[name=r]:checked");
  if (checkboxes.length === 0) {
    createNotification("Значение R не выбрано");
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
  }, 3000); // Уведомление исчезает через 3 секунды
}
