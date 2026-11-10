const API_URL = "http://localhost:8080/phone-management-ajax-web-service/api/smartphones";

function saveSmartPhone(event) {
    if (event) {
        event.preventDefault();
    }
    let id = $('#smartphone-id').val();
    if (id) {
        updateSmartPhone(id);
    } else {
        addNewSmartPhone();
    }
}

function addNewSmartPhone() {
    let producer = $('#producer').val();
    let model = $('#model').val();
    let price = $('#price').val();
    let newSmartphone = {
        producer: producer,
        model: model,
        price: price
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newSmartphone),
        url: API_URL,
        success: function () {
            resetForm();
            successHandler();
        }
    });
}

function updateSmartPhone(id) {
    let producer = $('#producer').val();
    let model = $('#model').val();
    let price = $('#price').val();
    let updatedSmartphone = {
        producer: producer,
        model: model,
        price: price
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(updatedSmartphone),
        url: `${API_URL}/${id}`,
        success: function () {
            resetForm();
            successHandler();
        }
    });
}

function successHandler() {
    $.ajax({
        type: "GET",
        url: API_URL,
        success: function (data) {
            let content = '<tr>' +
                '<th>Producer</th>' +
                '<th>Model</th>' +
                '<th>Price</th>' +
                '<th>Edit</th>' +
                '<th>Delete</th>' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += getSmartphone(data[i]);
            }
            document.getElementById('smartphoneList').innerHTML = content;
            document.getElementById('smartphoneList').style.display = "table";
            document.getElementById('add-smartphone').style.display = "none";
            document.getElementById('title').style.display = "block";
            document.getElementById('display').style.display = "none";
            document.getElementById('display-create').style.display = "inline-block";
        }
    });
}

function displayFormCreate() {
    resetForm();
    document.getElementById('smartphoneList').style.display = "none";
    document.getElementById('add-smartphone').style.display = "block";
    document.getElementById('display-create').style.display = "none";
    document.getElementById('title').style.display = "none";
    document.getElementById('display').style.display = "inline-block";
}

function getSmartphone(smartphone) {
    return `<tr>` +
        `<td>${smartphone.producer}</td>` +
        `<td>${smartphone.model}</td>` +
        `<td>${smartphone.price}</td>` +
        `<td><button onclick="showEditForm(${smartphone.id})">Edit</button></td>` +
        `<td><button onclick="deleteSmartphone(${smartphone.id})">Delete</button></td>` +
        `</tr>`;
}

function showEditForm(id) {
    $.ajax({
        type: "GET",
        url: `${API_URL}/${id}`,
        success: function (smartphone) {
            $('#smartphone-id').val(smartphone.id);
            $('#producer').val(smartphone.producer);
            $('#model').val(smartphone.model);
            $('#price').val(smartphone.price);
            $('#form-title').text('Form update');
            $('#btn-submit').val('Update');
            $('#btn-cancel').show();

            document.getElementById('smartphoneList').style.display = "none";
            document.getElementById('add-smartphone').style.display = "block";
            document.getElementById('display-create').style.display = "none";
            document.getElementById('title').style.display = "none";
            document.getElementById('display').style.display = "inline-block";
        }
    });
}

function resetForm() {
    $('#smartphone-id').val('');
    $('#producer').val('');
    $('#model').val('');
    $('#price').val('');
    $('#form-title').text('Form create');
    $('#btn-submit').val('Add');
    $('#btn-cancel').hide();
}

function deleteSmartphone(id) {
    $.ajax({
        type: "DELETE",
        url: `${API_URL}/${id}`,
        success: function () {
            successHandler();
        }
    });
}

$(document).ready(function () {
    successHandler();
});

